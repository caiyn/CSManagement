package com.clj.csmanagement.dto;

import java.util.ArrayList;
import java.util.List;

public class GoodsDTO {

	List<Goods> goodsList = new ArrayList<Goods>();

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}
	
}
