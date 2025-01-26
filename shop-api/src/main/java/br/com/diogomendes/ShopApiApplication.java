package br.com.diogomendes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApiApplication.class, args);
	}

}
