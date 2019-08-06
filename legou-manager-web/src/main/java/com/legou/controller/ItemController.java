package com.legou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.pojo.TbItem;
import com.legou.service.ItemService;

@Controller
public class ItemController {
	//需要调用service查询
	
	
	@Autowired
	private ItemService ItemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId) {
		
		TbItem item = ItemService.getItem(itemId);
		
		return item;
		
	}
	
	
	
	
	@RequestMapping("/test")
	@ResponseBody
	public String Test() {
		return "hello test";
	}
	
	

}
