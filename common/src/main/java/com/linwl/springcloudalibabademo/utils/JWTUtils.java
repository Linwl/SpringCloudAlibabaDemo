package com.linwl.springcloudalibabademo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:37
 * @description ：
 * @modified By：
 */
public class JWTUtils {

    private JWTUtils(){}

    /**
     * 创建Jwt
     * @param id jwt的ID
     * @param subject 主体
     * @param exMillis 过期时间
     * @param secretKey jwt私钥
     * @param claims 公共声明
     * @return
     * @throws Exception
     */
    public static String createJWT(String id, String subject, long exMillis, String secretKey, Map<String,Object> claims) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        SecretKey key = JWTUtils.generalKey(secretKey);
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (exMillis >= 0) {
            long expMillis = nowMillis + exMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);     //设置过期时间
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     * @param jwt
     * @param secretKey JWT私钥
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt, String secretKey) throws Exception{
        SecretKey key = generalKey(secretKey);  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)         //设置签名的秘钥
                .parseClaimsJws(jwt).getBody();//设置需要解析的jwt
        return claims;
    }

    /**
     * 由字符串生成加密key
     * @return
     */
    private static SecretKey generalKey(String secretKey) throws IOException {
        byte[] encodedKey = Base64.getDecoder().decode(secretKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}