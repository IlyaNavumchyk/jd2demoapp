package com.jd2.security.jwt;

import com.jd2.configuration.JwtSecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Claims.SUBJECT;
import static java.util.Calendar.MILLISECOND;

@Component
@RequiredArgsConstructor
public class JwtTokenHelper {

    /*Generate JWT Token and fields in token. Also add signature into 3-d part of token*/
    public static final String CREATE_VALUE = "created";

    public static final String ROLES = "roles";

    public static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;

    public static final String JWT = "JWT";

    private final JwtSecurityConfig jwtSecurityConfig;

    //-------------------------------------------------------1 generateToken
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUBJECT, userDetails.getUsername());
        claims.put(CREATE_VALUE, generateCurrentDate());
        claims.put(ROLES, getEncryptedRoles(userDetails));
        return generateToken(claims);
    }

    //1.1 generateToken + 2.2 refreshToken + 3.3.2 canRefreshToken
    private Date generateCurrentDate() {
        return new Date();
    }

    //1.2 refreshToken
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

        return Jwts
                .builder()
                /*Set headers with algo and token type info*/
                .setHeader(generateJWTHeaders())
                /*We create payload with user info, roles, expiration date of token*/
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                /*Signature*/
                .signWith(ALGORITHM, jwtSecurityConfig.getSecret())
                //.signWith(getSecretKey())
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
    private Date generateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(MILLISECOND, jwtSecurityConfig.getExpiration());
        return calendar.getTime();
    }

    //1.3.3 generateToken + 2.1.1 refreshToken
    /*private Key getSecretKey() {
        return new SecretKeySpec(jwtSecurityConfig.getSecret().getBytes(), ALGORITHM.getValue());
    }*/

    //-------------------------------------------------------2 refreshToken
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            claims.put(CREATE_VALUE, this.generateCurrentDate());
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
                .parserBuilder()
                //.setSigningKey(getSecretKey())
                .setSigningKey(jwtSecurityConfig.getSecret())
                .build()
                .parseClaimsJwt(token)
                .getBody();

        /*.parser()
        .setSigningKey(jwtSecurityConfig.getSecret())
        .parseClaimsJws(token)
        .getBody();*/
    }

    //-------------------------------------------------------3 canRefreshToken
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = this.getCreatedDateFromToken(token);
        return !(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
                && !(this.isTokenExpired(token));
    }

    //3.1 canRefreshToken
    public Date getCreatedDateFromToken(String token) {
        return (Date) getClaimsFromToken(token).get(CREATE_VALUE);
    }

    //3.2 canRefreshToken
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    //3.3 canRefreshToken
    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(this.generateCurrentDate());
    }

    //3.3.1 canRefreshToken
    private Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    //-------------------------------------------------------4 isValidToken
    public Boolean isValidateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername());
    }

    //4.1 isValidToken
    private String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }
}
