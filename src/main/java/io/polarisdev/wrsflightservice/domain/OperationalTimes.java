package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OperationalTimes {

  private PublishedDeparture publishedDeparture;

  private PublishedArrival publishedArrival;

  private ScheduledGateDeparture scheduledGateDeparture;

  private EstimatedGateDeparture estimatedGateDeparture;

  private FlightPlanPlannedDeparture flightPlanPlannedDeparture;

  private ScheduledGateArrival scheduledGateArrival;

  private EstimatedGateArrival estimatedGateArrival;

  private FlightPlanPlannedArrival flightPlanPlannedArrival;
  
  private EstimatedRunwayDeparture estimatedRunwayDeparture;
  
  private EstimatedRunwayArrival estimatedRunwayArrival;
}
