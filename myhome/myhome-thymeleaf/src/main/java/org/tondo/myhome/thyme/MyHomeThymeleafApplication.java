package org.tondo.myhome.thyme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tondo.myhome.thyme.converter.LocalDateConverter;

@SpringBootApplication(scanBasePackages="org.tondo.myhome")
public class MyHomeThymeleafApplication extends WebMvcConfigurerAdapter {
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		super.addFormatters(registry);
		
		registry.addConverter(new LocalDateConverter("dd.MM.yyyy"));
	}

	public static void main(String[] args) {
		SpringApplication.run(MyHomeThymeleafApplication.class, args);
	}
}
