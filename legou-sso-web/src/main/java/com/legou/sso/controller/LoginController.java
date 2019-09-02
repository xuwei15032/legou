package com.legou.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.utils.CookieUtils;
import com.legou.common.utils.LegouResult;
import com.legou.sso.service.LoginService;



@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	
	@RequestMapping("/page/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public LegouResult login(String username, String password,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("login"+username);
		LegouResult legouResult = loginService.userLogin(username, password);
		//判断是否登录成功
		if(legouResult.getStatus() == 200) {
			String token = legouResult.getData().toString();
			//如果登录成功需要把token写入cookie
			CookieUtils.setCookie(request, response, TOKEN_KEY, token);
		}
		//返回结果
		return legouResult;
	}
}
