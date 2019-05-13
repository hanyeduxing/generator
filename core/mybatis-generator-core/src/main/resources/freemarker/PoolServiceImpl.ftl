package ${basePackage}.service.${package}.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${basePackage}.common.service.impl.BaseServiceImpl;
import ${basePackage}.dao.${package}.mapper.${modelName}Mapper;
import ${basePackage}.dao.${package}.model.${modelName};
import ${basePackage}.dao.${package}.req.${modelName}Req;
import ${basePackage}.service.${package}.${modelName}Service;

import lombok.extern.slf4j.Slf4j;

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
