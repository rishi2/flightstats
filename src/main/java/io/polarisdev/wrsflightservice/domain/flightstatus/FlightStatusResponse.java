package io.polarisdev.wrsflightservice.domain.flightstatus;

import java.util.List;
import io.polarisdev.wrsflightservice.domain.Error;
import io.polarisdev.wrsflightservice.domain.Appendix;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FlightStatusResponse {

  private Appendix appendix;

  private List<FlightStatus> flightStatuses;

  private Error error;
}
