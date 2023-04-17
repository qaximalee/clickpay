package com.clickpay;
import com.clickpay.utils.CryptoUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.File;

@SpringBootApplication
@EnableJpaAuditing
public class ClickPayApplication {

	public static void main(String[] args) {
		// Decrypt the app file and convert into plain text file with extension of .properties
		//CryptoUtil.start(args);
		SpringApplication springApp = new SpringApplication(ClickPayApplication.class);
		/*springApp.addListeners((ApplicationListener<ContextClosedEvent>) event -> {
			System.out.println("Shutdown process initiated...");
			File file = new File(CryptoUtil.CLASSPATH+ "application-qasim.properties");
			file.delete();
			System.out.println("Graceful Shutdown is processed successfully");
		});*/
		springApp.run(args);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public FilterRegistrationBean corsFilterBean() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
