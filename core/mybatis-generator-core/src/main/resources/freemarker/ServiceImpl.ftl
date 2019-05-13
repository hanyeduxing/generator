package ${basePackage}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${basePackage}.common.service.impl.BaseServiceImpl;
import ${basePackage}.manager.${modelName}Manager;
import ${basePackage}.model.${modelName};
import ${basePackage}.req.${modelName}Req;
import ${basePackage}.service.${modelName}Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ${modelName}ServiceImpl extends BaseServiceImpl<${modelName}, ${modelName}Req> implements ${modelName}Service {
    
    private ${modelName}Manager ${lowerModelName}Manager;

    @Autowired
    public ${modelName}ServiceImpl(${modelName}Manager ${lowerModelName}Manager) {
        setBaseManager(${lowerModelName}Manager);
        this.${lowerModelName}Manager = ${lowerModelName}Manager;
    }
    
}
