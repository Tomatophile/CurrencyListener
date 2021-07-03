package ru.neoflex.tomatophile.currencylistener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.neoflex.tomatophile.currencylistener.pojo.Subscribe;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class SubscribeConfig {
    @Bean
    public List<Subscribe> subscribesOnUpdate() {
        return new ArrayList<>();
    }

    @Bean
    public List<Subscribe> subscribesOnFall() {
        return new ArrayList<>();
    }

    @Bean
    List<String> errors() {
        return new ArrayList<>();
    }
}
