package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstimatedRunwayDeparture {

  private String dateLocal;

  private String dateUtc;
}
