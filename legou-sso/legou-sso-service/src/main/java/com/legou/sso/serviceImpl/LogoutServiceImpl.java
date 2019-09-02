package com.legou.sso.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.redis.JedisClient;
import com.legou.sso.service.LogoutService;

@Service
public class LogoutServiceImpl implements LogoutService {
	
	@Autowired
	private JedisClient jedisClient;

	@Override
	public void Logout(String token) {
		jedisClient.del("SESSION:" + token);		
	}
}
