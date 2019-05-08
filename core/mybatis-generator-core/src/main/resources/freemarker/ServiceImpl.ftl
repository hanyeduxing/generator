package ${basePackage}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${basePackage}.common.service.impl.BaseServiceImpl;
import ${basePackage}.mapper.${modelName}Mapper;
import ${basePackage}.model.${modelName};
import ${basePackage}.req.${modelName}Req;
import ${basePackage}.service.${modelName}Service;

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
