package net.nvsoftware.iProductService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class IProductServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(IProductServiceApplication.class, args);
	}

}
