
package io.polarisdev.wrsflightservice.response.data.flightstatus;

import java.util.List;
import io.polarisdev.wrsflightservice.response.data.Error;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilteredFlightStatusResponse {

  private List<FilteredFlightStatus> flightStatuses;
  private Error error;
}
