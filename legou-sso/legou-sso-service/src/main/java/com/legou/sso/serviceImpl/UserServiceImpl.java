package com.legou.sso.serviceImpl;

import java.util.Date;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.legou.common.utils.LegouResult;
import com.legou.mapper.TbUserMapper;
import com.legou.pojo.TbUser;
import com.legou.sso.dao.UserMapper;
import com.legou.sso.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
     UserMapper userMapper;
	
	@Autowired
	TbUserMapper tbUserMapper;

	@Override
	public LegouResult ckeckName(String name) {
		TbUser user = userMapper.findByName(name);
		System.out.println(user);
		if(user==null) {
			return LegouResult.ok(true);
		}
		return LegouResult.ok(false);
	}
	
	@Override
	public LegouResult ckeckPhone(String phone) {
		TbUser user = userMapper.findByPhone(phone);
		if(user==null) {
			return LegouResult.ok(true);
		}
		return LegouResult.ok(false);
	}
	
	@Override
	public LegouResult insertUser(TbUser user) {
		
		//数据有效性校验
				if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) 
						|| StringUtils.isBlank(user.getPhone())) {
					return LegouResult.build(400, "用户数据不完整，注册失败");
				}
				//1：用户名 2：手机号 3：邮箱
				LegouResult result = this.ckeck(user.getUsername(), 1);
				if (!(boolean) result.getData()) {
					return LegouResult.build(400, "此用户名已经被占用");
				}
				result = this.ckeck(user.getPhone(), 2);
				if (!(boolean)result.getData()) {
					return LegouResult.build(400, "手机号已经被占用");
				}
				//对密码进行md5加密
				String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
				user.setPassword(md5Pass);
		user.setCreated(new Date());
		user.setUpdated(new Date());
		tbUserMapper.insertSelective(user);
		return LegouResult.ok();
	}
	
	@Override
	public LegouResult ckeck(String value, int type) {
		if(type == 1) {
			return ckeckName(value);
		}
		return ckeckPhone(value);
	}
	


}
