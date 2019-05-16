package com.sf.gis.cds.common.model;

import java.io.Serializable;
import java.util.List;


public class PageInfo<T>  implements Serializable {

    private static final long serialVersionUID = 587754556498974978L;
    
    public int currentPage = 1;//当前页
    public int pageSize = 10;//每页显示条数
    public int totalPage;//总页数
    public int totalSize;//记录总数
    
    //条件查询对象
	private T clazz ;
	
	//查询结果对象
	private List<T> listObj;
    
    //得到当前页
	//当前页如果为0,则默认值为1
	public int getCurrentPage() {
		return currentPage == 0 ? 1 : (int)Math.ceil(currentPage);
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage == 0 ? 1 : (int)Math.ceil(currentPage);
	}
	
    public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize =  pageSize == 0 ? 1 : (int)Math.ceil(pageSize);;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	//计算总页数
	public int getTotalPage() {
		int size = getTotalSize() / getPageSize();
		int mod = getTotalSize()  % getPageSize();
	        if (mod != 0) {
	            size++;
	        }
        return  getTotalSize() == 0 ? 1 : (int)Math.ceil(size);
	}

	private int currentResult;
	
	public int getCurrentResult() {
		//起始位置 	(当前页数-1)*每页显示多少条
		return (getCurrentPage()-1) * getPageSize();
	}

	public T getClazz() {
		return clazz;
	}

	public void setClazz(T clazz) {
		this.clazz = clazz;
	}

	public List<T> getListObj() {
		return listObj;
	}

	public void setListObj(List<T> listObj) {
		this.listObj = listObj;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	
	
	
	
}
