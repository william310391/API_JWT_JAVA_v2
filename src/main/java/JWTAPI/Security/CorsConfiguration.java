package JWTAPI.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new  WebMvcConfigurer() {
            
            @Override
            public void addCorsMappings(CorsRegistry registry){

                registry.addMapping("/**").allowedOrigins("http://localhost:5173","http://192.168.18.7:5173","http://192.168.18.7","http://192.168.18.7:8080");
                // registry.addMapping("/cors")
                // .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
                //         "Access-Control-Request-Headers")
                // .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                // .allowedOrigins("http://localhost:5173","http://localhost:9000")
                // .allowedMethods("POST","GET","DELETE");
            }
        };
    }
}
