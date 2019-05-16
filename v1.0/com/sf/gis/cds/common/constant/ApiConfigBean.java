package com.sf.gis.cds.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@PropertySource("classpath:api.properties")
public class ApiConfigBean {

	@Value("${api.service.pool:http://localhost:8080/zsypool}")
	private String poolServiceUrl;
}
