package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FlightDurations {

  private Integer scheduledBlockMinutes;

  private Integer scheduledAirMinutes;

  private Integer scheduledTaxiOutMinutes;

  private Integer scheduledTaxiInMinutes;
}
