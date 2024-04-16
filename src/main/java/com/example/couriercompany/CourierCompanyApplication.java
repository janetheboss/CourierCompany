package com.example.couriercompany;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Yanchos deliveries bratan",
        description = "${tosho.cukata}",
        version = "v1.0"
       ))
public class CourierCompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourierCompanyApplication.class, args);
    }

}
