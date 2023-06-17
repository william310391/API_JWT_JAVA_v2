package JWTAPI.Security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String bearertoken = request.getHeader("Authorization");
        if (bearertoken!= null && bearertoken.startsWith("Bearer ")){
            String token = bearertoken.replace("Bearer ","");
            UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.GetAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(usernamePAT);                 
        } 
        filterChain.doFilter(request, response);           
    }
    
}
