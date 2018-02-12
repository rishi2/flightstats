
package io.polarisdev.wrsflightservice.alert.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Alert {

  private Event event;
  private String dataSource;
  private String dateTimeRecorded;
  private Rule rule;
  private FlightStatus flightStatus;
}
