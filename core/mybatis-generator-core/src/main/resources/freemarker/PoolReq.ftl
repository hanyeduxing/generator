package ${basePackage}.dao.${package}.req;

import ${basePackage}.common.req.BaseReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#list reqImportList  as var>
import ${var};
</#list>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel(description = "${remarks}列表查询条件")
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ${modelName}Req extends BaseReq {

    <#list reqColumns  as col>
    
    /**
     * ${col.remarks}
     */
    @ApiModelProperty(value = "${col.remarks}", position = ${col_index+1})
    private ${col.fullyQualifiedJavaType.shortName} ${col.javaProperty};
    </#list>
}
