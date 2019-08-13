package com.legou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.utils.LegouResult;
import com.legou.content.service.ContentService;
import com.legou.pojo.TbContent;

@Controller
public class ContentController {
	
	@Autowired
	ContentService contentService;
	
	
	
	@RequestMapping("/content/save")
	@ResponseBody
	public LegouResult saveContent(TbContent tbContent){
		
		LegouResult legouResult = contentService.saveContent(tbContent);
		
		return legouResult;
		
	}
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult queryList(int page ,int rows) {
		EasyUIDataGridResult easyUIDataGridResult = contentService.queryList(page,rows);
		return easyUIDataGridResult;
	}

}
