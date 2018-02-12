package io.polarisdev.wrsflightservice.service.impl;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.polarisdev.wrsflightservice.alert.entity.FlightAlertRule;
import io.polarisdev.wrsflightservice.alert.repository.FlightAlertRepository;
import io.polarisdev.wrsflightservice.domain.flight.alert.FlightAlertsResponse;
import io.polarisdev.wrsflightservice.request.data.RequestData;
import io.polarisdev.wrsflightservice.service.FlightAlertInfoService;
import io.polarisdev.wrsflightservice.service.FlightAlertService;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import io.polarisdev.wsclientframework.commons.exception.WServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * Flight alert Service which is responsible for Flight Alert API call .
 */
@Service("flightAlertsServiceImpl")
@Slf4j
public class FlightAlertServiceImpl implements FlightAlertService {

  private FlightAlertInfoService flightAlertsInfoService;

  private FlightAlertRepository flightAlertRepository;



  /**
   * invoke constructor
   * 
   * @param flightAlertsInfoService
   */
  public FlightAlertServiceImpl(FlightAlertInfoService flightAlertsInfoService,
      FlightAlertRepository flightAlertRepository) {
    this.flightAlertsInfoService = flightAlertsInfoService;
    this.flightAlertRepository = flightAlertRepository;
  }

  /**
   * method used to transform response and return back to caller.
   * 
   * @param flightAlertsResponseContainer
   * @return FlightAlertsResponse
   */
  private String transformResponse(ISvcContainer flightAlertsResponseContainer) {
    ResponseEntity<FlightAlertsResponse> response =
        (ResponseEntity<FlightAlertsResponse>) flightAlertsResponseContainer
            .getSvcField(ISvcContainer.OUTPUT_KEY);
    if (null != response && null != response.getBody()) {
      String partyId = flightAlertsResponseContainer.getSvcField(ISvcContainer.PARTY_KEY).toString();
      String ruleId = response.getBody().getRule().getId();
      return createFlightAlertRule(partyId, ruleId);
    }
    return ISvcContainer.ERROR;
  }

  /**
   * creating mapping between userId and ruleId
   * 
   * @param partyId
   * @param ruleId
   */
  private String createFlightAlertRule(String partyId, String ruleId) {
    FlightAlertRule flightAlertRule = new FlightAlertRule();
    flightAlertRule.setUserId(partyId);
    flightAlertRule.setRuleId(ruleId);
    try {
      flightAlertRule = flightAlertRepository.save(flightAlertRule);
      return flightAlertRule.getRuleId();
    } catch (final Exception e) {
      log.error("error in creating mapping between userId, ruleId {} {}",partyId,ruleId);
    }
    return ISvcContainer.ERROR;
  }



  @Override
  public String createFlightRuleByDeparture(RequestData requestData) {
    try {
      return transformResponse(flightAlertsInfoService.call(requestData));
    } catch (WServiceException e) {
      log.error("error in createFlightRuleByDeparture:{}", e.getMessage());
    }
    return ISvcContainer.ERROR;
  }

}
