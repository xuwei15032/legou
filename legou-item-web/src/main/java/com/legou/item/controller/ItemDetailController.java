package com.legou.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legou.common.pojo.SearchItem;
import com.legou.item.pojo.ItemDetailPojo;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemDesc;
import com.legou.service.ItemDescService;
import com.legou.service.ItemService;

@Controller
public class ItemDetailController {
	
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemid}")
	public String getItemDetailInfo(@PathVariable  Long itemid,Model model) {
		
	   TbItem tbItem= itemService.getItem(itemid);
	   ItemDetailPojo  itemDetailPojo = new ItemDetailPojo(tbItem);	  
	   TbItemDesc tbItemDesc = itemDescService.getById(itemid);
	   model.addAttribute("item", itemDetailPojo);
	   model.addAttribute("itemDesc", tbItemDesc);
		return "item";
	}

}
