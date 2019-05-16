package com.sf.gis.cds.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {

	private Boolean success;
	
	private String message;
	
	private T data;
}
