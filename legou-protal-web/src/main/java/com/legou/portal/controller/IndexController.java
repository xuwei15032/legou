package com.legou.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legou.content.service.ContentService;
import com.legou.pojo.TbContent;

@Controller
public class IndexController {
	
	@Autowired
	ContentService contentService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		
		long categoryid=89;
		List<TbContent> contentList= contentService.getContentByCategoryId(categoryid);
		model.addAttribute("aD1List", contentList);
		return "index";
	}

}
