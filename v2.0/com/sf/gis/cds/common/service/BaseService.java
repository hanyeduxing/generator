package com.sf.gis.cds.common.service;


import java.util.List;

import com.sf.gis.cds.common.model.ServiceMessage;
import com.sf.gis.cds.common.req.BaseReq;


public interface BaseService<T, REQ extends BaseReq> {
	
	/**
	 * 新增
	 * @param record 记录
	 * @return Integer 成功条数  
	 */
	ServiceMessage insert(T record);
	
	/**
	 * 批量新增
	 * @param records 记录集合
	 * @return Integer 成功条数  
	 */
	ServiceMessage batchInsert(List<T> records);
	
	/**
	 * 新增
	 * @param record 记录
	 * @return Integer 成功条数  
	 */
	ServiceMessage insertSelective(T record);
	
	/**
	 * 按主键删除
	 * @param id
	 * @return
	 */
	ServiceMessage deleteByPrimaryKey(String id);

   /**
    * 主键更新
    * @param record
    * @return
    */
	ServiceMessage updateByPrimaryKey(T record);
	
	/**
	 * 主键更新
	 * @param obj
	 * @return
	 */
	ServiceMessage updateByPrimaryKeySelective(T record);
	
	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(String id);

	/**
	 * 查询所有
	 * @param record
	 * @return
	 */
	List<T> queryEntityList(REQ req);
	
	/**
	 * 通过ID集合查询
	 * @param record
	 * @return
	 */
	List<T> queryByIds(List<String> ids);

	/**
	 * 记录保存接口（新增/更新）
	 * @param record
	 * @return
	 */
	ServiceMessage save(T record);

	/**
	 * 通过ID判断数据是否存在
	 * @param id
	 * @return
	 */
	boolean checkExist(String id);

	/**
	 * 新增前操作
	 */
	default void preparedInsert(T record) {
		
	}
	
	/**
	 * 编辑前操作
	 */
	default void preparedUpdate(T record) {
		
	}
	
	/**
	 * 查询列表前操作
	 */
	default void preparedList(REQ req) {
		
	}
}
