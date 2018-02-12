package io.polarisdev.wrsflightservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(
    scanBasePackages = "io.polarisdev.wsclientframework, io.polarisdev.wrsflightservice")
public class FlightStatusInfoApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlightStatusInfoApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public HttpHeaders httpHeaders() {
    return new HttpHeaders();
  }

}
