package com.legou.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.cart.service.LegouCartService;
import com.legou.common.utils.CookieUtils;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbUser;
import com.legou.service.ItemService;

@Controller
public class LegouCartController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private LegouCartService legouCartService;

	public List<TbItem> getItemFromCookie(HttpServletRequest request) {

		String jsonString = CookieUtils.getCookieValue(request, "legoucart", true);

		if (StringUtils.isBlank(jsonString)) {
			return new ArrayList<TbItem>(); // []
		}
		List<TbItem> itemList = JsonUtils.jsonToList(jsonString, TbItem.class);
		return itemList;
	}

	@RequestMapping("/cart/add/{itemid}")
	public String cart(@PathVariable long itemid, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {

		// 先判断用户是否登录
		TbUser tbUser = (TbUser) request.getAttribute("legouuser");

		if (tbUser != null) {
			// 如果用户已经登录我们就把购物车保存在服务器端，不保存在用户客户端浏览器里面
			legouCartService.addItemToCart(tbUser.getId(), itemid, num);
			return "cartSuccess";
		}

		// 把用户购买的商品信息加入购物车(即使用户在没有登录的状态下也需要保存用户的购物信息到cookie)
		// 先从cookie里面取出所有的商品信息
		List<TbItem> items = getItemFromCookie(request);
		// 添加一个判断标记
		boolean flag = false;
		// 遍历cookie里面的所有的商品
		for (TbItem item : items) {
			if (item.getId() == itemid) {
				item.setNum(item.getNum() + num);
				flag = true;
			}
		}

		// 如果cookie中不存在当前商品那么再去数据库中搜索该商品
		if (!flag) {
			// 如果flag是false ！flag=true
			TbItem tbItem = itemService.getItem(itemid);
			tbItem.setNum(num);
			String imagePath = tbItem.getImage();
			if (StringUtils.isNotBlank(imagePath)) {
				// 由于一条商品可以有多张图片，我们购物车仅仅需要一张图片
				String[] imagesPath = imagePath.split(",");
				tbItem.setImage(imagesPath[0]);
			}
			items.add(tbItem);
		}

		CookieUtils.setCookie(request, response, "legoucart", JsonUtils.objectToJson(items), 30 * 60 * 60, true);
		return "cartSuccess";
	}

	@RequestMapping("/cart/cart")
	public String goCart(HttpServletResponse response, HttpServletRequest request) {

		List<TbItem> itemList = getItemFromCookie(request);

		// 如果用户已经登录，那么购物车列表信息就需要从redis里面获取

		// 先判断用户是否登录
		TbUser tbUser = (TbUser) request.getAttribute("legouuser");

		if (tbUser != null) {

			legouCartService.moveCookItemToRedis(tbUser.getId(), itemList);
			CookieUtils.deleteCookie(request, response, "legoucart");

			// 从redis里面取出合并后的商品信息
			itemList = legouCartService.getItemList(tbUser.getId());

		}

		request.setAttribute("cartList", itemList);

		return "cart";

	}

	// 更新购物车数量
	@ResponseBody
	@RequestMapping("/cart/update/num/{itemid}/{num}")
	public LegouResult modifyItemNum(@PathVariable long itemid, @PathVariable int num, HttpServletRequest request,
			HttpServletResponse response) {
		
		// 先判断用户是否登录
		TbUser tbUser = (TbUser) request.getAttribute("legouuser");
	
		if(tbUser !=null) {
			legouCartService.updateCartNum(tbUser.getId(),itemid,num);
			return LegouResult.ok();
		}

		List<TbItem> itemList = getItemFromCookie(request);

		for (TbItem tbItem : itemList) {
			if (tbItem.getId() == itemid) {
				tbItem.setNum(num);
			}
		}

		CookieUtils.setCookie(request, response, "legoucart", JsonUtils.objectToJson(itemList), 30 * 60 * 60, true);

		return LegouResult.ok();
	}

	@RequestMapping("/cart/delete/{itemid}")
	public String deleteCart(@PathVariable Long itemid, HttpServletRequest request, HttpServletResponse response) {

		//如果登录，删除购物车信息就需要操作redis
		
		// 先判断用户是否登录
		TbUser tbUser = (TbUser) request.getAttribute("legouuser");
		if(tbUser !=null) {
			legouCartService.deleteItemFromCart(tbUser.getId(),itemid);
			return "redirect:/cart/cart.html";
		}
		
		
		List<TbItem> tbItems = getItemFromCookie(request);

		for (TbItem tbItem : tbItems) {
			if (tbItem.getId().longValue() == itemid) {
				tbItems.remove(tbItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "legoucart", JsonUtils.objectToJson(tbItems), 30 * 60 * 60, true);
		return "redirect:/cart/cart.html";

	}

}
