package com.sf.gis.cds.common.model;

import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TokenCheckResult {

    private boolean success;

    private Claims claims;

    private String errorCode;

}
