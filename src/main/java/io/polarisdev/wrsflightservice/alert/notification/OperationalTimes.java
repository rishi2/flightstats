
package io.polarisdev.wrsflightservice.alert.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationalTimes {

  private PublishedDeparture publishedDeparture;
  private PublishedArrival publishedArrival;
  private ScheduledGateDeparture scheduledGateDeparture;
  private EstimatedGateDeparture estimatedGateDeparture;
  private ActualGateDeparture actualGateDeparture;
  private FlightPlanPlannedDeparture flightPlanPlannedDeparture;
  private EstimatedRunwayDeparture estimatedRunwayDeparture;
  private ActualRunwayDeparture actualRunwayDeparture;
  private ScheduledGateArrival scheduledGateArrival;
  private EstimatedGateArrival estimatedGateArrival;
  private ActualGateArrival actualGateArrival;
  private FlightPlanPlannedArrival flightPlanPlannedArrival;
  private EstimatedRunwayArrival estimatedRunwayArrival;
  private ActualRunwayArrival actualRunwayArrival;

}
