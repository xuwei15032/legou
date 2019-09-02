package com.legou.cart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.cart.service.LegouCartService;
import com.legou.common.redis.JedisClient;
import com.legou.common.utils.JsonUtils;
import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbItem;

@Service
public class LegouCartServiceImpl implements LegouCartService{
	
	@Autowired
	JedisClient jedisClient;
	
	@Autowired
	TbItemMapper tbItemMapper;
	
	

	@Override
	public void addItemToCart(Long id, long itemid, Integer num) {
		
		//首先判断该商品是否在redis中存在
		
		boolean isexists= jedisClient.hexists("legou"+id, itemid+"");
		
		if(isexists) {
			
			String itemJsonString = jedisClient.hget("legou"+id, itemid+"");
			
			TbItem tbItem = JsonUtils.jsonToPojo(itemJsonString, TbItem.class);
			
			tbItem.setNum(tbItem.getNum()+num);
			
			jedisClient.hset("legou"+id, itemid+"", JsonUtils.objectToJson(tbItem));
			
		}
		
		//把商品信息，用户id 添加到redis里面
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemid);
		tbItem.setNum(num);
		String imagePath = tbItem.getImage();
		if (StringUtils.isNotBlank(imagePath)) {
			// 由于一条商品可以有多张图片，我们购物车仅仅需要一张图片
			String[] imagesPath = imagePath.split(",");
			tbItem.setImage(imagesPath[0]);
		}
		jedisClient.hset("legou"+id, itemid+"", JsonUtils.objectToJson(tbItem));
		
		
	}



	@Override
	public void moveCookItemToRedis(Long userId, List<TbItem> cookItemList) {
		
		
		for (TbItem tbItem : cookItemList) {
			addItemToCart(userId,tbItem.getId(),tbItem.getNum());
		}
		
	}



	@Override
	public List<TbItem> getItemList(Long id) {
		
		List<String> jsonListStrings= jedisClient.hvals("legou"+id);
		
		List<TbItem> newList= new ArrayList<TbItem>();
		
		for (String tbItemString : jsonListStrings) {
			TbItem tbItem = JsonUtils.jsonToPojo(tbItemString, TbItem.class);
			newList.add(tbItem);
		}
		
		return newList;
	}



	@Override
	public void deleteItemFromCart(Long id, Long itemid) {
	
		jedisClient.hdel("legou"+id, itemid+"");
		
	}



	@Override
	public void updateCartNum(Long id, long itemid, int num) {
		
		String itemJsonString = jedisClient.hget("legou"+id, itemid+"");
		
		TbItem tbItem = JsonUtils.jsonToPojo(itemJsonString, TbItem.class);
		
		tbItem.setNum(num);
		
		jedisClient.hset("legou"+id, itemid+"", JsonUtils.objectToJson(tbItem));
		
		
	}

}
