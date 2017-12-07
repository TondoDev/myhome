package org.tondo.myhome.data;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EntityScan(basePackages = "org.tondo.myhome.data.domain")
@EnableJpaRepositories(basePackages ="org.tondo.myhome.data.repo")
public class MyhomeDataConfig {

}
