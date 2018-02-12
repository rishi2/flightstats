
package io.polarisdev.wrsflightservice.alert.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightStatus {

  private String flightId;
  private String carrierFsCode;
  private String flightNumber;
  private String departureAirportFsCode;
  private String arrivalAirportFsCode;
  private DepartureDate departureDate;
  private ArrivalDate arrivalDate;
  private String status;
  private Schedule schedule;
  private OperationalTimes operationalTimes;
  private Codeshares codeshares;
  private Delays delays;
  private FlightDurations flightDurations;
  private AirportResources airportResources;
  private FlightEquipment flightEquipment;
  private FlightStatusUpdates flightStatusUpdates;
  private String operatingCarrierFsCode;
  private String primaryCarrierFsCode;

}
