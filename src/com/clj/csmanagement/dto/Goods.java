package com.clj.csmanagement.dto;

public class Goods {

	private String goods_name = null;
	
	private String goods_id = null;
	
	private String goods_code = null;
	
	private String goods_name_en = null;
	
	private int goods_num = 0;
	
	private int goods_sales_num = 0;
	
	private int goods_price = 0;
	
	private String goods_description = null;
	
	private String goods_type = null;

	private String goods_state = null;
	
	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goodsName) {
		goods_name = goodsName;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goodsId) {
		goods_id = goodsId;
	}

	public String getGoods_code() {
		return goods_code;
	}

	public void setGoods_code(String goodsCode) {
		goods_code = goodsCode;
	}

	public String getGoods_name_en() {
		return goods_name_en;
	}

	public void setGoods_name_en(String goodsNameEn) {
		goods_name_en = goodsNameEn;
	}

	public int getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(int goodsNum) {
		goods_num = goodsNum;
	}

	public int getGoods_sales_num() {
		return goods_sales_num;
	}

	public void setGoods_sales_num(int goodsSalesNum) {
		goods_sales_num = goodsSalesNum;
	}

	public int getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(int goodsPrice) {
		goods_price = goodsPrice;
	}

	public String getGoods_description() {
		return goods_description;
	}

	public void setGoods_description(String goodsDescription) {
		goods_description = goodsDescription;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goodsType) {
		goods_type = goodsType;
	}

	public void setGoods_state(String goods_state) {
		this.goods_state = goods_state;
	}

	public String getGoods_state() {
		return goods_state;
	}
	
}
