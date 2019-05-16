package com.sf.gis.cds.common.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 唯一标识
	 */
	@ApiModelProperty(value = "唯一标识", position = 0)
	private String id;
}
