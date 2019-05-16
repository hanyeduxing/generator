package com.sf.gis.cds.common.manager.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.sf.gis.app.common.vo.RestResponse;
import com.sf.gis.cds.common.constant.ServiceConfig;
import com.sf.gis.cds.common.exception.MyException;
import com.sf.gis.cds.common.manager.BaseManager;
import com.sf.gis.cds.common.model.BaseModel;
import com.sf.gis.cds.common.model.Result;
import com.sf.gis.cds.common.req.BaseReq;
import com.sf.gis.cds.common.util.RestTemplateUtil;

public abstract class BaseManagerImpl<T extends BaseModel, REQ extends BaseReq> implements BaseManager<T, REQ> {

	/**
	 * 该模块的路径映射 与 资源池 Controller 中RequestMapping 一致
	 */
	protected String mapping;
	
	@Override
	public Integer insert(T record) {
		String url = String.format(ServiceConfig.INSERT_URL, ServiceConfig.POOL_SERVICE_URL, this.mapping);
		RestResponse<?> response = RestTemplateUtil.getRestTemplate().postForObject(url, record, RestResponse.class);
		if(response.isOk()) {
			return (Integer)response.getData();
		}else {
			throw new MyException(response.getMessage());
		}
	}

	@Override
	public Integer batchInsert(List<T> records) {
		String url = String.format(ServiceConfig.BATCH_INSERT_URL, ServiceConfig.POOL_SERVICE_URL, this.mapping);
		RestResponse<?> response = RestTemplateUtil.getRestTemplate().postForObject(url, records, RestResponse.class);
		if(response.isOk()) {
			return (Integer)response.getData();
		}else {
			throw new MyException(response.getMessage());
		}
	}

	@Override
	public Integer insertSelective(T record) {
		String url = String.format(ServiceConfig.INSERT_SELECTIVE_URL, ServiceConfig.POOL_SERVICE_URL, this.mapping);
		RestResponse<?> response = RestTemplateUtil.getRestTemplate().postForObject(url, record, RestResponse.class);
		if(response.isOk()) {
			return (Integer)response.getData();
		}else {
			throw new MyException(response.getMessage());
		}
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		String url = String.format(ServiceConfig.DELETE_URL, ServiceConfig.POOL_SERVICE_URL, this.mapping, id);
		RestResponse<?> response = RestTemplateUtil.getRestTemplate().getForObject(url, RestResponse.class);
		if(response.isOk()) {
			return (Integer)response.getData();
		}else {
			throw new MyException(response.getMessage());
		}
	}

	@Override
	public Integer updateByPrimaryKey(T record) {
		String url = String.format(ServiceConfig.UPDATE_URL, ServiceConfig.POOL_SERVICE_URL, this.mapping);
		RestResponse<?> response = RestTemplateUtil.getRestTemplate().postForObject(url, record, RestResponse.class);
		if(response.isOk()) {
			return (Integer)response.getData();
		}else {
			throw new MyException(response.getMessage());
		}
	}

	@Override
	public Integer updateByPrimaryKeySelective(T record) {
		String url = String.format(ServiceConfig.UPDATE_SELECTIVE_URL, ServiceConfig.POOL_SERVICE_URL, this.mapping);
		RestResponse<?> response = RestTemplateUtil.getRestTemplate().postForObject(url, record, RestResponse.class);
		if(response.isOk()) {
			return (Integer)response.getData();
		}else {
			throw new MyException(response.getMessage());
		}
	}

	@Override
	public T selectByPrimaryKey(String id) {
		String url = String.format(ServiceConfig.GET_BY_ID_URL, ServiceConfig.POOL_SERVICE_URL, this.mapping, id);
		Gson gson = RestTemplateUtil.getGson();
		String json = RestTemplateUtil.getRestTemplate().getForObject(url, String.class);
		Result<T> result = gson.fromJson(json, getResultOfT());
		if(result.getSuccess()) {
			return result.getData();
		}else {
			throw new MyException(result.getMessage());
		}
	}

	@Override
	public List<T> queryEntityList(REQ req) {
		String url = String.format(ServiceConfig.LIST_URL, ServiceConfig.POOL_SERVICE_URL, this.mapping);
		Gson gson = RestTemplateUtil.getGson();
		String json = RestTemplateUtil.getRestTemplate().postForObject(url, req, String.class);
		Result<PageInfo<T>> result = gson.fromJson(json, getResultPageInfoOfT());
		if(result.getSuccess()) {
			PageInfo<T> pageInfo = result.getData();
			Page<T> list = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
			list.setTotal(pageInfo.getTotal());
			list.addAll(pageInfo.getList());
			return list;
		}else {
			throw new MyException(result.getMessage());
		}
	}

	@Override
	public List<T> queryByIds(List<String> ids) {
		String url = String.format(ServiceConfig.GET_BY_IDS_URL, ServiceConfig.POOL_SERVICE_URL, this.mapping, String.join(",", ids));
		Gson gson = RestTemplateUtil.getGson();
		String json = RestTemplateUtil.getRestTemplate().getForObject(url, String.class);
		Result<List<T>> result = gson.fromJson(json, getResultListOfT());
		if(result.getSuccess()) {
			return result.getData();
		}else {
			throw new MyException(result.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private Class<T> getClazz(){
		return (Class<T>)getTypeOfT().getActualTypeArguments()[0];
	}
	
	private ParameterizedType getTypeOfT(){
		return (ParameterizedType)getClass().getGenericSuperclass();
	}
	
	private Type getResultOfT(){
		Type type = new ParameterizedTypeImpl(Result.class, new Class[] {getClazz()});
		return type;
	}
	
	private Type getResultListOfT(){
		Type listType = new ParameterizedTypeImpl(List.class, new Class[] {getClazz()});
		Type type = new ParameterizedTypeImpl(Result.class, new Type[] {listType});
		return type;
	}
	
	private Type getResultPageInfoOfT(){
		Type listType = new ParameterizedTypeImpl(PageInfo.class, new Class[] {getClazz()});
		Type type = new ParameterizedTypeImpl(Result.class, new Type[] {listType});
		return type;
	}
	
	
	private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
		private static final long serialVersionUID = 0;

		private final Type[] args;
	    private final Class<?> rawType;

	    ParameterizedTypeImpl(Class<?> rawType, Type[] typeArguments) {
	    	this.rawType = rawType;
	        this.args = typeArguments != null ? typeArguments : new Type[0];
	    }

	    @Override
	    public Type[] getActualTypeArguments() {
	      return args;
	    }

	    @Override
	    public Type getRawType() {
	      return rawType;
	    }

	    @Override
	    public Type getOwnerType() {
	      return null;
	    }
	  }
}
