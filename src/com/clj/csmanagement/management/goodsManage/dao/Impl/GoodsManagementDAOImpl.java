package com.clj.csmanagement.management.goodsManage.dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.clj.csmanagement.dao.CSMDAO;
import com.clj.csmanagement.dto.Goods;
import com.clj.csmanagement.management.goodsManage.dao.GoodsManagementDAO;

public class GoodsManagementDAOImpl extends CSMDAO implements GoodsManagementDAO {
	
	private static final String SQL_QUERY_GOODS = "SELECT * FROM goods where GOODS_STATE = '1' order by GOODS_SALES_NUM desc";
	
	private static final String SQL_QUERY_GOODS_QH = "SELECT * FROM goods where GOODS_NUM = 0 order by GOODS_SALES_NUM desc";
	
	private static final String SQL_QUERY_GOODS_M = "SELECT * FROM goods order by GOODS_SALES_NUM desc";
	
	private static final String SQL_QUERY_GOODS_BY_TYPE = "SELECT * FROM goods WHERE GOODS_STATE = '1' and GOODS_TYPE=? order by GOODS_SALES_NUM desc";
	
	private static final String SQL_QUERY_GOODS_BY_NAME = "SELECT * FROM goods WHERE GOODS_STATE = '1' and ";
	
	private static final String SQL_ADDGOODS = "INSERT INTO goods (GOODS_NAME,GOODS_NUM,GOODS_PRICE,GOODS_TYPE,GOODS_DESCRIPTION,GOODS_ID) VALUES (?,?,?,?,?,?)";
	
	private static final String SQL_UPDATEGOODS = "update goods set GOODS_NAME=? , GOODS_NUM = ? ,GOODS_PRICE=? , GOODS_TYPE = ?,GOODS_STATE=? , GOODS_DESCRIPTION = ?where GOODS_ID=?";
	
	private static final String SQL_UPDATEGOODS_SELLED = "update goods set GOODS_NUM = ? ,GOODS_SALES_NUM = ? where GOODS_ID=?";
	
	private static final String SQL_DELETEGOODS = "DELETE FROM goods WHERE GOODS_ID = ?";
	
	private static class GoodsRowMapper implements RowMapper<Goods>{

		public Goods mapRow(ResultSet rs, int rowNumber) throws SQLException {
			Goods goods = new Goods();
			goods.setGoods_code(rs.getString("GOODS_CODE"));
			goods.setGoods_description(rs.getString("GOODS_DESCRIPTION"));
			goods.setGoods_id(rs.getString("GOODS_ID"));
			goods.setGoods_name(rs.getString("GOODS_NAME"));
			goods.setGoods_name_en(rs.getString("GOODS_NAME_EN"));
			goods.setGoods_num(rs.getInt("GOODS_NUM"));
			goods.setGoods_price(rs.getInt("GOODS_PRICE"));
			goods.setGoods_sales_num(rs.getInt("GOODS_SALES_NUM"));
			goods.setGoods_type(rs.getString("GOODS_TYPE"));
			goods.setGoods_state(rs.getString("GOODS_STATE"));
			return goods;
		}
		
	}
	public List<Goods> queryGoodsInit() {
		// TODO Auto-generated method stub
		return this.query(SQL_QUERY_GOODS, new GoodsRowMapper());
	}
	public List<Goods> queryGoodsqx() {
		// TODO Auto-generated method stub
		return this.query(SQL_QUERY_GOODS_QH, new GoodsRowMapper());
	}
	public List<Goods> queryGoodsInitM() {
		// TODO Auto-generated method stub
		return this.query(SQL_QUERY_GOODS_M, new GoodsRowMapper());
	}
	public List<Goods> queryByType(String goodsType) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] { goodsType};
		return this.query(SQL_QUERY_GOODS_BY_TYPE,params, new GoodsRowMapper());
	}
	public List<Goods> queryByName(String goodsName){
		String sql = SQL_QUERY_GOODS_BY_NAME;
		sql+=" GOODS_NAME like '%"+goodsName+"%' order by GOODS_SALES_NUM desc";
		return this.query(sql, new GoodsRowMapper());
	}
	public void addGoods(Goods goods, String goodsId) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] { goods.getGoods_name(),goods.getGoods_num(),goods.getGoods_price(),goods.getGoods_type(),goods.getGoods_description(),goodsId};
		this.update(SQL_ADDGOODS, params);
	}
	public void updateGoods(Goods goods) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] { goods.getGoods_name(),goods.getGoods_num(),goods.getGoods_price(),goods.getGoods_type(),goods.getGoods_state(),goods.getGoods_description(),goods.getGoods_id()};
		this.update(SQL_UPDATEGOODS, params);
	}
	public void goodsSelled(Goods goods) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] { goods.getGoods_num(),goods.getGoods_sales_num(),goods.getGoods_id()};
		this.update(SQL_UPDATEGOODS_SELLED, params);
	}
	public void deleteGoods(Goods goods) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] { goods.getGoods_id()};
		this.update(SQL_DELETEGOODS, params);
	}

}
