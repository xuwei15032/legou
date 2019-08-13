package com.legou.content.service;

import java.util.List;

import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbContent;

public interface ContentService {

	LegouResult saveContent(TbContent tbContent);

	EasyUIDataGridResult queryList(int page, int rows);
	List<TbContent> getContentByCategoryId(long i);

}
