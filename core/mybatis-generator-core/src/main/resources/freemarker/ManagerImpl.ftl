package ${basePackage}.manager.impl;

import org.springframework.stereotype.Service;

import com.sf.gis.cds.common.constant.ServiceConfig;
import ${basePackage}.common.manager.impl.BaseManagerImpl;
import ${basePackage}.manager.${modelName}Manager;
import ${basePackage}.model.${modelName};
import ${basePackage}.req.${modelName}Req;

@Service
public class ${modelName}ManagerImpl extends BaseManagerImpl<${modelName}, ${modelName}Req> implements ${modelName}Manager {

    public ${modelName}ManagerImpl() {
        this.serviceUrl = ServiceConfig.POOL_SERVICE_URL;
        this.mapping = "${lowerModelName}";
    }
}
