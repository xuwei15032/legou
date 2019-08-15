package com.legou.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.redis.JedisClientPool;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.content.service.ContentService;
import com.legou.mapper.TbContentMapper;
import com.legou.pojo.TbContent;
import com.legou.pojo.TbContentExample;
import com.legou.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	TbContentMapper tbContentMapper;
	
	@Autowired
	JedisClientPool jedisClientPool;

	@Override
	public LegouResult saveContent(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		tbContentMapper.insert(tbContent);		
		jedisClientPool.hdel("CONTENT_LIST", tbContent.getCategoryId().toString());		
		return LegouResult.ok();
	}

	@Override
	public EasyUIDataGridResult queryList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		List<TbContent> selectByExampleList = tbContentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(selectByExampleList);
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		easyUIDataGridResult.setRows(selectByExampleList);
		easyUIDataGridResult.setTotal(pageInfo.getTotal());
		return easyUIDataGridResult;
	}

	@Override
	public List<TbContent> getContentByCategoryId(long catgoryid) {

          String cashContentList= jedisClientPool.hget("CONTENT_LIST", catgoryid+"");
		
		if(!StringUtils.isEmpty(cashContentList)) {
			System.out.println("从缓存中获取");
			return JsonUtils.jsonToList(cashContentList, TbContent.class);
		}
		System.out.println("从数据库中获取");
		 TbContentExample example= new TbContentExample();
		 Criteria createCriteria = example.createCriteria();
		 createCriteria.andCategoryIdEqualTo(catgoryid);
		 List<TbContent> contentList= tbContentMapper.selectByExample(example);
		 jedisClientPool.hset("CONTENT_LIST", catgoryid+"", JsonUtils.objectToJson(contentList));
	 	 
		 return contentList;
	}
	

}
