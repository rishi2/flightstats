package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScheduledGateDeparture {

  private String dateLocal;

  private String dateUtc;
}
