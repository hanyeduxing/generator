package com.sf.gis.cds.common.util;

import com.sf.gis.cds.common.constant.CommonConstanst;
import com.sf.gis.cds.common.model.TokenCheckResult;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

// jwt_token管理
public class TokenMgrUtil {

    private static final int EXPIRES_TIME = 86400 * 1000;

    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(CommonConstanst.JWT_SECERT);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 生成token，该方法只在用户登录成功后调用
     * @param payload Map集合，可以存储用户id，token生成时间，token过期时间等自定义字段
     * @return token
     */
    public static String createJwtToken(Map<String, Object> payload) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        String id = payload.get("openid").toString();
        String subject = payload.get("subject").toString();
        JwtBuilder builder = Jwts.builder()
                .setClaims(payload)
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey);
        if (EXPIRES_TIME >= 0) {
            long expMillis = nowMillis + EXPIRES_TIME;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    /**
     * 验证JWT
     * @param tokenStr
     * @return
     */
    public static TokenCheckResult validateJWT(String tokenStr) {
        TokenCheckResult checkResult = new TokenCheckResult();
        Claims claims = null;
        try {
            claims = parseTokenStr(tokenStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrorCode(CommonConstanst.JWT_ERRCODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrorCode(CommonConstanst.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrorCode(CommonConstanst.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     *
     * 解析JWT字符串
     * @param token
     * @return
     * @throws Exception
     */
    public static Claims parseTokenStr(String token) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
