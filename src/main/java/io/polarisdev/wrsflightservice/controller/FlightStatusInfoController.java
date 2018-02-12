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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.polarisdev.wrsflightservice.request.data.RequestData;
import io.polarisdev.wrsflightservice.response.data.flightstatus.FilteredFlightStatusResponse;
import io.polarisdev.wrsflightservice.service.FlightStatusService;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin
@RestController
@RequestMapping("/flight/status")
@Slf4j
public class FlightStatusInfoController {

  private FlightStatusService flightStatusService;

  public FlightStatusInfoController(FlightStatusService flightStatusService) {
    this.flightStatusService = flightStatusService;
  }


  /**
   * Returns the Flight Statuses for the given Carrier and Flight Number that departed on the given
   * date. Optionally, the departure airport may be specified.
   * 
   * @param carrier
   * @param flightNo
   * @param depatureDate
   * @param utc
   * @param airport
   * @param codeType
   * @return
   */
  @GetMapping("/departure/{carrier}/{flightNo}/{year}/{month}/{day}")
  public String getFlightStatusByDepartureDate(@PathVariable String carrier,
      @PathVariable String flightNo, @PathVariable String year, @PathVariable String month,
      @PathVariable String day, @RequestParam("utc") Optional<String> utc,
      @RequestParam("airport") Optional<String> airport,
      @RequestParam("codeType") Optional<String> codeType) {
    try {
      Map<String, Object> requestDataMap = new HashMap<>();
      ObjectMapper objectMapper = new ObjectMapper();
      requestDataMap.put(ISvcContainer.CARRIER, carrier);
      requestDataMap.put(ISvcContainer.FLIGHT_NUMBER, flightNo);
      requestDataMap.put(ISvcContainer.YEAR, year);
      requestDataMap.put(ISvcContainer.MONTH, month);
      requestDataMap.put(ISvcContainer.DAY, day);
      if (utc.isPresent())
        requestDataMap.put(ISvcContainer.UTC, utc);
      if (airport.isPresent())
        requestDataMap.put(ISvcContainer.AIRPORT, airport.toString());
      if (codeType.isPresent())
        requestDataMap.put(ISvcContainer.CODE_TYPE, codeType.toString());
      requestDataMap.put(ISvcContainer.REQUEST_TYPE, ISvcContainer.FLIGHT_STATUS);
      RequestData requestData = new RequestData();
      requestData.setRequestDataMap(requestDataMap);
      FilteredFlightStatusResponse filteredFlightStatusResponse =
          flightStatusService.getFlightStatusByDepartureDate(requestData);
      return objectMapper.writeValueAsString(filteredFlightStatusResponse);
    } catch (JsonProcessingException e) {
      log.error("Error in getFlightStatusByDepartureDate :{}", e.getMessage());
    }
    return ISvcContainer.ERROR;
  }

}
