package com.legou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemExample;
import com.legou.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private TbItemMapper tbItemMapper;

	@Override
	public TbItem getItem(Long itemId) {
		
		return tbItemMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		
		PageHelper.startPage(page, rows);
		
		TbItemExample example = new TbItemExample();
		
		List<TbItem> selectByExampleList = tbItemMapper.selectByExample(example);
		
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(selectByExampleList);
		
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		easyUIDataGridResult.setRows(selectByExampleList);
		easyUIDataGridResult.setTotal(pageInfo.getTotal());
		
		
		
		return easyUIDataGridResult;
	}

}
