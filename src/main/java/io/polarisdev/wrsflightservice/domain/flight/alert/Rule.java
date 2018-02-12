
package io.polarisdev.wrsflightservice.domain.flight.alert;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rule {

  private String id;
  private String name;
  private String carrierFsCode;
  private String flightNumber;
  private String departureAirportFsCode;
  private String arrivalAirportFsCode;
  private String departure;
  private String arrival;
  private List<RuleEvent> ruleEvents;
  private List<Object> nameValues;
  private Delivery delivery;
}
