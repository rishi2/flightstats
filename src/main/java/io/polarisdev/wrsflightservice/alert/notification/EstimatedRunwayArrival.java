
package io.polarisdev.wrsflightservice.alert.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstimatedRunwayArrival {

  private String dateLocal;
  private String dateUtc;

}
