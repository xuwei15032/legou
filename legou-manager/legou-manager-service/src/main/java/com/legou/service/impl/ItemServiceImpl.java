package com.legou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbItem;
import com.legou.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private TbItemMapper tbItemMapper;

	@Override
	public TbItem getItem(Long itemId) {
		
		
		return tbItemMapper.selectByPrimaryKey(itemId);
	}

}
