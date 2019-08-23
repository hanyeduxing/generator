package com.sf.gis.cds.common.service.impl;

import java.util.List;

import org.springframework.jdbc.UncategorizedSQLException;

import com.sf.gis.cds.common.constant.ResponeContant;
import com.sf.gis.cds.common.exception.MyException;
import com.sf.gis.cds.common.model.ServiceMessage;
import com.sf.gis.cds.common.req.BaseReq;
import com.sf.gis.cds.common.service.BaseService;
import com.sf.gis.cds.common.util.GenerateUUID;
import com.sf.gis.cds.common.util.StringUtil;
import com.sf.gis.cds.common.mapper.BaseMapper;
import com.sf.gis.cds.common.model.BaseModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseServiceImpl<T extends BaseModel, REQ extends BaseReq> implements BaseService<T, REQ> {
	
	private BaseMapper<T, REQ> baseMapper;
	
	protected void setBaseMapper(BaseMapper<T, REQ> baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public ServiceMessage insert(T record) {
		try {
			//设置ID
			record.setId(GenerateUUID.getUUID());
			Integer result = baseMapper.insert(record);
			return new ServiceMessage(ServiceMessage.STATUS_SUCC, ResponeContant.INSERT_SUCC, result);
		}catch (Exception e) {
			log.error("[{}][{}] message=[{}]", record.getClass().getSimpleName(), ResponeContant.INSERT_FAIL, e.getMessage(), e);
			if(e instanceof UncategorizedSQLException && e.getCause() != null) {
				return new ServiceMessage(ServiceMessage.STATUS_ERROR, e.getCause().getMessage());
			}
			return new ServiceMessage(ServiceMessage.STATUS_ERROR, ResponeContant.INSERT_FAIL);
		}
	}

	@Override
	public ServiceMessage insertSelective(T record) {
		try {
			//设置ID
			record.setId(GenerateUUID.getUUID());
			Integer result = baseMapper.insertSelective(record);
			return new ServiceMessage(ServiceMessage.STATUS_SUCC, ResponeContant.INSERT_SUCC, result);
		}catch (Exception e) {
			log.error("[{}][{}] message=[{}]", record.getClass().getSimpleName(), ResponeContant.INSERT_FAIL, e.getMessage(), e);
			if(e instanceof UncategorizedSQLException && e.getCause() != null) {
				return new ServiceMessage(ServiceMessage.STATUS_ERROR, e.getCause().getMessage());
			}
			return new ServiceMessage(ServiceMessage.STATUS_ERROR, ResponeContant.INSERT_FAIL);
		}
	}
	
	@Override
	public ServiceMessage updateByPrimaryKey(T record) {
		try {
			//判断数据是否存在
			if(!checkExist(record.getId())) {
				return new ServiceMessage(ServiceMessage.STATUS_ERROR, ResponeContant.MAIN_DEMAND_NOT_FOUND);
			}
			Integer result = baseMapper.updateByPrimaryKey(record);
			return new ServiceMessage(ServiceMessage.STATUS_SUCC, ResponeContant.UPDATE_SUCC, result);
		}catch (Exception e) {
			log.error("[{}][{}] message=[{}]", record.getClass().getSimpleName(), ResponeContant.UPDATE_FAIL, e.getMessage(), e);
			if(e instanceof UncategorizedSQLException && e.getCause() != null) {
				return new ServiceMessage(ServiceMessage.STATUS_ERROR, e.getCause().getMessage());
			}
			return new ServiceMessage(ServiceMessage.STATUS_ERROR, ResponeContant.UPDATE_FAIL);
		}
	}

	@Override
	public ServiceMessage updateByPrimaryKeySelective(T record) {
		try {
			//判断数据是否存在
			if(!checkExist(record.getId())) {
				return new ServiceMessage(ServiceMessage.STATUS_ERROR, ResponeContant.MAIN_DEMAND_NOT_FOUND);
			}
			Integer result = baseMapper.updateByPrimaryKeySelective(record);
			return new ServiceMessage(ServiceMessage.STATUS_SUCC, ResponeContant.UPDATE_SUCC, result);
		}catch (Exception e) {
			log.error("[{}][{}] message=[{}]", record.getClass().getSimpleName(), ResponeContant.UPDATE_FAIL, e.getMessage(), e);
			if(e instanceof UncategorizedSQLException && e.getCause() != null) {
				return new ServiceMessage(ServiceMessage.STATUS_ERROR, e.getCause().getMessage());
			}
			return new ServiceMessage(ServiceMessage.STATUS_ERROR, ResponeContant.UPDATE_FAIL);
		}
	}

	@Override
	public ServiceMessage deleteByPrimaryKey(String id) {
		try {
			//判断数据是否存在
			T m = baseMapper.selectByPrimaryKey(id);
			if(m == null) {
				return new ServiceMessage(ServiceMessage.STATUS_ERROR, ResponeContant.MAIN_DEMAND_NOT_FOUND);
			}
			Integer result = baseMapper.deleteByPrimaryKey(id);
			return new ServiceMessage(ServiceMessage.STATUS_SUCC, ResponeContant.DELETE_SUCC, result);
		}catch (Exception e) {
			log.error("[{}][{}] message=[{}]", baseMapper.getClass().getSimpleName(), ResponeContant.DELETE_FAIL, e.getMessage(), e);
			if(e instanceof UncategorizedSQLException && e.getCause() != null) {
				return new ServiceMessage(ServiceMessage.STATUS_ERROR, e.getCause().getMessage());
			}
			return new ServiceMessage(ServiceMessage.STATUS_ERROR, ResponeContant.DELETE_FAIL);
		}
	}

	@Override
	public T selectByPrimaryKey(String id) {
		return baseMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<T> queryEntityList(REQ record) {
		return baseMapper.queryEntityList(record);
	}

	@Override
	public ServiceMessage save(T record) {
		try {
			if(StringUtil.isEmpty(record.getId())) {
				return this.insert(record);
			}else {
				return this.updateByPrimaryKey(record);
			}
		}catch (Exception e) {
			log.error("[{}][{}] message=[{}]", record.getClass().getSimpleName(), ResponeContant.SAVE_FAIL, e.getMessage(), e);
			if(e instanceof UncategorizedSQLException && e.getCause() != null) {
				return new ServiceMessage(ServiceMessage.STATUS_ERROR, e.getCause().getMessage());
			}
			return new ServiceMessage(ServiceMessage.STATUS_ERROR, ResponeContant.SAVE_FAIL);
		}
	}

	@Override
	public List<T> queryByIds(List<String> ids) {
		return baseMapper.queryByIds(ids);
	}

	@Override
	public boolean checkExist(String id) {
		try {
			//判断数据是否存在
			T m = this.selectByPrimaryKey(id);
			if(m == null) {
				return false;
			}
		}catch (Exception e) {
			log.error("[{}][{}]:id=[{}], message=[{}]", baseMapper.getClass().getSimpleName(), id, "检查数据是否存在失败", e.getMessage(), e);
			throw new MyException("检查数据是否存在失败");
		}
		return true;
	}
	
}