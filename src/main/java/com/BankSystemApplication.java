package com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bank.wsdl.Mt103Request;
import com.bank.wsdl.Mt900Response;
import com.webservice.client.CentralBankClient;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableScheduling
public class BankSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSystemApplication.class, args);
	}
	
	@Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Bank System",
                "Bank System application.",
                "1.0",
                "Terms of service",
                new Contact("Bank System", "https://bank.eu",null),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
    }
    
    @Bean
	public PasswordEncoder getBCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
    
    @Bean
   	CommandLineRunner lookup(CentralBankClient bankClient) {
   		return args -> {
   			String ticker = "MSFT";

   			if (args.length > 0) {
   				ticker = args[0];
   			}
   			Mt900Response response = bankClient.getRtgsResponse(new Mt103Request());
   			System.out.println(response.getAmount());
   		};
   	}
}
