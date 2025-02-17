package example.spring.security.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import example.spring.security.jwt.TokenProvider;
import example.spring.util.handler.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static example.spring.util.handler.ResponseHandler.buildResponse;

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
                        new UsernamePasswordAuthenticationToken(
                                decodedJWT.getSubject(),
                                null,
                                tokenProvider.getAuthoritiesFromToken(decodedJWT));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                filterChain.doFilter(request, response);
            } catch (SignatureVerificationException exception) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                buildResponse(response, new ErrorResponse("TOKEN_DECLARATION_IS_WRONG", HttpServletResponse.SC_FORBIDDEN));
            } catch (TokenExpiredException exception) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                buildResponse(response, new ErrorResponse("TOKEN_IS_EXPIRED", HttpServletResponse.SC_FORBIDDEN));
            } catch (Exception exception) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                buildResponse(response, new ErrorResponse("YOU_ARE_NOT_AUTHENTICATED", HttpServletResponse.SC_UNAUTHORIZED));
            }
        }
    }
}
