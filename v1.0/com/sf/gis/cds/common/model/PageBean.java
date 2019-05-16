package com.sf.gis.cds.common.model;

import java.io.Serializable;
import java.util.List;


/**
 * 分页bean 01374035
 * @param <T>
 */
public class PageBean<T>  implements Serializable {

    private static final long serialVersionUID = 587754556498974978L;
    
    public int pageNo = 1;//当前页
    public int pageSize = 10;//每页显示条数
    public int totalPage;//总页数
    public long totalSize;//记录总数
    
	//查询结果对象
	private List<T> data;

	public PageBean() {
	}

	public PageBean(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
