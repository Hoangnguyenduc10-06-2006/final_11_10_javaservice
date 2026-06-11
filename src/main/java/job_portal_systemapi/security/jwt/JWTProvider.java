package job_portal_systemapi.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.security.SignatureException;
import job_portal_systemapi.exception.JWTValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JWTProvider {
    @Value("${jwt-secret}")
    private String jwtSecret;
    @Value("${jwt-expire}")
    private Long jwtExpired;


    public String generateToken(String username) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + jwtExpired);

            return Jwts.builder()
                    .subject(username)
                    .issuedAt(now)
                    .expiration(expiryDate)
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e) {
            throw new JWTValidate("lỗi khi tạo jwt");

        }
    }

    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (UnsupportedJwtException e) {
            log.info("Hệ thống không hỗ trợ JWT");
            throw new JWTValidate("Hệ thống không hỗ trợ JWT");

        } catch (MalformedJwtException e) {
            log.info("Chuỗi JWT không đúng");
            throw new JWTValidate("Chuỗi JWT không đúng");

        } catch (ExpiredJwtException e) {
            log.info("Chuỗi JWT hết hạn");
            throw new JWTValidate("Chuỗi JWT hết hạn");

        } catch (SignatureException e) {
            log.info("Sai chữ ký JWT");
            throw new JWTValidate("Sai chữ ký JWT");

        } catch (IllegalArgumentException e) {
            log.info("Chuỗi JWT rỗng");
            throw new JWTValidate("Chuỗi JWT rỗng");

        } catch (JwtException e) {
            log.info("JWT không hợp lệ");
            throw new JWTValidate("JWT không hợp lệ");
        }
    }

    public String getUsernameFromToken(String token) {
        try{
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }
}
