package ${basePackage}.model;

import ${basePackage}.common.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#list importList  as var>
import ${var};
</#list>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel(description = "${remarks}")
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ${modelName} extends BaseModel {
    

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    <#list columns  as col>
    
    /**
     * ${col.remarks!}
     */
    @ApiModelProperty(value = "${col.remarks!}", position = ${col_index+1})
    private ${col.fullyQualifiedJavaType.shortName} ${col.javaProperty};
    </#list>

}