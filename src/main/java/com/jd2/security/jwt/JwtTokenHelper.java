package com.jd2.security.jwt;

import com.jd2.configuration.JwtSecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Claims.ISSUED_AT;
import static io.jsonwebtoken.Claims.SUBJECT;
import static java.util.Calendar.MILLISECOND;

@Component
@RequiredArgsConstructor
public class JwtTokenHelper {

    /*Generate JWT Token and fields in token. Also add signature into 3-d part of token*/

    public static final String ROLES = "roles";

    public static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;

    public static final String JWT = "JWT";

    private final JwtSecurityConfig jwtSecurityConfig;

    //-------------------------------------------------------1 generateToken
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUBJECT, userDetails.getUsername());
        claims.put(ISSUED_AT, generateCurrentDate());
        claims.put(ROLES, getEncryptedRoles(userDetails));
        return generateToken(claims);
    }

    //1.1 generateToken + 2.2 refreshToken + 3.3.2 canRefreshToken
    private Date generateCurrentDate() {
        return new Date();
    }

    //1.2 generateToken
    private List<String> getEncryptedRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().
                stream()
                .map(GrantedAuthority::getAuthority)
                .map(s -> s.replace("ROLE_", ""))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    //1.3 generateToken + 2.3 generateToken
    private String generateToken(Map<String, Object> claims) {

        final Date date = generateCurrentDate();

        return Jwts
                .builder()
                //Set headers with algo and token type info
                .setHeader(generateJWTHeaders())
                //We create payload with user info, roles, expiration date of token
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(generateExpirationDate(date))
                //Signature
                .signWith(ALGORITHM, jwtSecurityConfig.getSecret())
                .compact();
    }

    //1.3.1 generateToken
    private Map<String, Object> generateJWTHeaders() {
        Map<String, Object> jwtHeaders = new LinkedHashMap<>();
        jwtHeaders.put("typ", JWT);
        jwtHeaders.put("alg", ALGORITHM.getValue());

        return jwtHeaders;
    }

    //1.3.2 generateToken
    private Date generateExpirationDate(Date date) {
        Instant instant = date.toInstant();
        return Date.from(instant.plusSeconds(jwtSecurityConfig.getExpiration()));
    }

    //-------------------------------------------------------2 refreshToken
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            claims.setIssuedAt(generateCurrentDate());
            refreshedToken = this.generateToken(claims);
        } catch (Exception e) {
            //log???
            refreshedToken = null;
        }
        return refreshedToken;
    }

    //2.1 refreshedToken + 3.1.1 canRefreshToken + 3.3.1.1 canRefreshToken + 4.1.1
    private Claims getClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtSecurityConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    //-------------------------------------------------------3 canRefreshToken
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        if (lastPasswordReset == null) {
            //log???
            return false;
        }
        final Claims claims = this.getClaimsFromToken(token);
        return !(claims.getIssuedAt().before(lastPasswordReset))
                && !(claims.getExpiration().before(lastPasswordReset));
    }

    //-------------------------------------------------------4 isValidToken
    public Boolean isValidateToken(String token, UserDetails userDetails) {
        final String username = getClaimsFromToken(token).getSubject();
        return username.equals(userDetails.getUsername());
    }
}
