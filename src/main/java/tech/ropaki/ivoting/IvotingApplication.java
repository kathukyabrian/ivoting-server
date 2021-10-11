package tech.ropaki.ivoting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.ropaki.ivoting.config.ApplicationProperties;

@EnableConfigurationProperties(ApplicationProperties.class)
@SpringBootApplication
public class IvotingApplication {

	public static void main(String[] args) {
		SpringApplication.run(IvotingApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfig(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("*").allowedMethods("*");
			}
		};

	}
}
