package org.webframe.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象(兼容easyui)
 * @author 张永葑
 * @param <T>
 */
public class Pager<T> {
	
	/**
	 * 当前页
	 */
	private Integer page;
	
	/**
	 * 查询结果的总条数
	 */
	private Long total;
	
	/**
	 * 总页数
	 */
	private Integer pageCount;
	
	/**
	 * 每页条数
	 */
	private Integer size;
	
	/**
	 * 分页数据
	 */
	private List<T> rows = new ArrayList<T>();
	
	/**
	 * 分页无参构造，默认第一页，每页10条
	 */
	public Pager() {
		setPage(1);
		setSize(10);
	}
	
	/**
	 * 有参构造，默认每页10条
	 * @param page 当前页
	 */
	public Pager(Integer page){
		setPage(page);
		setSize(10);
	}
	
	/**
	 * 有参构造
	 * @param page 当前页
	 * @param size 每页条数
	 */
	public Pager(Integer page, Integer size) {
		setPage(page);
		setSize(size);
	}

	/**
	 * 获取当前页
	 * @return 当前页
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * 设置当前页
	 * @param page 当前页
	 */
	public void setPage(Integer page) {
		if(page == null || page <= 0) {
			this.page = 1;
			return ;
		}
		this.page = page;
	}
	
	/**
	 * 获取查询结果总条数
	 * @return 结果总条数
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * 设置查询结果总条数
	 * @param total 总条数
	 */
	public void setTotal(Long total) {
		this.total = total;
		this.pageCount = (int) Math.ceil((double)total / size);
	}
	
	/**
	 * 获取总页数
	 * @return 总页数
	 */
	public Integer getPageCount() {
		return pageCount;
	}

	/**
	 * 获取每页条数
	 * @return 每页条数
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * 设置每页条数，如果参数小于或等于0则设置为10条
	 * @param size 每页条数
	 */
	public void setSize(Integer size) {
		if(size <= 0){
			this.size = 10;
			return;
		}
		this.size = size;
	}

	/**
	 * 获取分页数据
	 * @return 分页数据
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * 设置分页数据
	 * @param rows 分页数据
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
