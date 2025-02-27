package example.spring.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import example.spring.exception.JwtTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TokenProvider {
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expireTime}")
    private long expireTime;

    public String createToken(Authentication authenticate) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(authenticate.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .withClaim("roles", getRoleNamesFromAuthorities(authenticate))
                .sign(algorithm);
    }

    public DecodedJWT resolveToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public List<String> getRoleNamesFromAuthorities(Authentication authenticate) {
        return authenticate.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(DecodedJWT decodedJWT) {
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        return Arrays.stream(roles)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
