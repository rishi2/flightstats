package io.polarisdev.wrsflightservice.service;



import javax.annotation.PostConstruct;

import io.polarisdev.wsclientframework.AbstractWebService;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import io.polarisdev.wsclientframework.commons.exception.WServiceException;
import io.polarisdev.wsclientframework.system.annotation.WebServiceComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author
 *
 */
@WebServiceComponent(value = FlightService.NAME, serviceName = FlightService.NAME,
    serviceClient = "flightStatSvcClient", transformer = "flightStatTransformer")
@Slf4j
public class FlightService extends AbstractWebService {

  public static final String NAME = "flightStatService";

  @PostConstruct
  @Override
  public void register() throws Exception {
    this.register(NAME, this);

  }

  /*
   * (non-Javadoc)
   * 
   * @see io.polarisdev.wsclientframework.IService#call(java.lang.Object)
   */
  @Override
  public ISvcContainer call(Object data) throws WServiceException {
    ISvcContainer svcContainer = newSvcContainer();
    try {
      svcContainer.setSvcField(ISvcContainer.INPUT_KEY, data);
      svcContainer.setSvcField(ISvcContainer.CAN_CONTINUE, true);
      return this.callService(svcContainer);
    } catch (final Exception exc) {
      log.error("error in calling framework::{}",exc);
    }
    return this.callService(svcContainer);
  }

}
