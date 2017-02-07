package com.clj.csmanagement.management.goodsManage.dao;

import java.sql.SQLException;
import java.util.List;

import com.clj.csmanagement.dto.Goods;

public interface GoodsManagementDAO {

	public List<Goods> queryGoodsInit();
	
	public List<Goods> queryGoodsqx();
	
	public List<Goods> queryGoodsInitM();
	
	public List<Goods> queryByType(String goodsType);
	
	public List<Goods> queryByName(String goodsName);
	
	public void addGoods(Goods goods,String goods_id);
	
	public void updateGoods(Goods goods);
	
	public void goodsSelled(Goods goods);
	
	public void deleteGoods(Goods goods);
}
