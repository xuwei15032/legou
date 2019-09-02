package com.legou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.legou.common.redis.JedisClientPool;
import com.legou.common.utils.JsonUtils;
import com.legou.mapper.TbItemDescMapper;
import com.legou.pojo.TbItemDesc;
import com.legou.service.ItemDescService;

@Service 
public class ItemDescServiceImpl implements ItemDescService{
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private JedisClientPool jedisClientPool;
	
	@Override
	public TbItemDesc getById(long id) {
		String itemDescRedis = jedisClientPool.hget("itemDesc", id+"");		
		if(!StringUtils.isEmpty(itemDescRedis)) {
			System.out.println("从缓存中获取itemDesc");
			return JsonUtils.jsonToPojo(itemDescRedis,TbItemDesc.class);
		}
		System.out.println("从数据库中获取itemDesc");
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		jedisClientPool.hset("itemDesc", id+"", JsonUtils.objectToJson(tbItemDesc));
		jedisClientPool.expire("itemDesc",60);
		return tbItemDesc;
	}

}
