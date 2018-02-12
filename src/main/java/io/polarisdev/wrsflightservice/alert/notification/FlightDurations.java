
package io.polarisdev.wrsflightservice.alert.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightDurations {

  private String scheduledBlockMinutes;
  private String blockMinutes;
  private String scheduledAirMinutes;
  private String airMinutes;
  private String scheduledTaxiOutMinutes;
  private String taxiOutMinutes;
  private String scheduledTaxiInMinutes;
  private String taxiInMinutes;

}
