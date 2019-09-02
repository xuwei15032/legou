package com.legou.cart.service;

import java.util.List;

import com.legou.pojo.TbItem;

public interface LegouCartService {

	void addItemToCart(Long id, long itemid, Integer num);

	void moveCookItemToRedis(Long id, List<TbItem> cookItemList);

	List<TbItem> getItemList(Long id);

	void deleteItemFromCart(Long id, Long itemid);

	void updateCartNum(Long id, long itemid, int num);
	
	
	

}
