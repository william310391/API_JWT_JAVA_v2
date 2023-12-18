package JWTAPI.Security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import JWTAPI.Configuration.WebSecurityConfig;


@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter{

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        boolean isInvalidToken = false;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            isInvalidToken = true;
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        String username = TokenUtils.getUsernameFromToken(jwt);
        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (TokenUtils.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken);
            }
        } else {
            isInvalidToken = true;
        }
        if (isInvalidToken) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
        }

        filterChain.doFilter(request, response);      
    }
    
    @Override
	protected boolean shouldNotFilter(HttpServletRequest request)
			throws ServletException {
		//String path = request.getRequestURI();
		//return "/health".equals(path);

		String path = ((HttpServletRequest) request).getRequestURI();
		String[] allowedPaths = WebSecurityConfig.PUBLIC_REQUEST_MATCHERS;
		for (var allowedPath : allowedPaths) {
			allowedPath = allowedPath.replace("/*", "");
			allowedPath = allowedPath.replace("/**", "");
			if (path.contains(allowedPath)) {
				return true;
			}
		}

		return false;
	}
}
