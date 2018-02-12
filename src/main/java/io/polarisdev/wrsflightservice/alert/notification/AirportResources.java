
package io.polarisdev.wrsflightservice.alert.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirportResources {

  private String departureTerminal;
  private String departureGate;
  private String arrivalTerminal;
  private String arrivalGate;
  private String baggage;

}
