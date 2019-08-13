package com.legou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbItem;
import com.legou.service.ItemService;

@Controller
public class ItemController {
	// 需要调用service查询

	// @Autowired注解需要找到该接口的实现类实例

	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")

	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId) {

		TbItem item = itemService.getItem(itemId);
		return item;

	}

	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(int page, int rows) {

		return itemService.getItemList(page, rows);

	}
	
	
	@RequestMapping("/item/save")
	@ResponseBody
	public LegouResult saveItem(TbItem tbItem,String desc){
		
		LegouResult leoLegouResul = itemService.save(tbItem,desc);
		
		return leoLegouResul;
	}
	
	

	@RequestMapping("/test")
	@ResponseBody
	public String Test() {
		return "hello test";
	}
	


}
