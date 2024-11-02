package com.kunuz.util;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.kunuz.dto.JwtDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;


public class JwtUtil {
    private static final int ACCESS_TOKEN = 86400000; // 1-day
    private static final String secretKey = "jwtproject@cCeE$SH1kDAtPr0JectDAtbacksDwposRestaurant";
    private static final int REFRESH_TOKEN_EXPIRATION = 604800000;

    public static String encode(String username, String role) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);
        extraClaims.put("username", username);

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    public static JwtDto decode(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = (String) claims.get("username");
        String role = (String) claims.get("role");
        return new JwtDto(username, role);
    }

    private static Key getSignInKey() {
        String str = Base64.getEncoder().encodeToString(secretKey.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(str);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
