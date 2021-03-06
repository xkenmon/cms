package com.xkenmon.cms.admin.auth;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author bigmeng
 * @date 2018/8/9
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${auth.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    Integer getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Integer.parseInt(claims.getSubject());
    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.info("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.info("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.info("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.info("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.info("JWT claims string is empty.");
        }
        return false;
    }
}
