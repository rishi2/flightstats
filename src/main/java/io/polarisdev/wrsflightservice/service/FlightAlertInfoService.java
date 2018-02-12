package io.polarisdev.wrsflightservice.service;



import javax.annotation.PostConstruct;

import io.polarisdev.wsclientframework.AbstractFlightAlertWebService;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import io.polarisdev.wsclientframework.commons.exception.WServiceException;
import io.polarisdev.wsclientframework.system.annotation.WebServiceComponent;
import lombok.extern.slf4j.Slf4j;

@WebServiceComponent(value = FlightAlertInfoService.NAME, serviceName = FlightAlertInfoService.NAME,
    serviceClient = "flightStatSvcClient", transformer = "flightStatTransformer")
@Slf4j
public class FlightAlertInfoService extends AbstractFlightAlertWebService {

  public static final String NAME = "flightAlertService";

  @PostConstruct
  @Override
  public void register() throws Exception {
    this.register(NAME, this);
  }

  @Override
  public ISvcContainer call(Object data) throws WServiceException {
    ISvcContainer svcContainer = newSvcContainer();
    try {
      svcContainer.setSvcField(ISvcContainer.INPUT_KEY, data);
      svcContainer.setSvcField(ISvcContainer.CAN_CONTINUE, true);
      return this.callService(svcContainer);
    } catch (final Exception ex) {
      log.error("error in call:{}", ex.getMessage());
    }
    return this.callService(svcContainer);
  }
}
