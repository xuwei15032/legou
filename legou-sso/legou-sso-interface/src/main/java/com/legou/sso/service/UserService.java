package com.legou.sso.service;

import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbUser;
import javax.servlet.http.HttpServletRequest;

public interface UserService {
	LegouResult ckeckName(String name);
	LegouResult ckeckPhone(String phone);
	LegouResult insertUser(TbUser user);
	LegouResult ckeck(String value ,int type);
}
