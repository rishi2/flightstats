package io.polarisdev.wrsflightservice.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.protobuf.Timestamp;

import io.polarisdev.wrsflightservice.domain.Airline;
import io.polarisdev.wrsflightservice.domain.Airport;
import io.polarisdev.wrsflightservice.domain.flightstatus.FlightStatus;
import io.polarisdev.wrsflightservice.domain.flightstatus.FlightStatusResponse;
import io.polarisdev.wrsflightservice.request.data.RequestData;
import io.polarisdev.wrsflightservice.response.data.Error;
import io.polarisdev.wrsflightservice.response.data.OperationalTimes;
import io.polarisdev.wrsflightservice.response.data.flightstatus.FilteredFlightStatus;
import io.polarisdev.wrsflightservice.response.data.flightstatus.FilteredFlightStatusResponse;
import io.polarisdev.wrsflightservice.service.FlightService;
import io.polarisdev.wrsflightservice.service.FlightStatusService;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import io.polarisdev.wsclientframework.commons.exception.WServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * Flight Status Service which is responsible for FlightStats status API call .
 */
@Service("flightStatusServiceImpl")
@Slf4j
public class FlightStatusServiceImpl implements FlightStatusService {

  private FlightService wrsFlightService;

  /**
   * invoke constructor
   * 
   * @param wrsFlightService
   */
  public FlightStatusServiceImpl(FlightService wrsFlightService) {
    this.wrsFlightService = wrsFlightService;
  }

  /**
   * method to return filtered flight status as a response.
   * 
   * @param requestData
   * @return FilteredFlightStatusResponse
   * 
   */
  @Override
  public FilteredFlightStatusResponse getFlightStatusByDepartureDate(RequestData requestData) {

    try {
      return transformResponse(wrsFlightService.call(requestData));
    } catch (WServiceException e) {
      log.error("Excetpion in transformResponse :{}", e.getMessage());
    }
    return null;
  }

  /**
   * method used to transform response and return back to caller.
   * 
   * @param flightStatResponseContainer
   * @returnFilteredFlightStatusResponse
   */
  private FilteredFlightStatusResponse transformResponse(
      ISvcContainer flightStatResponseContainer) {

    ResponseEntity<FlightStatusResponse> response =
        (ResponseEntity<FlightStatusResponse>) flightStatResponseContainer
            .getSvcField(ISvcContainer.OUTPUT_KEY);
    return prepareCustomResponse(response.getBody());
  }

  /**
   * getCustomResponse filters actual FlightStatusResponse and return desired result.
   * 
   * @param flightStatusResponse
   * @return
   */
  private FilteredFlightStatusResponse prepareCustomResponse(
      FlightStatusResponse flightStatusResponse) {
    FilteredFlightStatusResponse filteredFlightStatusResponse = new FilteredFlightStatusResponse();

    List<FilteredFlightStatus> flightStatuses =
        filterFlightStatus(flightStatusResponse.getFlightStatuses(), flightStatusResponse);
    filteredFlightStatusResponse.setFlightStatuses(flightStatuses);
    filterErrors(flightStatusResponse, filteredFlightStatusResponse);
    return filteredFlightStatusResponse;
  }

  private void filterErrors(FlightStatusResponse flightStatusResponse,
      FilteredFlightStatusResponse filteredFlightStatusResponse) {
    if (Optional.ofNullable(flightStatusResponse.getError()).isPresent()) {
      Error error = new Error();
      error.setErrorCode(flightStatusResponse.getError().getErrorCode());
      error.setErrorMessage(flightStatusResponse.getError().getErrorMessage());
      error.setErrorId(flightStatusResponse.getError().getErrorId());
      error.setHttpStatusCode(flightStatusResponse.getError().getHttpStatusCode());
      filteredFlightStatusResponse.setError(error);
    }
  }

  /**
   * filteredFlightStatus filters actual FightStatus and return desired result.
   * 
   * @return
   */
  private List<FilteredFlightStatus> filterFlightStatus(List<FlightStatus> flightStatusList,
      FlightStatusResponse flightStatusResponse) {
    List<FilteredFlightStatus> filteredFlightStatusList = new ArrayList<>();
    for (FlightStatus flightStatus : flightStatusList) {
      FilteredFlightStatus filteredStatus = new FilteredFlightStatus();
      if (StringUtils.isNotBlank(flightStatus.getFlightNumber())) {
        filteredStatus.setFlightNumber(flightStatus.getFlightNumber());
      }
      // TODO : Status need to be refactor
      filteredStatus.setFlightStatus(
          io.polarisdev.flight.proto.enums.flightstatus.FlightStatusEnum.FlightStatus.SCHEDULED);

      Optional.ofNullable(flightStatus.getAirportResources())
          .ifPresent(aiportResources -> filteredStatus.setAirportResources(aiportResources));

      if (Optional.ofNullable(flightStatusResponse.getAppendix()).isPresent()) {
        if (null != flightStatusResponse.getAppendix().getAirlines()
            && !flightStatusResponse.getAppendix().getAirlines().isEmpty()) {
          Airline airLineInfo = flightStatusResponse.getAppendix().getAirlines().stream()
              .filter(airLine -> airLine.getFs().equalsIgnoreCase(flightStatus.getCarrierFsCode()))
              .findFirst().get();
          filteredStatus.setAirline(airLineInfo);
        }
        if (null != flightStatusResponse.getAppendix().getAirports()
            && !flightStatusResponse.getAppendix().getAirports().isEmpty()) {
          List<io.polarisdev.wrsflightservice.domain.Airport> airportList =
              flightStatusResponse.getAppendix().getAirports();
          Airport arrivalAirport = airportList.stream().filter(
              airport -> airport.getFs().equalsIgnoreCase(flightStatus.getArrivalAirportFsCode()))
              .findFirst().get();

          Airport departureAirport = airportList.stream().filter(
              airport -> airport.getFs().equalsIgnoreCase(flightStatus.getDepartureAirportFsCode()))
              .findFirst().get();

          filteredStatus.setDepartureAirport(departureAirport);
          filteredStatus.setArrivalAirport(arrivalAirport);
        }
      }

      filteredStatus.setOperationalTimes(getOperationTimes(flightStatus.getOperationalTimes()));
      if (Optional.ofNullable(flightStatus.getDepartureDate().getDateUtc()).isPresent()) {
        filteredStatus
            .setDepartureDate(convertDateToTimeStamp(flightStatus.getDepartureDate().getDateUtc()));
      }
      if (Optional.ofNullable(flightStatus.getArrivalDate().getDateUtc()).isPresent()) {
        filteredStatus
            .setArrivalDate(convertDateToTimeStamp(flightStatus.getArrivalDate().getDateUtc()));
      }
      if (Optional.ofNullable(flightStatus.getDelays()).isPresent()) {
        filteredStatus.setDelays(flightStatus.getDelays());
      }
      filteredFlightStatusList.add(filteredStatus);
    }
    return filteredFlightStatusList;
  }

  /**
   * set Operation time according to proto
   * 
   * @throws ParseException
   */
  private OperationalTimes getOperationTimes(
      io.polarisdev.wrsflightservice.domain.OperationalTimes domainOperationalTimes) {
    OperationalTimes operationTimes = null;
    try {
      operationTimes = new OperationalTimes();
      if (Optional.ofNullable(domainOperationalTimes.getEstimatedGateArrival()).isPresent()) {
        operationTimes.setEstimatedGateArrival(
            convertDateToTimeStamp(domainOperationalTimes.getEstimatedGateArrival().getDateUtc()));
      }
      if (Optional.ofNullable(domainOperationalTimes.getEstimatedGateDeparture()).isPresent()) {
        operationTimes.setEstimatedGateDeparture(convertDateToTimeStamp(
            domainOperationalTimes.getEstimatedGateDeparture().getDateUtc()));
      }
      if (Optional.ofNullable(domainOperationalTimes.getScheduledGateArrival()).isPresent()) {
        operationTimes.setScheduledGateArrival(
            convertDateToTimeStamp(domainOperationalTimes.getScheduledGateArrival().getDateUtc()));
      }
      if (Optional.ofNullable(domainOperationalTimes.getScheduledGateDeparture()).isPresent()) {
        operationTimes.setScheduledGateDeparture(convertDateToTimeStamp(
            domainOperationalTimes.getScheduledGateDeparture().getDateUtc()));
      }
      if (Optional.ofNullable(domainOperationalTimes.getEstimatedRunwayArrival()).isPresent()) {
        operationTimes.setEstimatedRunwayArrival(convertDateToTimeStamp(
            domainOperationalTimes.getEstimatedRunwayArrival().getDateUtc()));
      }
    } catch (Exception e) {
      log.error("Exception in  getOperationTimes :{}", e.getMessage());
    }
    return operationTimes;
  }

  private Timestamp convertDateToTimeStamp(String date) {
    Timestamp.Builder timeStampBuilder = Timestamp.newBuilder();
    try {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");
      return timeStampBuilder.setSeconds(dateFormat.parse(date).getTime()).build();
    } catch (ParseException ex) {
      log.error("Date Parse Exception {}", ex.getMessage());
    }
    return timeStampBuilder.build();
  }

  /**
   * TODO: yet to be implement.
   */
  @Override
  public FlightStatusResponse getFlightStatusByFlightId(RequestData requestData) {
    return null;
  }
}
