package io.polarisdev.wrsflightservice.connection;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import io.polarisdev.wrsflightservice.constant.FlightServiceConstant;

@Slf4j
public class FlightStatsConnection {


  /**
   * private constructor
   */
  private FlightStatsConnection() {

  }

  /**
   * getMessageConverters.
   * 
   * @return List<HttpMessageConverter<?>>
   */
  public static List<HttpMessageConverter<?>> getMessageConverters() {
    List<HttpMessageConverter<?>> converters = new ArrayList<>();
    converters.add(new MappingJackson2HttpMessageConverter());
    return converters;
  }


  /**
   * create HttpEntity Object.
   * 
   * @return
   */
  public static HttpEntity<String> getHttpEntity(HttpHeaders headers) {
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(headers);
  }

  /**
   * create RestTemplate object.
   * 
   * @param restTemplate
   * @return
   */
  public static RestTemplate getRestTemplate(RestTemplate restTemplate) {
    restTemplate.setMessageConverters(getMessageConverters());
    return restTemplate;
  }

  /**
   * 
   * @param param
   * @return
   */
  public static String prepareParam(String param) {
    return param.concat(FlightServiceConstant.BACKSLASH);
  }
}
