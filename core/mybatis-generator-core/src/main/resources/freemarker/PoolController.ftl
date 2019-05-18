package ${basePackage}.web.controller.${package};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${basePackage}.common.controller.BaseController;
import ${basePackage}.dao.${package}.model.${modelName};
import ${basePackage}.dao.${package}.req.${modelName}Req;
import ${basePackage}.service.${package}.${modelName}Service;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "质检_${remarks}管理接口")
@Slf4j
@RestController
@RequestMapping("/${lowerModelName}")
public class ${modelName}Controller extends BaseController<${modelName}, ${modelName}Req> {
  
    private ${modelName}Service ${lowerModelName}Service;

    @Autowired
    public ${modelName}Controller(${modelName}Service ${lowerModelName}Service) {
        this.${lowerModelName}Service = ${lowerModelName}Service;
        setBaseService(${lowerModelName}Service);
    }
    
}
