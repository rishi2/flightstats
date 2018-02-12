package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstimatedRunwayArrival {

  private String dateLocal;
  
  private String dateUtc;
}
