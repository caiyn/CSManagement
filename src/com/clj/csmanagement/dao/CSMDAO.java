package com.clj.csmanagement.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.clj.csmanagement.dto.Pagination;
import com.clj.csmanagement.exception.DBException;

public class CSMDAO extends NamedParameterJdbcDaoSupport {

	private class EPPBatchPSSetter implements BatchPreparedStatementSetter{
		private List<Object[]> params = null;

		public EPPBatchPSSetter(List<Object[]> params) {
			this.params = params;
		}
		/**
		 * 
		 * <p>[匿名类的实现方法，取得批量记录总数]</p>
		 * 
		 */
		public int getBatchSize() {
			return params.size();
		}
		
		/**
		 * 
		 * <p>[设值方法]</p>
		 * 
		 */
		public void setValues(PreparedStatement ps, int i) throws SQLException {
			Object[] param = params.get(i);
			for (int j = 0; j < param.length; j++) {
				if (param[j] instanceof java.util.Date) {
					ps.setTimestamp(j + 1, new java.sql.Timestamp(((java.util.Date) param[j]).getTime()));
				} else {
					ps.setObject(j + 1, param[j]);
				}
			}
		}
	}
	/**
	 * 
	 * <p>[描述信息：操作LOB字段的回调基类]</p>
	 *
	 */
	protected abstract class ESSLobCreator extends AbstractLobCreatingPreparedStatementCallback {
		public ESSLobCreator() {
			super(getLobHandler());
		}
	}
	/**
	 * Lob类型数据操作器。
	 */
	protected LobHandler lobHandler = null;
	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}
	protected LobHandler getLobHandler() {
		return lobHandler;
	}
	
	/**
	 * 
	 * <p>[批量更新]</p>
	 * 
	 * @param sql : SQL语句
	 * @param params :参数的集合，按SQL语句中 ？的顺序进行匹配
	 * @throws DBException
	 * @return: void
	 */
	protected void batchUpdate(String sql, final List<Object[]> params) throws DBException {
		try {
			getJdbcTemplate().batchUpdate(sql, new EPPBatchPSSetter(params));
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * 
	 * <p>[批量更新</p>
	 * 
	 * @param sqls
	 * @param params
	 * @throws DBException
	 * @return: void
	 */
	protected void batchUpdate(List<String> sqls, final List<Object[]> params) throws DBException {
		try {
			if (sqls.size() != params.size())
				throw new DBException("SQL语句与参数不匹配！");
			Iterator<String> sqlIterator = sqls.iterator();
			Iterator<Object[]> paramsIterator = params.iterator();
			while (sqlIterator.hasNext() && paramsIterator.hasNext()) {
				this.update(sqlIterator.next(), paramsIterator.next());
			}
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	/**
	 * 
	 * <p>[执行更新操作，字段中带有LOB字段]</p>
	 * 
	 * @param sql
	 * @param psp
	 * @throws DBException
	 * @return: void
	 */
	protected void execute(String sql, PreparedStatementCallback psp) throws DBException {
		try {
			getJdbcTemplate().execute(sql, psp);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * 
	 * <p>[执行存储过程方法]</p>
	 * 
	 * @param callStatement
	 * @param inParams
	 * @param outParamsType
	 * @return
	 * @throws DBException
	 * @return: Object
	 */
	protected Object execute(String callStatement, final Object[] inParams, final int[] outParamsType)
	throws DBException {
		try {
			String sql = this.generateCallStatement(callStatement, inParams, outParamsType);
			Object ret = getJdbcTemplate().execute(sql, new CallableStatementCallback() {
		
				public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
					int inCnt = 0;
					if (inParams != null)
						inCnt = inParams.length;
					for (int i = 0; i < inCnt; i++) {
						cs.setObject(i + 1, inParams[i]);
					}
					if (outParamsType != null && outParamsType.length > 0)
						for (int j = 0; j < outParamsType.length; j++) {
							cs.registerOutParameter(inCnt + j + 1, outParamsType[j]);
						}
					cs.execute();
					Object[] result = null;
					if (outParamsType != null && outParamsType.length > 0) {
						result = new Object[outParamsType.length];
						for (int n = 0; n < outParamsType.length; n++) {
							result[n] = cs.getObject(inCnt + n + 1);
						}
					}
					return result;
				}
			});
			return ret;
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * 
	 * <p>[生成调用存储过程SQL语句]</p>
	 * 
	 * @param callStatement
	 * @param inParams
	 * @param ouinamsOarams
	 * @return
	 * @return: String
	 */
	private String generateCallStatement(String callStatement, Object[] inParams, int[] ouinamsOarams) {
		StringBuffer sb = new StringBuffer();
		sb.append("{ CALL ").append(callStatement);
		int paramsCnt = 0;
		if (inParams != null) {
			paramsCnt += inParams.length;
		}
		if (ouinamsOarams != null) {
			paramsCnt += ouinamsOarams.length;
		}
		if (paramsCnt > 0) {
			sb.append(" ( ");
			for (int i = 0; i < paramsCnt; i++) {
				sb.append("?,");
			}
			return sb.substring(0, sb.length() - 1) + " )}";
		} else {
			sb.append(" }");
		}
		return sb.toString();
	}
	/**
	 * 
	 * <p>[翻页操作方法]</p>
	 * 
	 * @param sql
	 * @param realCnt
	 * @param pagination
	 * @return
	 * @return: String
	 */
	private String generatePagingSql(String sql, int realCnt, Pagination pagination) {
		pagination = getLastPage(realCnt, pagination);
		return new StringBuffer("SELECT * FROM ( SELECT T.*, ROWNUM NUM FROM (").append(sql).append(
				") T WHERE ROWNUM <= ").append(pagination.getStart() + pagination.getLimit()).append(
				" ) TT  WHERE TT.NUM > ").append(pagination.getStart()).toString();
	}
	
	/**
	 * 
	 * <p>[反向翻页操作方法：倒序排列取每页数，再按照排序字段asc排列]</p>
	 * 
	 * @param sql
	 * @param realCnt
	 * @param pagination
	 * @param field
	 * @return
	 * @return: String
	 */
	private String generateReversePagingSql(String sql, int realCnt, Pagination pagination, String field) {
		pagination = getLastPage(realCnt, pagination);
		return new StringBuffer("SELECT * FROM ( SELECT T.*, ROWNUM NUM FROM (").append(sql).append(
				") T WHERE ROWNUM <= ").append(pagination.getStart() + pagination.getLimit()).append(
				" ) TT  WHERE TT.NUM > ").append(pagination.getStart()).append(" order by ").append(field).toString();
	}
	
	/**
	 * 
	 * <p>[将多个数据组成对象数组列表。]</p>
	 * 例如：
	 * <li>[A,B,C],X,[L,M,N] = > [A,X,L], [B,X,M], [C,X,N]
	 * <li>X,Y,Z = > [X,Y,Z]
	 * <li>[X,Y],A,[L,M,N] = > 出错，因为多个数组见长度不同。
	 * @param data ：参数需要为多个Object,如果需要传入
	 *            String[],请使用parseObjectArrayListForSingleParam方法。
	 * @return
	 * @return: List<Object[]>
	 */
	protected List<Object[]> parseObjectArrayListForMultiArrays(Object... data) {
		List<Object[]> list = new ArrayList<Object[]>();
		int temp = -1;
		boolean containArray = false;
		for (Object e : data) {
			if (e instanceof Object[]) {
				containArray = true;
				int length = ((Object[]) e).length;
				if (temp == -1)
					temp = length;
				Validate.isTrue(temp == length, "All of data which is type of Array has not the same size");
			}
		}

		if (!containArray) {
			Object[] objs = new Object[data.length];
			for (int i = 0; i < objs.length; i++)
				objs[i] = data[i];
			list.add(objs);
			return list;
		}

		for (int i = 0; i < temp; i++) {
			Object[] listE = new Object[data.length];
			for (int pos = 0; pos < data.length; pos++) {
				Object e = data[pos];
				Object ele = e;
				if (ele instanceof Object[]) {
					ele = ((Object[]) ele)[i];
				}
				listE[pos] = ele;
			}
			list.add(listE);
		}
		return list;
	}
	
	/**
	 * 
	 * <p>[将单个数据组成对象数组列表。]</p>
	 * 
	 * @param data：单个数组数据
	 * @return
	 * @return: List<Object[]>
	 */
	protected List<Object[]> parseObjectArrayListForSingleArray(Object[] data) {
		List<Object[]> list = new ArrayList<Object[]>();
		for (Object d : data) {
			list.add(new Object[] { d });
		}
		return list;
	}
	
	/**
	 * 
	 * <p>[查询]</p>
	 * 
	 * @param sql
	 * @param paramMap
	 * @param rowMapper
	 * @return
	 * @throws DBException
	 * @return: List
	 */
	protected List query(String sql, Map paramMap, RowMapper rowMapper) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().query(sql, paramMap, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected List query(String sql, Map paramMap, RowMapper rowMapper, Pagination pagination) throws DBException {
		try {
			int totalCount = 0;
			if (checkPagination(pagination)) {
				// sql = generatePagingSql(sql, pagination);
				totalCount = getCount(sql, paramMap);
				sql = generatePagingSql(sql, totalCount, pagination);
				pagination.setTotalCount(totalCount);
			}
			return getNamedParameterJdbcTemplate().query(sql, paramMap, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected List query(String sql, Object[] args, RowMapper rowMapper) throws DBException {
		try {
			return getJdbcTemplate().query(sql, args, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected List query(String sql, Object[] args, RowMapper rowMapper, Pagination pagination) throws DBException {
		try {
			int totalCount = 0;
			if (checkPagination(pagination)) {
				// sql = generatePagingSql(sql, pagination);
				totalCount = getCount(sql, args);
				sql = generatePagingSql(sql, totalCount, pagination);
				pagination.setTotalCount(totalCount);
			}
			return getJdbcTemplate().query(sql, args, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected List queryReverse(String sql, Object[] args, RowMapper rowMapper, Pagination pagination, String field)
	throws DBException {
		try {
			int totalCount = 0;
			if (checkPagination(pagination)) {
				totalCount = getCount(sql, args);
				sql = generateReversePagingSql(sql, totalCount, pagination, field);
				pagination.setTotalCount(totalCount);
			}
			return getJdbcTemplate().query(sql, args, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	/**
	 * 
	 * <p>[校验pagination是否合法]</p>
	 * 
	 * @param p
	 * @return
	 * @return: boolean
	 */
	private boolean checkPagination(Pagination p) {
		return p != null && p.getStart() >= 0 && p.getLimit() >= 0;
	}
	
	protected List query(String sql, RowMapper rowMapper) throws DBException {
		try {
			return getJdbcTemplate().query(sql, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected List query(String sql, RowMapper rowMapper, Pagination pagination) throws DBException {
		try {
			int totalCount = 0;
			if (checkPagination(pagination)) {
				// sql = generatePagingSql(sql, pagination);
				totalCount = getCount(sql);
				sql = generatePagingSql(sql, totalCount, pagination);
				pagination.setTotalCount(totalCount);
			}
			return getJdbcTemplate().query(sql, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected List query(String sql, SqlParameterSource paramSource, RowMapper rowMapper) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().query(sql, paramSource, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected List query(String sql, SqlParameterSource paramSource, RowMapper rowMapper, Pagination pagination)
	throws DBException {
		try {
			int totalCount = 0;
			if (checkPagination(pagination)) {
				// sql = generatePagingSql(sql, pagination);
				totalCount = getCount(sql, paramSource);
				sql = generatePagingSql(sql, totalCount, pagination);
				pagination.setTotalCount(totalCount);
			}
			return getNamedParameterJdbcTemplate().query(sql, paramSource, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected int queryForInt(String sql) throws DBException {
		try {
			return getJdbcTemplate().queryForInt(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected int queryForInt(String sql, Map paramMap) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForInt(sql, paramMap);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected int queryForInt(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().queryForInt(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected int queryForInt(String sql, SqlParameterSource paramSource) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForInt(sql, paramSource);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected List<Map<String, Object>> queryForList(String sql) throws DBException {
		try {
			return getJdbcTemplate().queryForList(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected List<Map<String, Object>> queryForList(String sql, Map paramMap) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	protected List<String> queryForStringList(String sql) throws DBException {
		try {
			return this.getJdbcTemplate().queryForList(sql, String.class);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}
	
	protected List<String> queryForStringList(String sql,Object[] params) throws DBException {
		try {
			return this.getJdbcTemplate().queryForList(sql, String.class,params);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * 
	 * <p>查询Map对象列表</p>
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws DBException
	 * @return: List<Map<String,Object>>
	 */
	protected List<Map<String, Object>> queryForList(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().queryForList(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * 带翻页参数的queryForList
	 * 
	 * @param sql
	 * @param args
	 * @param pagination
	 * @return
	 * @throws DBException
	 * @return: List<Map<String,Object>>
	 */
	protected List<Map<String, Object>> queryForList(String sql, Object[] args, Pagination pagination)
			throws DBException {
		try {
			int totalCount = 0;
			if (checkPagination(pagination)) {
				// sql = generatePagingSql(sql, pagination);
				totalCount = getCount(sql, args);
				sql = generatePagingSql(sql, totalCount, pagination);
				pagination.setTotalCount(totalCount);
			}
			return getJdbcTemplate().queryForList(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}


	protected long queryForLong(String sql) throws DBException {
		try {
			return getJdbcTemplate().queryForLong(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}


	protected long queryForLong(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().queryForLong(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}


	protected Map<String, Object> queryForMap(String sql) throws DBException {
		try {
			return getJdbcTemplate().queryForMap(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}


	protected Map<String, Object> queryForMap(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().queryForMap(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}


	protected Object queryForObject(String sql, Map paramMap, RowMapper rowMapper) throws DBException {
		try {
			List list = query(sql, paramMap, rowMapper);
			if (list.size() == 0)
				return null;
			return list.get(0);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	protected Object queryForObject(String sql, Object[] args, RowMapper rowMapper) throws DBException {
		try {
			List list = query(sql, args, rowMapper);
			if (list.size() == 0)
				return null;
			return list.get(0);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	protected Object queryForObject(String sql, RowMapper rowMapper) throws DBException {
		try {
			List list = query(sql, rowMapper);
			if (list.size() == 0)
				return null;
			return list.get(0);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	protected SqlRowSet queryForRowSet(String sql) throws DBException {
		try {
			return getJdbcTemplate().queryForRowSet(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	protected SqlRowSet queryForRowSet(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().queryForRowSet(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	protected int update(final String sql) throws DBException {
		try {
			return getJdbcTemplate().update(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}


	protected int update(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().update(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}


	protected String convertBoolean2String(Boolean b) {
		return DAOUtils.convertBoolean2String(b);
	}

	protected Boolean convertString2Boolean(String value, Boolean defaultValue) {
		return DAOUtils.convertString2Boolean(value, defaultValue);
	}

	protected boolean convertString2Boolean(String value) {
		return DAOUtils.convertString2Boolean(value, Boolean.FALSE);
	}

	/**
	 * 
	 * <p>[当前数据出现不同步的时候，需要查询到最后一页]</p>
	 * 
	 * @param realCnt
	 * @param p
	 * @return
	 * @return: Pagination
	 */
	private Pagination getLastPage(int realCnt, Pagination p) {
		if (p.getStart() < realCnt)
			return p;
		if (p.getLimit() > 0) {
			if (realCnt > 0 && realCnt % p.getLimit() == 0)
				p.setStart((realCnt - 1) / p.getLimit() * p.getLimit() + 1);
			else
				p.setStart(realCnt / p.getLimit() * p.getLimit() + 1);
		}
		return p;
	}

	private int getCount(String sql, Object[] args) {
		return this.queryForInt(generateCntSql(sql), args);
	}

	private int getCount(String sql, Map args) {
		return this.queryForInt(generateCntSql(sql), args);
	}

	private int getCount(String sql) {
		return this.queryForInt(generateCntSql(sql));
	}

	private int getCount(String sql, SqlParameterSource paramSource) {
		return this.queryForInt(generateCntSql(sql), paramSource);
	}

	private String generateCntSql(String sql) {
		return "SELECT COUNT(1) FROM ( " + sql + " ) TEMPTABLE";
	}
}
