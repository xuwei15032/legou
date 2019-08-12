package com.legou.service;

import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbItem;

public interface ItemService {
	
	TbItem getItem(Long  itemId);
	
	EasyUIDataGridResult getItemList(Integer page,Integer rows);

	LegouResult save(TbItem tbItem, String desc);

	LegouResult desc(long id);
	

}
