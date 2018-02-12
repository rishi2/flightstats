package io.polarisdev.wrsflightservice.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import io.polarisdev.wrsflightservice.connection.FlightStatsConnection;
import io.polarisdev.wrsflightservice.domain.flight.alert.FlightAlertsResponse;
import io.polarisdev.wrsflightservice.domain.flightstatus.FlightStatusResponse;
import io.polarisdev.wsclientframework.commons.client.IServiceClient;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import io.polarisdev.wsclientframework.commons.container.impl.RequestContainer;
import io.polarisdev.wsclientframework.commons.container.impl.ResponseContainer;
import io.polarisdev.wsclientframework.commons.exception.WSExceptionConstants;
import io.polarisdev.wsclientframework.commons.exception.WServiceException;
import io.polarisdev.wsclientframework.system.annotation.ApplicationComponent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationComponent("flightStatSvcClient")
public class FlightStatSvcClient implements IServiceClient {

  private RestTemplate restTemplate;

  private HttpHeaders headers;

  public FlightStatSvcClient(RestTemplate restTemplate, HttpHeaders headers) {
    this.restTemplate = restTemplate;
    this.headers = headers;
  }

  /**
   * Responsible for actual REST call
   * 
   * @param RequestContainer
   * @param ResponseContainer
   * @return ResponseContainer
   */
  @Override
  public ResponseContainer process(RequestContainer requestContainer,
      ResponseContainer responseContainer) throws WServiceException {

    try {
      long processingTime = System.currentTimeMillis();
      this.callRestfulService(requestContainer, responseContainer);
      log.debug("Response received in {} ms." + (System.currentTimeMillis() - processingTime));
    } catch (final Exception ex) {
      log.error("Error in FlightStatSvcClient {}", ex);
      throw new WServiceException(WSExceptionConstants.GENERAL_ERROR,
          WSExceptionConstants.GENERAL_ERROR_MSG, ex, requestContainer);
    }
    return responseContainer;
  }

  /**
   * Responsible to make actual API call based on REST URL and binds actual output into OUTPUT_KEY
   * and make sure whether response is appropriate or not. CAN_CONTINUE equals false in case
   * response be null.
   * 
   * @param requestContainer
   * @param responseContainer
   */
  private void callRestfulService(RequestContainer requestContainer,
      ResponseContainer responseContainer) {
    ISvcContainer svcContainer = (ISvcContainer) requestContainer.getRequest();
    if (canContinue(requestContainer)) {
      ResponseEntity<FlightStatusResponse> responseFlightStat = null;
      ResponseEntity<FlightAlertsResponse> responseAlert = null;
      String flightStatusEndPoints = null;
      // create the complete endpoint
      flightStatusEndPoints = createEndPoint(svcContainer);
      if ((svcContainer.getSvcField(ISvcContainer.INPUT_TYPE).toString()
          .equalsIgnoreCase(ISvcContainer.FLIGHT_STATUS))) {
        responseFlightStat = FlightStatsConnection.getRestTemplate(restTemplate).exchange(
            flightStatusEndPoints, HttpMethod.GET, FlightStatsConnection.getHttpEntity(headers),
            FlightStatusResponse.class);
        if (null != responseFlightStat && responseFlightStat.getStatusCodeValue() >= 300) {
          svcContainer.setSvcField(ISvcContainer.ERROR_KEY,
              "http status=" + responseFlightStat.getStatusCode());
          svcContainer.setSvcField(ISvcContainer.CAN_CONTINUE, false);
          svcContainer.setSvcField(ISvcContainer.CRITICAL_KEY, true);
        }
        svcContainer.setSvcField(ISvcContainer.CAN_CONTINUE, true);
        if (null != responseFlightStat && null != responseFlightStat.getBody()) {
          svcContainer.setSvcField(ISvcContainer.OUTPUT_KEY, responseFlightStat);
        } else {
          svcContainer.setSvcField(ISvcContainer.CAN_CONTINUE, false);
          svcContainer.setSvcField(ISvcContainer.OUTPUT_KEY, responseFlightStat);
          log.error("Error in FlightStatusResponse {}", responseFlightStat);
        }
        // else requestType will be createAlertRule
      } else {
        responseAlert = FlightStatsConnection.getRestTemplate(restTemplate).exchange(
            flightStatusEndPoints, HttpMethod.GET, FlightStatsConnection.getHttpEntity(headers),
            FlightAlertsResponse.class);

        if (null != responseAlert && responseAlert.getStatusCodeValue() >= 300) {
          svcContainer.setSvcField(ISvcContainer.ERROR_KEY,
              "http status=".concat(responseAlert.getStatusCode().toString()));
          svcContainer.setSvcField(ISvcContainer.CAN_CONTINUE, false);
          svcContainer.setSvcField(ISvcContainer.CRITICAL_KEY, true);
        }
        svcContainer.setSvcField(ISvcContainer.CAN_CONTINUE, true);

        if (null != responseAlert && null != responseAlert.getBody()) {
          svcContainer.setSvcField(ISvcContainer.OUTPUT_KEY, responseAlert);
          svcContainer.setSvcField(ISvcContainer.PARTY_KEY,
              svcContainer.getSvcField(ISvcContainer.PARTY_KEY).toString());
        } else {
          svcContainer.setSvcField(ISvcContainer.CAN_CONTINUE, false);
          svcContainer.setSvcField(ISvcContainer.OUTPUT_KEY, responseAlert);
          log.error("error in callRestfulService:{}");
        }
      }

      responseContainer.setResponse(svcContainer);
    }

  }

  /**
   * prepare REST endpoint URI.
   * 
   * @param svcContainer
   * @return
   */
  private String createEndPoint(ISvcContainer svcContainer) {
    return svcContainer.getSvcField(ISvcContainer.SERVICE_URL).toString()
        + svcContainer.getSvcField(ISvcContainer.INPUT_KEY) + ISvcContainer.APP_ID
        + svcContainer.getSvcField(ISvcContainer.APP_ID) + ISvcContainer.APP_KEY
        + svcContainer.getSvcField(ISvcContainer.APP_KEY);
  }

  /**
   * set boolean values as a success and failure.
   * 
   * @param requestContainer
   * @return
   */
  protected boolean canContinue(RequestContainer requestContainer) {
    return getRequest(requestContainer).getSvcField(ISvcContainer.CAN_CONTINUE) != null
        && (Boolean) getRequest(requestContainer).getSvcField(ISvcContainer.CAN_CONTINUE);

  }

  protected ISvcContainer getRequest(RequestContainer requestContainer) {
    return (ISvcContainer) requestContainer.getRequest();
  }
}
