package JWTAPI.Security;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import JWTAPI.DTO.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;
	public final static String[] PUBLIC_REQUEST_MATCHERS = { "/api/v1/auth/**", "/api-docs/**", "/swagger-ui/**","/api/seguridad/*" };


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        http
        .cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_REQUEST_MATCHERS).permitAll()
                .anyRequest()
                .authenticated())
        .exceptionHandling(x -> x.authenticationEntryPoint((request, response, authException) -> {
            response = ErrorResponse(response);
        }))
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    PasswordEncoder passwordEncoder() throws Exception {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http
                                    .getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userDetailsService)
                                    .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();

    }

    private HttpServletResponse ErrorResponse(HttpServletResponse response) throws IOException  {
        ApiResponse<String> apiResponse = new ApiResponse<String>(null);
        apiResponse.setCodigoHTTP(HttpStatus.FORBIDDEN.value());
        apiResponse.setResultadoDescripcion("Token no valido");
        apiResponse.setResultadoIndicador(false);
        response.setStatus(401);
        response.setHeader("content-type", "application/json");
        ObjectMapper mapper = new ObjectMapper();
        String responseMsg = mapper.writeValueAsString(apiResponse);
        response.getWriter().write(responseMsg);
        return response;
    }

}
