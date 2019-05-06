package com.sf.gis.cds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.gis.cds.common.constant.ResponeContant;
import com.sf.gis.cds.common.exception.MyException;
import com.sf.gis.cds.common.service.impl.BaseServiceImpl;
import com.sf.gis.cds.constant.AuditStatusEnum;
import com.sf.gis.cds.constant.${modelName}TypeEnum;
import com.sf.gis.cds.mapper.${modelName}Mapper;
import com.sf.gis.cds.model.${modelName};
import com.sf.gis.cds.req.${modelName}Req;
import com.sf.gis.cds.service.${modelName}AuditSettingService;
import com.sf.gis.cds.service.${modelName}Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ${modelName}ServiceImpl extends BaseServiceImpl<${modelName}, ${modelName}Req> implements ${modelName}Service {
	
	private ${modelName}Mapper ${modelName}Mapper;
	@Autowired
	private ${modelName}AuditSettingService ${modelName}AuditSettingService;

	@Autowired
	public ${modelName}ServiceImpl(${modelName}Mapper ${modelName}Mapper) {
		setBaseMapper(${modelName}Mapper);
		this.${modelName}Mapper = ${modelName}Mapper;
	}
	
}
