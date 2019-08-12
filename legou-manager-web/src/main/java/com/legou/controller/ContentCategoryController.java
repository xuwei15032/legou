package com.legou.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.pojo.EasyUITreeNode;
import com.legou.content.service.ContentCategoryService;

@Controller
public class ContentCategoryController {
	
	@Autowired
	ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatgoryList(@RequestParam(name="id",defaultValue="0") long id){
		
		List<EasyUITreeNode> contentList= contentCategoryService.getConteCategoryList(id);
		return contentList;
	}

}
