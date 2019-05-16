package com.sf.gis.cds.common.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by 01115468 on 2018/2/2.
 */
@Data
@ToString
public class ServiceMessage implements Serializable {

    /**
     * 成功
     */
    public static final String STATUS_SUCC = "S";
    /**
     * 失败
     */
    public static final String STATUS_ERROR = "E";

    private String type;
    private String status;//S E
    private String message;
    private Date creatTime;
    private Map<String, Object> resultData;   //扩展字段，返回
    private Object result;

    public ServiceMessage(String type, String status, String message) {
        super();
        this.type = type;
        this.status = status;
        this.message = message;
        this.creatTime = new Date();
    }
    public ServiceMessage(String status, String message) {
        super();
        this.status = status;
        this.message = message;
        this.creatTime = new Date();
    }
    public ServiceMessage(String status, String message, Map<String, Object> resultData) {
        super();
        this.status = status;
        this.message = message;
        this.resultData = resultData;
        this.creatTime = new Date();
    }
    public ServiceMessage(String status, String message, Object  result) {
		 super();
	     this.status = status;
	     this.message = message;
	     this.result = result;
		 this.creatTime = new Date();
    }
    public ServiceMessage() {
        super();
    }
}
