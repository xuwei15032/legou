package com.legou.sso.dao;

import com.legou.pojo.TbUser;

public interface UserMapper {
	TbUser findByName(String name);
	TbUser findByPhone(String phone);
	TbUser findByNameAndPswd(TbUser user);
}
