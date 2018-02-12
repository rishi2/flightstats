
package io.polarisdev.wrsflightservice.alert.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightStatusUpdate {

  private UpdatedAt updatedAt;
  private String source;
  private UpdatedTextFields updatedTextFields;
  private UpdatedDateFields updatedDateFields;

}
