
package io.polarisdev.wrsflightservice.alert.notification;

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
  private RuleEvents ruleEvents;
  private Object nameValues;
  private Delivery delivery;

}
