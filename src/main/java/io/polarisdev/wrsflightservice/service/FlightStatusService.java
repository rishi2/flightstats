package io.polarisdev.wrsflightservice.service;


import io.polarisdev.wrsflightservice.domain.flightstatus.FlightStatusResponse;
import io.polarisdev.wrsflightservice.request.data.RequestData;
import io.polarisdev.wrsflightservice.response.data.flightstatus.FilteredFlightStatusResponse;

public interface FlightStatusService {

  /**
   * Returns the Flight Statuses for the given Carrier and Flight Number that departed on the given
   * date. Optionally, the departure airport may be specified.
   * 
   * @param requestData
   * @return
   */
  public FilteredFlightStatusResponse getFlightStatusByDepartureDate(RequestData requestData);

  /**
   * Returns the Flight Status associated with provided Flight ID. The Flight ID is an arbitrary
   * number that FlightStats uses to uniquely identify flights.
   * 
   * @param requestData
   * @return
   */
  public FlightStatusResponse getFlightStatusByFlightId(RequestData requestData);
}
