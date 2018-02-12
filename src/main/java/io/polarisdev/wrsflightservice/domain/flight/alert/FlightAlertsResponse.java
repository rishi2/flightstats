
package io.polarisdev.wrsflightservice.domain.flight.alert;

import io.polarisdev.wrsflightservice.domain.Appendix;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightAlertsResponse {

  private Request request;
  private Rule rule;
  private Appendix appendix;
  private AlertCapabilities alertCapabilities;
}
