package com.sf.gis.cds.common.mapper;

import java.util.ArrayList;
import java.util.List;

import com.sf.gis.cds.common.req.BaseReq;
import com.sf.gis.cds.common.model.BaseModel;


/**
 * K 条件查询列表  T 查询对象
 * @param <T>
 */
public interface BaseMapper<T extends BaseModel, REQ extends BaseReq> {
	
	/**
	 * 新增
	 * @param record 记录
	 * @return Integer 成功条数  
	 */
	Integer insert(T record);
	
	/**
	 * 新增
	 * @param record 记录
	 * @return Integer 成功条数  
	 */
	Integer insertSelective(T record);
	
	/**
	 * 按主键删除
	 * @param id
	 * @return
	 */
    Integer deleteByPrimaryKey(String id);

   /**
    * 主键更新
    * @param obj
    * @return
    */
    Integer updateByPrimaryKey(T record);
    
    /**
     * 主键更新
     * @param obj
     * @return
     */
    Integer updateByPrimaryKeySelective(T record);
	
	/**
	 * 主键查询
	 * @param entity
	 * @return
	 */
	T selectByPrimaryKey(String id);

	
	/**
	 * 条件查询
	 * @param obj
	 * @return
	 */
	List<T> queryEntityList(REQ req);

	/**
	 * 按照ID集合查询列表
	 * @param ids
	 * @return
	 */
	default List<T> queryByIds(List<String> ids) {
		return new ArrayList<>();
	}
}
