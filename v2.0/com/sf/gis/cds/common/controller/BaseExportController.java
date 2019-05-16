package com.sf.gis.cds.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sf.gis.app.common.vo.RestResponse;
import com.sf.gis.cds.common.model.BaseModel;
import com.sf.gis.cds.common.req.BaseReq;
import com.sf.gis.cds.common.util.ExcelUtils;
import com.sf.gis.cds.common.util.StringUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

public interface BaseExportController<T extends BaseModel, REQ extends BaseReq> {

	/**
	 * 导出功能步骤
	 * 1、实现mapper中queryByIds方法（如果需要导出选中功能）
	 * 2、设置tableName、thead、columnWidth
	 * 3、重写castExportData 方法 组装数据
	 * @param response
	 * @param req
	 * @param ids
	 * @return
	 */
	@ApiOperation("导出Excel接口(按查询条件导出、或特定ID记录)")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	default RestResponse<?> export(HttpServletResponse response, @RequestBody REQ req, 
			@ApiParam(value = "选中记录ID", required = false)@RequestParam(required = false) List<String> ids) {
		if(StringUtil.isEmpty(getTableName()) || getThead() == null || getColumnWidth() == null) {
			return RestResponse.error("导出功能不可用");
		}
		List<T> list = exportData(ids, req);
		
		List<List<String>> dataList = new ArrayList<>();
		for(T m :list) {
			dataList.add(castExportData(m));
		}
		ExcelUtils.exportExcel(response, getTableName(), getThead(), getColumnWidth(), dataList);
		return null;
	}
	
	/**
	 * 获取导出数据
	 * @param ids
	 * @param req
	 * @return
	 */
	List<T> exportData(List<String> ids, REQ req);


	/**
	 * 将数据组装到集合中进行导出（长度和表头一致）
	 * 需要导出功能时重写
	 * @param m
	 * @return
	 */
	List<String> castExportData(T m);
	
	/**
	 * 获取表名
	 * @return
	 */
	String getTableName();
	
	/**
	 * 获取表头
	 * @return
	 */
	String[] getThead();
	
	/**
	 * 获取列宽
	 * @return
	 */
	int[] getColumnWidth();
}
