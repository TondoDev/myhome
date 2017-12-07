package org.tondo.myhome.thyme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="org.tondo.myhome")
public class MyHomeThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyHomeThymeleafApplication.class, args);
	}
}
