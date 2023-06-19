package JWTAPI.Security;

import java.io.Serializable;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;



public class TokenUtils implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(key).compact();
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private final static String ACCES_TOKE_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";
    private final static long ACCES_TOKE_VALIDATE_SECONDS = 2_592_000L;

    public static String CreateToken(String nombre, String email){
        long expirationTime = ACCES_TOKE_VALIDATE_SECONDS *1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("nombre",nombre);

        return Jwts.builder()
            .setSubject(email)
            .setExpiration(expirationDate)
            .addClaims(extra)
            .signWith(Keys.hmacShaKeyFor(ACCES_TOKE_SECRET.getBytes()))
            .compact();
    }

    public static UsernamePasswordAuthenticationToken GetAuthentication(String token)
    {
        try {
            Claims claims = Jwts.parserBuilder()
            .setSigningKey(ACCES_TOKE_SECRET.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody();

            String email=claims.getSubject();
            return new UsernamePasswordAuthenticationToken(email,null,Collections.emptyList());

        } catch (JwtException e) {
            return null;
        }
    }

    //https://www.youtube.com/watch?v=_p-Odh3MZJc&t=960s

}