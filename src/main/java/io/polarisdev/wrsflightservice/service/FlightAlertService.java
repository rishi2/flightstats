package io.polarisdev.wrsflightservice.service;

import io.polarisdev.wrsflightservice.request.data.RequestData;

public interface FlightAlertService {

  /**
   * Create a flight rule to be monitored for a specific flight departing from an airport on the
   * given day. Returns the fully constructed flight rule that was created.
   * 
   * @param requestData
   * @return
   */
  public String createFlightRuleByDeparture(RequestData requestData);
}
