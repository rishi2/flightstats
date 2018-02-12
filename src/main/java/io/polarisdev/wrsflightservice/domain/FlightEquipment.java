package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FlightEquipment {

  private String scheduledEquipmentIataCode;

  private String actualEquipmentIataCode;

  private String tailNumber;
}
