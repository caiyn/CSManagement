package com.clj.csmanagement.management.goodsManage.action;


import com.clj.csmanagement.common.CSMAction;
import com.clj.csmanagement.dto.Goods;
import com.clj.csmanagement.dto.GoodsDTO;
import com.clj.csmanagement.management.goodsManage.business.GoodsManagementBS;

public class GoodsManagementAC extends CSMAction {

	private static final long serialVersionUID = 7795306685697928298L;
	
	private GoodsDTO goodsDTO;
	
	private String goodsType;
	
	private String goodsName;
	
	private Goods goods;
	
	private GoodsManagementBS goodsManagementBS;
//	public String getCurrentUserName(){
//		userName = CSMContext.getInstance().getUserName();
//		return JSON;
//	}
	
	public String queryGoodsInit(){
		goodsDTO = goodsManagementBS.queryGoodsInit();
		return JSON;
	}
	public String queryGoodsqx(){
		goodsDTO = goodsManagementBS.queryGoodsqx();
		return JSON;
	}
	public String queryGoodsInitM(){
		goodsDTO = goodsManagementBS.queryGoodsInitM();
		return JSON;
	}
	public String queryByType(){
		goodsDTO = goodsManagementBS.queryByType(goodsType);
		return JSON;
	}
	
	public String addGoods(){
		goodsManagementBS.addGoods(goods);
		return JSON;
	}
	public String updateGoods(){
		goodsManagementBS.updateGoods(goods);
		return JSON;
	}
	public String goodsSelled(){
		goodsManagementBS.goodsSelled(goods);
		return JSON;
	}
	public String queryByName(){
		goodsDTO = goodsManagementBS.queryByName(goodsName);
		return JSON;
	}
	public String deleteGoods(){
		goodsManagementBS.deleteGoods(goods);
		return JSON;
	}
	public void setGoodsDTO(GoodsDTO goodsDTO) {
		this.goodsDTO = goodsDTO;
	}
	public GoodsDTO getGoodsDTO() {
		return goodsDTO;
	}

	public void setGoodsManagementBS(GoodsManagementBS goodsManagementBS) {
		this.goodsManagementBS = goodsManagementBS;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Goods getGoods() {
		return goods;
	}

	
}
