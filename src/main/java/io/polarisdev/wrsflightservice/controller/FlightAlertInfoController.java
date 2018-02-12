package io.polarisdev.wrsflightservice.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.polarisdev.wrsflightservice.request.data.RequestData;
import io.polarisdev.wrsflightservice.service.FlightAlertService;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;


@CrossOrigin
@RestController
@RequestMapping("/flight/alerts")
public class FlightAlertInfoController {

  private FlightAlertService flightAlertsService;

  public FlightAlertInfoController(FlightAlertService flightAlertsService) {
    this.flightAlertsService = flightAlertsService;
  }


  /**
   * Create a flight rule to be monitored for a specific flight departing from an airport on the
   * given day. Returns the fully constructed flight rule that was created.
   * 
   * @param carrier
   * @param flightNo
   * @param depatureDate
   * @param utc
   * @param airport
   * @param codeType
   * @return
   */
  @GetMapping("/create/{carrier}/{flightNumber}/from/{departureAirport}/departing/{year}/{month}/{day}")
  public String createFlightRuleByDeparture(@PathVariable String carrier,
      @PathVariable String flightNumber, @PathVariable String departureAirport,
      @PathVariable String year, @PathVariable String month, @PathVariable String day,
      @RequestParam("partyId") String partyId, @RequestParam("ruleName") Optional<String> ruleName) {

    Map<String, Object> requestDataMap = new HashMap<>();
    requestDataMap.put(ISvcContainer.CARRIER, carrier);
    requestDataMap.put(ISvcContainer.FLIGHT_NUMBER, flightNumber);
    requestDataMap.put(ISvcContainer.DEPARTING_AIRPORT, departureAirport);
    requestDataMap.put(ISvcContainer.YEAR, year);
    requestDataMap.put(ISvcContainer.MONTH, month);
    requestDataMap.put(ISvcContainer.DAY, day);
    requestDataMap.put(ISvcContainer.PARTY_ID, partyId);
    requestDataMap.put(ISvcContainer.RULE_NAME, ruleName);
    requestDataMap.put(ISvcContainer.REQUEST_TYPE, ISvcContainer.CREATE_ALERT_RULE);
    RequestData requestData = new RequestData();
    requestData.setRequestDataMap(requestDataMap);
    return flightAlertsService.createFlightRuleByDeparture(requestData);
  }

}
