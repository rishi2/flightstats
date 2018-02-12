package io.polarisdev.wrsflightservice.domain.flightstatus;

import java.util.List;

import io.polarisdev.wrsflightservice.domain.AirportResources;
import io.polarisdev.wrsflightservice.domain.ArrivalDate;
import io.polarisdev.wrsflightservice.domain.Codeshare;
import io.polarisdev.wrsflightservice.domain.Delays;
import io.polarisdev.wrsflightservice.domain.DepartureDate;
import io.polarisdev.wrsflightservice.domain.FlightDurations;
import io.polarisdev.wrsflightservice.domain.FlightEquipment;
import io.polarisdev.wrsflightservice.domain.OperationalTimes;
import io.polarisdev.wrsflightservice.domain.Schedule;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FlightStatus {

  private Integer flightId;

  private String carrierFsCode;

  private String flightNumber;

  private String departureAirportFsCode;

  private String arrivalAirportFsCode;

  private DepartureDate departureDate;

  private ArrivalDate arrivalDate;

  private String status;

  private Schedule schedule;

  private OperationalTimes operationalTimes;

  private List<Codeshare> codeshares;

  private FlightDurations flightDurations;

  private AirportResources airportResources;

  private FlightEquipment flightEquipment;
  
  private Delays delays;
}
