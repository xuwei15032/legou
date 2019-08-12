package com.legou.content.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.EasyUITreeNode;
import com.legou.content.service.ContentCategoryService;
import com.legou.mapper.TbContentCategoryMapper;
import com.legou.pojo.TbContentCategory;
import com.legou.pojo.TbContentCategoryExample;
import com.legou.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getConteCategoryList(long id) {
		
		TbContentCategoryExample example= new TbContentCategoryExample();
		
		Criteria createCriteria = example.createCriteria();
		
		createCriteria.andParentIdEqualTo(id);
		List<TbContentCategory>  categoryList= tbContentCategoryMapper.selectByExample(example);
		
		List<EasyUITreeNode> nodelisTreeNodes= new ArrayList<EasyUITreeNode>();
		
		for(TbContentCategory category :categoryList) {
			
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			
			easyUITreeNode.setId(category.getId());
			easyUITreeNode.setText(category.getName());
			easyUITreeNode.setState(category.getIsParent()?"closed":"open");
			nodelisTreeNodes.add(easyUITreeNode);
			
		}
		
		return nodelisTreeNodes;
	}

}
