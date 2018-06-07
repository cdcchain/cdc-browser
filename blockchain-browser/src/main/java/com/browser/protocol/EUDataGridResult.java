package com.browser.protocol;

import java.util.List;

/**
 * easyui分页列表POJO
 */
public class EUDataGridResult {

	private long total;
	private List<?> rows;
	private long pages;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public long getPages() {
		return pages;
	}
	public void setPages(long pages) {
		this.pages = pages;
	}
}
