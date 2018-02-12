
package io.polarisdev.wrsflightservice.domain.flight.alert;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertCapabilities {

  private Boolean baggage;
  private Boolean departureGateChange;
  private Boolean arrivalGateChange;
  private Boolean gateDeparture;
  private Boolean gateArrival;
  private Boolean runwayDeparture;
  private Boolean runwayArrival;

}
