package io.ennov.simple_ticket_management_system.config;

import java.util.Map;
import java.util.function.Function;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.spec.SecretKeySpec;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.ennov.simple_ticket_management_system.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
@Component
public class JwtUtils {

    private String secretKey = Constants.SECRET_KEY;
	private String expirationTime = Constants.EXPIRATION_TIME;
    
    public String generateToken(String username) {
            Map<String, Object> claims = new HashMap<>();
            return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
            return Jwts.builder()
                            .setClaims(claims)
                            .setSubject(username)
                            .setIssuedAt(new Date(System.currentTimeMillis()))
                            .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expirationTime)))
                            .signWith(getSignKey(),  SignatureAlgorithm.HS256)
                            .compact();

    }

    private Key getSignKey() {
            byte[] keyBytes = secretKey.getBytes();
            return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
            String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
            return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
    }

    @SuppressWarnings("deprecation")
    private Claims extractAllClaims(String token) {
            return Jwts.parser()
                            .setSigningKey(getSignKey())
                            .parseClaimsJws(token)
                            .getBody();
    }

    private boolean isTokenExpired(String token) {
            return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
            return extractClaim(token, Claims::getExpiration);
    }
}
