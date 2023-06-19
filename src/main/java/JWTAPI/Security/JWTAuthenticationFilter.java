package JWTAPI.Security;


import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;

import JWTAPI.DTO.ApiResponse;
import JWTAPI.DTO.SeguridadDTO;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request
                                               ,HttpServletResponse response) throws AuthenticationException
    {    
        AuthCredentials authCredentials = new AuthCredentials();
 
        try {
             authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (Exception e) {
        }    

        UsernamePasswordAuthenticationToken usernamePAT= new UsernamePasswordAuthenticationToken(
            authCredentials.getEmail(),
            authCredentials.getPassword(),
            Collections.emptyList()
        );
        return getAuthenticationManager().authenticate(usernamePAT);
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException,ServletException, java.io.IOException{

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token =TokenUtils.CreateToken(userDetails.getNombre(), userDetails.getUsername());

        //response.addHeader("Authorization", "Bearer " + token);
        
        ApiResponse<SeguridadDTO> apiResponse = new ApiResponse<SeguridadDTO>( new SeguridadDTO());

        SeguridadDTO dto = new SeguridadDTO();
        dto.setToken(token);
        dto.setNombre(userDetails.getNombre());
        dto.setUsuario(userDetails.getUsername());
        dto.setId(userDetails.getId());

        apiResponse.setData(dto);
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);

    }
    
}

///https://stackoverflow.com/questions/44697883/can-you-completely-disable-cors-support-in-spring
