package com.sf.gis.cds.common.util;

import com.sf.gis.cds.common.constant.Common;
import com.sf.gis.cds.common.model.TokenCheckResult;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

public class TokenManageUtil {
    private static final Logger logger = LoggerFactory.getLogger(TokenManageUtil.class);

    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(Common.JWT_ENCRYPT);
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
            logger.error("JWT字符串{}已过期，异常为{}",tokenStr,e.getMessage());
            checkResult.setErrorCode(Common.JWT_ERR_CODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            logger.error("JWT字符串{}签名编码错误，异常为{}",tokenStr,e.getMessage());
            checkResult.setErrorCode(Common.JWT_ERR_CODE_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            logger.error("JWT字符串{}验证失败，异常为{}",tokenStr,e.getMessage());
            checkResult.setErrorCode(Common.JWT_ERR_CODE_FAIL);
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
