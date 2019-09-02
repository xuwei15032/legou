package com.legou.item.pojo;

import com.legou.pojo.TbItem;

public class ItemDetailPojo extends TbItem {
	
	public ItemDetailPojo() {
		
	}
	public ItemDetailPojo(TbItem tbItem) {
		this.setId(tbItem.getId());
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setCreated(tbItem.getCreated());
		this.setImage(tbItem.getImage());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setTitle(tbItem.getTitle());
		this.setUpdated(tbItem.getUpdated());
	}
	public String[] getImages() {
		if(this.getImage()!=null && !"".equals(this.getImage())) {
			String[] images= this.getImage().split(",");
			return images;
		}
		return null;
	}
}
