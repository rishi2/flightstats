package io.polarisdev.wsclientframework.commons.client.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.polarisdev.wsclientframework.IService;
import io.polarisdev.wsclientframework.commons.client.IServiceManager;
import io.polarisdev.wsclientframework.system.annotation.WebServiceComponent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("serviceManager")
public class ServiceManager implements IServiceManager {

  private Map<String, IService> services = new HashMap<>();


  @Override
  public void registerService(String key, IService service) throws Exception {
    scanWebserviceAnnotation(key, service);
  }

  private void scanWebserviceAnnotation(String key, IService service) throws Exception {
    WebServiceComponent webService = service.getClass().getAnnotation(WebServiceComponent.class);
    if (webService == null) {
      throw new Exception(key + " service is not implementing  WebService annotation ");
    }
    String serviceClient = webService.serviceClient();
    String serviceName = webService.serviceName();
    String transformer = webService.transformer();
    log.warn("serviceClient...." + serviceClient);
    log.warn("serviceName...." + serviceName);
    log.warn("transformer...." + transformer);
    if (serviceClient == null)
      throw new Exception(key + " service provide @serviceClient ");
    if (transformer == null)
      throw new Exception(key + " service provide @transformer ");

    this.services.put(key, service);
  }

  @Override
  public IService get(String key) {
    IService service = this.services.get(key);
    if (service != null) {
      return service;
    } else {
      throw new RuntimeException("Service does not exist for " + key);
    }
  }
}
