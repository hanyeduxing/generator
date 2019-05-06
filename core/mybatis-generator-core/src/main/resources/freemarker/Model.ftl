package com.sf.gis.cds.model;

import com.sf.gis.cds.common.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
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
    
    <#foreach item="${columns}" var="col">
    
    /**
     * 方案编号
     */
    @ApiModelProperty(value = "${col.remarks}", position = ${col_index})
    private String ${modelName}Code;
    <#/foreach>

}