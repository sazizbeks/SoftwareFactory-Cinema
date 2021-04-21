package kz.edu.astanait.gambit_cinema.config;

import kz.edu.astanait.gambit_cinema.parser.ApiParser;
import kz.edu.astanait.gambit_cinema.parser.JsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public ApiParser apiParser(){
        return new ApiParser();
    }

    @Bean
    public JsonParser parser(){
        return new JsonParser();
    }
}
