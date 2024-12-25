package com.MustCloud.Cloud.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    // Bu anahtarı güvenli bir şekilde saklıyoruz
    private static final String SECRET_KEY = "(F7HGS4V79*Ne7sm"; 
    
    // String'i Key'e dönüştür
    static Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); 
        
        public static String generateToken(Integer userId) {
            return Jwts.builder()
                    .setSubject(String.valueOf(userId))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 saat geçerli
                    .signWith(key)
                .compact();
    }

    public static Integer extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }
}