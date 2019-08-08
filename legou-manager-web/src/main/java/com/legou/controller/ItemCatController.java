package com.legou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.legou.common.pojo.EasyUITreeNode;
import com.legou.service.ItemCatService;

@Controller
public class ItemCatController {
	
	@Autowired
	ItemCatService itemCatService;
	
	
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCat(@RequestParam(name="id",defaultValue="0") long id){
		
		List<EasyUITreeNode> itemcatList= itemCatService.getItemCatList(id);
		return itemcatList;
		
	}
	

}
