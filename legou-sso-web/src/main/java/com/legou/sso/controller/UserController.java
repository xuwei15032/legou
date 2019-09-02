package com.legou.sso.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbUser;
import com.legou.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/check/{name}/{type}")
	@ResponseBody
	public LegouResult check(@PathVariable String name,@PathVariable int type) throws Exception {
		return userService.ckeck(name, type);
	}
	

	@RequestMapping("/register")
	@ResponseBody
	public LegouResult register(TbUser user) {
		return userService.insertUser(user);		
	}
	

	
}








