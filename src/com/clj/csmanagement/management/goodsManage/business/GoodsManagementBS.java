package com.clj.csmanagement.management.goodsManage.business;

import java.sql.SQLException;

import com.clj.csmanagement.dto.Goods;
import com.clj.csmanagement.dto.GoodsDTO;

public interface GoodsManagementBS {

	public GoodsDTO queryGoodsInit();
	
	public GoodsDTO queryGoodsqx();
	
	public GoodsDTO queryGoodsInitM();
	
	public GoodsDTO queryByType(String goodsType);
	
	public GoodsDTO queryByName(String goodsName);
	
	public void addGoods(Goods goods);
	
	public void updateGoods(Goods goods);
	
	public void goodsSelled(Goods goods);
	
	public void deleteGoods(Goods goods);
}
