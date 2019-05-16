package com.sf.gis.cds.common.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sf.gis.app.common.vo.RestResponse;
import com.sf.gis.cds.common.constant.ResponeContant;
import com.sf.gis.cds.common.exception.MyException;
import com.sf.gis.cds.common.model.BaseModel;
import com.sf.gis.cds.common.model.ServiceMessage;
import com.sf.gis.cds.common.req.BaseReq;
import com.sf.gis.cds.common.service.BaseService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseController<T extends BaseModel, REQ extends BaseReq> {

	private BaseService<T, REQ> baseService;
	/**
	 * 导出文件名称
	 */
	protected String tableName;
	/**
	 * 导出文件表头名称
	 */
	protected String[] thead;
	/**
	 * 导出文件列宽(Excel 实际列宽)
	 */
	protected int[] columnWidth;
	
	protected void setBaseService(BaseService<T, REQ> baseService) {
		this.baseService = baseService;
	}
	
	@ApiOperation("通过ID获取记录接口")
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResponse getById(@ApiParam(name = "id", value = "ID", required = true)@RequestParam(value="id")String id){
		try {
			T t = baseService.selectByPrimaryKey(id);
			if(t == null) {
				return RestResponse.error(ResponeContant.RECORD_NOT_GET);
			}
			return RestResponse.ok(t);
		}catch (MyException e) {
			log.info("根据ID获取记录错误：id=[{}], message=[{}]", id, e.getMessage());
			return RestResponse.error(e.getMessage());
		}catch(Exception e) {
			log.error("根据ID获取记录错误:id=[{}], message=[{}]", id, e.getMessage(), e);
			return RestResponse.error(ResponeContant.RECORD_READ_ERROR);
		}
	}
	
	@ApiOperation("通过查询条件分页获取列表接口")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public RestResponse list(@RequestBody REQ req) {
		try {
			preparedList(req);
			//判断是否分页
			if(REQ.DEF_PAGING.equals(req.getPaging())) {
				PageHelper.startPage(req.getPageNo(), req.getPageSize(), req.getSorter());
			}else {
				//如果不分页，最多获取10000条数据
				PageHelper.startPage(1, 10000, req.getSorter());
			}
			List<T> list = baseService.queryEntityList(req);
			PageInfo<T> page = new PageInfo<>(list);
			return RestResponse.ok(page);
		}catch (MyException e) {
			log.info("根据条件获取数据列表错误：req=[{}], message=[{}]", req, e.getMessage());
			return RestResponse.error(e.getMessage());
		}catch (Exception e) {
			log.error("根据条件获取数据列表错误:req=[{}], message=[{}]", req, e.getMessage(), e);
			return RestResponse.error(ResponeContant.RECORD_NOT_GET);
		}
	}
	
	@ApiOperation("通过ID删除记录接口")
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public RestResponse delete(@ApiParam(name = "id", value = "ID", required = true)@RequestParam(value="id")String id) {
		try {
			ServiceMessage sm = baseService.deleteByPrimaryKey(id);
			if(ServiceMessage.STATUS_ERROR.equals(sm.getStatus())) {
				return RestResponse.error(sm.getMessage());
			}
			return RestResponse.ok(sm.getResult());
		}catch (MyException e) {
			log.info("根据ID删除记录失败：id=[{}], message=[{}]", id, e.getMessage());
			return RestResponse.error(e.getMessage());
		}catch (Exception e) {
			log.error("根据ID删除记录失败：id=[{}], message=[{}]", id, e.getMessage(), e);
			return RestResponse.error(ResponeContant.DELETE_FAIL);
		}
	}
	
	
	@ApiOperation("新增记录接口")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public RestResponse insert(@RequestBody T record) {
		try {
			preparedInsert(record);
			ServiceMessage sm = baseService.insert(record);
			if(ServiceMessage.STATUS_ERROR.equals(sm.getStatus())) {
				return RestResponse.error(sm.getMessage());
			}
			return RestResponse.ok(sm.getResult());
		}catch (MyException e) {
			log.info("新增记录失败：record=[{}], message=[{}]", record, e.getMessage());
			return RestResponse.error(e.getMessage());
		}catch (Exception e) {
			log.error("新增记录失败：record=[{}], message=[{}]", record, e.getMessage(), e);
			return RestResponse.error(ResponeContant.INSERT_FAIL);
		}
	}
	
	@ApiOperation("更新记录接口")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody T record) {
		try {
			preparedUpdate(record);
			ServiceMessage sm = baseService.updateByPrimaryKey(record);
			if(ServiceMessage.STATUS_ERROR.equals(sm.getStatus())) {
				return RestResponse.error(sm.getMessage());
			}
			return RestResponse.ok(sm.getResult());
		}catch (MyException e) {
			log.info("更新记录失败：record=[{}], message=[{}]", record, e.getMessage());
			return RestResponse.error(e.getMessage());
		}catch (Exception e) {
			log.error("更新记录失败：record=[{}], message=[{}]", record, e.getMessage(), e);
			return RestResponse.error(ResponeContant.UPDATE_FAIL);
		}
	}
	
	@ApiOperation("保存记录接口")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public RestResponse save(@RequestBody T record) {
		try {
			if(StringUtils.isEmpty(record.getId())) {
				preparedInsert(record);
			}else {
				preparedUpdate(record);
			}
			ServiceMessage sm = baseService.save(record);
			if(ServiceMessage.STATUS_ERROR.equals(sm.getStatus())) {
				return RestResponse.error(sm.getMessage());
			}
			return RestResponse.ok(sm.getResult());
		}catch (MyException e) {
			log.info("保存记录失败：record=[{}], message=[{}]", record, e.getMessage());
			return RestResponse.error(e.getMessage());
		}catch (Exception e) {
			log.error("保存记录失败：record=[{}], message=[{}]", record, e.getMessage(), e);
			return RestResponse.error(ResponeContant.SAVE_FAIL);
		}
	}
	
	
	/**
	 * 新增前操作
	 */
	private void preparedInsert(T record) {
		baseService.preparedInsert(record);
	}
	
	/**
	 * 编辑前操作
	 */
	private void preparedUpdate(T record) {
		baseService.preparedUpdate(record);
	}
	
	/**
	 * 查询列表前操作
	 */
	private void preparedList(REQ req) {
		baseService.preparedList(req);
	}

	/**
	 * 获取表名
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 获取表头
	 * @return
	 */
	public String[] getThead() {
		return thead;
	}

	/**
	 * 获取列宽
	 * @return
	 */
	public int[] getColumnWidth() {
		return columnWidth;
	}
	
	/**
	 * 获取导出数据
	 * @param ids
	 * @param req
	 * @return
	 */
	public List<T> exportData(List<String> ids, REQ req) {
		List<T> list = null;
		if(CollectionUtils.isEmpty(ids)) {
			preparedList(req);
			list= baseService.queryEntityList(req);
		}else {
			list= baseService.queryByIds(ids);
		}
		return list;
	}
}
