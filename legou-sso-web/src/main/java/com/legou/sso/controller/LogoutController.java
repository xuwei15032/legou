package com.legou.sso.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legou.sso.service.LogoutService;

@Controller
public class LogoutController {
	
	@Autowired
	LogoutService logoutService;
	
	@RequestMapping("/logout/{token}")
	public void logout(@PathVariable String token,HttpServletResponse response) throws IOException {
		logoutService.Logout(token);
		response.sendRedirect("http://localhost:8082");
	}

}
