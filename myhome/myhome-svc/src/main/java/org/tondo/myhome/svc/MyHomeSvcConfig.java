package org.tondo.myhome.svc;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class MyHomeSvcConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
