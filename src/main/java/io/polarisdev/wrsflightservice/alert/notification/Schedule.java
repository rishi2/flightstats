
package io.polarisdev.wrsflightservice.alert.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Schedule {

  private String flightType;
  private String serviceClasses;
  private String restrictions;
  private Uplines uplines;

}
