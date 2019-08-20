package com.legou.search.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legou.common.pojo.SearchResult;
import com.legou.search.service.SolrSearchService;

@Controller
public class SearchController {
	
	@Autowired
	SolrSearchService solrSearchService;
	
	@RequestMapping("/search")
	public String search(String keyword,Model model) throws Exception {
		
		keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
		
		SearchResult searchResult = solrSearchService.search(keyword);
	
		model.addAttribute("itemList", searchResult.getItemList());
		
		return "search";
	}

}
