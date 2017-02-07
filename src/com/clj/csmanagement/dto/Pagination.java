package com.clj.csmanagement.dto;

public class Pagination {


	private int start = 0;

	private int limit = 10;

	private int totalCount;

	/**
	 *
	 * @return 
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 *
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 *
	 * @return 
	 */
	public int getStart() {
		return start;
	}

	/**
	 *
	 * @param start
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 *
	 * @return 
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 *
	 * @param limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
