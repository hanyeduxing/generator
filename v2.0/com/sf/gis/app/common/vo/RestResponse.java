package com.sf.gis.app.common.vo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.sf.gis.app.common.constant.ExceptionEnums;
import com.sf.gis.app.common.utils.JacksonUtil;

import lombok.NoArgsConstructor;

/**
 * ResponseBody构造器。一般用于ajax、rest等类型的Web服务
 */
@SuppressWarnings("all")
@NoArgsConstructor
public class RestResponse<T> extends HashMap<String, Object> implements Serializable {
	private final static int COMMON_SUCCESS_CODE = 200;
	private final static int COMMON_ERROR_CDOE = 500;

	public RestResponse(int code, boolean success, String subCode, String message, T data) {
		super();
		this.setAny("code", code);
		this.setAny("success", success);
		this.setAny("subCode", subCode);
		this.setAny("message", message);
		this.setAny("data", data);
	}

	public static RestResponse ok() {
		return ok(null);
	}

	public static <T> RestResponse ok(T data) {
		return new RestResponse(COMMON_SUCCESS_CODE, true, "", "", data);
	}

	public static <T> RestResponse error(int code, String subCode, String message, T data) {
		return new RestResponse(code, false, subCode, message, data);
	}

	public static RestResponse error(String message) {
		return error(COMMON_ERROR_CDOE, message);
	}

	public static RestResponse error(int code, String message) {
		return error(code, "", message, null);
	}

	public static RestResponse error(BindingResult bindingResult) {
		return error(getJsonString(bindingResult));
	}

	public static RestResponse error(ExceptionEnums enums, Object... args) {
		return error(enums.getCode(), "", String.format(enums.getMsg(), args), null);
	}

	public Boolean isOk() {
		return myGet("success", Boolean.class);
	}

	public Object getData() {
		return myGet("data", Object.class);
	}

	public String getMessage() {
		return myGet("message", String.class);
	}

	public RestResponse setAny(String key, Object value) {
		if (key != null && value != null) {
			put(key, value);
		}
		return this;
	}

	private <T> T myGet(String key, Class<T> type) {
		Object obj = this.get(key);
		if (Objects.isNull(obj)) {
			return null;
		}
		return (T) obj;
	}

	/**
	 * 校验错误转字符串
	 * 
	 * @param bindingResult
	 * @return
	 */
	public static String getJsonString(BindingResult bindingResult) {

		if (bindingResult.getAllErrors().size() == 0) {
			return StringUtils.EMPTY;
		}
		List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();
		for (FieldError error : bindingResult.getFieldErrors()) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setField(error.getField());
			errorInfo.setInfo(error.getDefaultMessage());
			errorInfo.setType(error.getCode());
			errorInfoList.add(errorInfo);

		}
		String myInfo = null;
		try {
			myInfo = JacksonUtil.toJson(errorInfoList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myInfo;
	}

	public int getCode() {
		return myGet("code", Integer.class);
	}

	public String getSubCode() {
		return myGet("subCode", String.class);
	}

	public RestResponse setData(T data) {
		this.put("data", data);
		return this;
	}

}
