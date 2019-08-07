package ${basePackage}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${basePackage}.common.service.impl.BaseServiceImpl;
import ${basePackage}.dao.mapper.${modelName}Mapper;
import ${basePackage}.dao.model.${modelName};
import ${basePackage}.dao.req.${modelName}Req;
import ${basePackage}.service.${modelName}Service;

import lombok.extern.slf4j.Slf4j;

/**
*
* @author ${author}
* @date ${date}
*/
@Slf4j
@Service
public class ${modelName}ServiceImpl extends BaseServiceImpl<${modelName}, ${modelName}Req> implements ${modelName}Service {
    
    private ${modelName}Mapper ${lowerModelName}Mapper;

    @Autowired
    public ${modelName}ServiceImpl(${modelName}Mapper ${lowerModelName}Mapper) {
        setBaseMapper(${lowerModelName}Mapper);
        this.${lowerModelName}Mapper = ${lowerModelName}Mapper;
    }
    
}
