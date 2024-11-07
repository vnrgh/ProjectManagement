package example.spring.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import example.spring.security.jwt.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    public JwtAuthorizationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/auth/signin") ||
                request.getRequestURI().equals("/auth/signup") ||
                request.getServletPath().contains("swagger") ||
                request.getServletPath().contains("v3")) {
            filterChain.doFilter(request, response);
        } else {
            try {
                String authHeader = request.getHeader("Authorization");
                DecodedJWT decodedJWT = tokenProvider.resolveToken(authHeader);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, tokenProvider.getAuthoritiesFromToken(decodedJWT));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
