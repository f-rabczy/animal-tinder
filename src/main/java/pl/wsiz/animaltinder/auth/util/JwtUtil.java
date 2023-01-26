package pl.wsiz.animaltinder.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.wsiz.animaltinder.auth.config.JwtProperties;
import pl.wsiz.animaltinder.auth.domain.CustomUserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String ID_CLAIM_MAP_KEY = "id";
    public static final String ROLES_CLAIM_MAP_KEY = "roles";

    private final JwtProperties jwtProperties;

    public String generateToken(CustomUserDetails userDetails) {
        var claims = generateClaims(userDetails);
        return createToken(claims, userDetails.getUsername());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Integer extractUserId(String token) {
        var idClaim = extractClaim(token, claims -> claims.get(ID_CLAIM_MAP_KEY)).toString();
        return Integer.valueOf(idClaim);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        var username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime() * 1000 * 60))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()).compact();
    }

    private Map<String, Object> generateClaims(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        var roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put(ID_CLAIM_MAP_KEY, userDetails.getId());
        claims.put(ROLES_CLAIM_MAP_KEY, roles);
        return claims;
    }

}
