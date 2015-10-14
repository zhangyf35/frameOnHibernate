package org.webframe.common;

import java.util.List;

/**
 * 分页对象(兼容easyui)
 * @author 张永葑
 * @param <T>
 */
public class Pager<T> {
	
	private int page; // 当前页
	
	private long total; // 总条数
	
	private long pageCount; //总页数
	
	private int size; // 每页条数
	
	private List<T> rows; // 分页数据
	
	public Pager() {
		setPage(1);
		setSize(10);
	}
	
	public Pager(int page){
		setPage(page);
		setSize(10);
	}
	
	public Pager(int page, int size) {
		setPage(page);
		setSize(size);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if(page < 0){
			this.page = 1;
		}
		this.page = page;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		this.pageCount = (long) Math.ceil((double)total / size);
	}
	
	public long getPageCount() {
		return pageCount;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if(size <= 0){
			return;
		}
		this.size = size;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
