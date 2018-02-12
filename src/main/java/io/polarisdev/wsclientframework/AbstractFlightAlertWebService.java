package io.polarisdev.wsclientframework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.polarisdev.wsclientframework.commons.client.IServiceManager;
import io.polarisdev.wsclientframework.commons.client.impl.ServiceWrapper;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import io.polarisdev.wsclientframework.commons.container.impl.SvcContainer;
import io.polarisdev.wsclientframework.commons.exception.WServiceException;
import io.polarisdev.wsclientframework.system.annotation.WebServiceComponent;


public abstract class AbstractFlightAlertWebService implements IService {

  @Value("${flight.alerts.basic.url}")
  private String basicFlightAlertsUrl;

  @Value("${flight.alerts.url}")
  private String flightAlertsUrl;


  @Value("${flight.status.app.id}")
  private String flightAppId;

  @Value("${flight.status.app.key}")
  private String flightAppKey;

  @Autowired
  private IServiceManager serviceManager;

  @Autowired
  private ServiceWrapper serviceWrapper;


  public void register(String key, IService service) throws Exception {
    serviceManager.registerService(key, service);
  }

  protected ISvcContainer callService(ISvcContainer serviceContainer) throws WServiceException {
    serviceContainer.setSvcField(ISvcContainer.SERVICE_NAME,
        this.getClass().getAnnotation(WebServiceComponent.class).serviceName());
    return serviceWrapper.process(serviceContainer);
  }

  protected ISvcContainer newSvcContainer() {
    ISvcContainer serviceData = new SvcContainer();
    serviceData.setSvcField(ISvcContainer.SERVICE_URL,
        basicFlightAlertsUrl.concat(flightAlertsUrl));
    serviceData.setSvcField(ISvcContainer.APP_ID, flightAppId);
    serviceData.setSvcField(ISvcContainer.APP_KEY, flightAppKey);
    return serviceData;
  }
}
