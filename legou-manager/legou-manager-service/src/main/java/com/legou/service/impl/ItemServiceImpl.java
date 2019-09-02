package com.legou.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.redis.JedisClientPool;
import com.legou.common.utils.IDUtils;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.mapper.TbItemDescMapper;
import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemDesc;
import com.legou.pojo.TbItemExample;
import com.legou.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;

	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private  JmsTemplate jmsTemplate;
	
	//resource 默认按照名字注入
    @Resource
	private Destination activeMQTopic;
    
	@Autowired
	private JedisClientPool jedisClientPool;

	@Override
	public TbItem getItem(Long itemId) {
		
		String itemRedis = jedisClientPool.hget("item", itemId+"");		
		if(!StringUtils.isEmpty(itemRedis)) {
			System.out.println("从缓存中获取item");
			return JsonUtils.jsonToPojo(itemRedis,TbItem.class);
		}
		System.out.println("从数据库中获取item");	
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		jedisClientPool.hset("item", itemId+"", JsonUtils.objectToJson(tbItem));
		jedisClientPool.expire("item",60);
		return tbItem;
		
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

	@Override
	public LegouResult save(TbItem tbItem, String desc) {

		long itemId = IDUtils.genItemId();
		System.out.println(itemId);
		tbItem.setId(itemId);
		tbItem.setStatus((byte) 1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		tbItemMapper.insert(tbItem);
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDescMapper.insert(tbItemDesc);
		
		//每次添加一条商品就需要向mq里面发送一条消息
		jmsTemplate.send(activeMQTopic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(Long.toString(itemId));
			}
		});

		return LegouResult.ok();
	}

	@Override
	public LegouResult desc(long id) {
		TbItemDesc desc  = tbItemDescMapper.selectByPrimaryKey(id);
		LegouResult ls = new LegouResult(desc);
		return ls;
	}

}
