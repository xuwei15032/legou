package com.legou.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.EasyUITreeNode;
import com.legou.mapper.TbItemCatMapper;
import com.legou.pojo.TbItemCat;
import com.legou.pojo.TbItemCatExample;
import com.legou.pojo.TbItemCatExample.Criteria;
import com.legou.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Autowired
   TbItemCatMapper tbItemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getItemCatList(long id) {
	
		TbItemCatExample example= new TbItemCatExample();	
		Criteria createCriteria = example.createCriteria();	
		
		createCriteria.andParentIdEqualTo(id);	
		List<TbItemCat> selectByExampleList = tbItemCatMapper.selectByExample(example);
		
		List<EasyUITreeNode> listNodes= new ArrayList<EasyUITreeNode>();
		
		for (TbItemCat itemcat : selectByExampleList) {		
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();			
			easyUITreeNode.setId(itemcat.getId());	
			easyUITreeNode.setText(itemcat.getName());		
			easyUITreeNode.setState(itemcat.getIsParent()?"closed":"open");	
			listNodes.add(easyUITreeNode);
			
		}
		
		return listNodes;
	}

}
