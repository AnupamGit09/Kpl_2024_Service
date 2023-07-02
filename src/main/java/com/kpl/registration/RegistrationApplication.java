package com.kpl.registration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kpl.registration.config.UTCtoISTConverter;
import com.kpl.registration.logging.LoggingInterceptor;

@SpringBootApplication
public class RegistrationApplication extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/**");

	}

	  @Bean
	    public ModelMapper modelMapper() {
	        ModelMapper modelMapper = new ModelMapper();

	        // Register the UTCtoISTConverter with ModelMapper
	        modelMapper.addConverter(new UTCtoISTConverter());

	        return modelMapper;
	    }
	

	@Autowired
	private LoggingInterceptor loggingInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingInterceptor);
	}

}
