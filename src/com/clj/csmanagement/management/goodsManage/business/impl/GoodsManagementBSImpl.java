package com.clj.csmanagement.management.goodsManage.business.impl;

import java.sql.SQLException;
import java.util.List;

import com.clj.csmanagement.dto.Goods;
import com.clj.csmanagement.dto.GoodsDTO;
import com.clj.csmanagement.management.goodsManage.business.GoodsManagementBS;
import com.clj.csmanagement.management.goodsManage.dao.GoodsManagementDAO;
import com.clj.csmanagement.util.IDGenerator;

public class GoodsManagementBSImpl implements GoodsManagementBS {

	private GoodsManagementDAO goodsManagementDAO;
	public GoodsDTO queryGoodsInit() {
		GoodsDTO goodsDTO = new GoodsDTO();
		List<Goods> goodsList = goodsManagementDAO.queryGoodsInit();
		goodsDTO.setGoodsList(goodsList);
		return goodsDTO;
	}
	public GoodsDTO queryGoodsqx() {
		GoodsDTO goodsDTO = new GoodsDTO();
		List<Goods> goodsList = goodsManagementDAO.queryGoodsqx();
		goodsDTO.setGoodsList(goodsList);
		return goodsDTO;
	}
	public GoodsDTO queryGoodsInitM() {
		GoodsDTO goodsDTO = new GoodsDTO();
		List<Goods> goodsList = goodsManagementDAO.queryGoodsInitM();
		goodsDTO.setGoodsList(goodsList);
		return goodsDTO;
	}
	public GoodsDTO queryByType(String goodsType) {
		GoodsDTO goodsDTO = new GoodsDTO();
		List<Goods> goodsList = goodsManagementDAO.queryByType(goodsType);
		goodsDTO.setGoodsList(goodsList);
		return goodsDTO;
	}
	
	public GoodsDTO queryByName(String goodsName){
		GoodsDTO goodsDTO = new GoodsDTO();
		List<Goods> goodsList = goodsManagementDAO.queryByName(goodsName);
		goodsDTO.setGoodsList(goodsList);
		return goodsDTO;
	}
	public void addGoods(Goods goods) {
		String goods_id = IDGenerator.generateId();
		goodsManagementDAO.addGoods(goods,goods_id);
	}
	public void updateGoods(Goods goods) {
		goodsManagementDAO.updateGoods(goods);
	}
	public void goodsSelled(Goods goods) {
		goodsManagementDAO.goodsSelled(goods);
	}
	public void deleteGoods(Goods goods) {
		// TODO Auto-generated method stub
		goodsManagementDAO.deleteGoods(goods);
	}
	public void setGoodsManagementDAO(GoodsManagementDAO goodsManagementDAO) {
		this.goodsManagementDAO = goodsManagementDAO;
	}
	public GoodsManagementDAO getGoodsManagementDAO() {
		return goodsManagementDAO;
	}

}
