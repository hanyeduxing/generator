package com.sf.gis.cds.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sf.gis.app.common.vo.RestResponse;
import com.sf.gis.cds.common.constant.ResponeContant;
import com.sf.gis.cds.common.controller.BaseController;
import com.sf.gis.cds.common.controller.BaseExportController;
import com.sf.gis.cds.common.exception.MyException;
import com.sf.gis.cds.common.model.ServiceMessage;
import com.sf.gis.cds.common.util.DateUtil;
import com.sf.gis.cds.common.util.StringUtil;
import com.sf.gis.cds.constant.AuditStatusEnum;
import com.sf.gis.cds.model.${modelName};
import com.sf.gis.cds.req.${modelName}Req;
import com.sf.gis.cds.service.${modelName}Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "${remarks}管理接口")
@Slf4j
@RestController
@RequestMapping("/${modelName}")
public class ${modelName}Controller extends BaseController<${modelName}, ${modelName}Req> implements BaseExportController<${modelName}, ${modelName}Req>{
  
	private ${modelName}Service ${modelName}Service;

	@Autowired
	public ${modelName}Controller(${modelName}Service ${modelName}Service) {
		this.${modelName}Service = ${modelName}Service;
		setBaseService(${modelName}Service);
	}
    
}
