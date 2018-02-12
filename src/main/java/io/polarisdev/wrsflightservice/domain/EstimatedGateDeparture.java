package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstimatedGateDeparture {

  private String dateLocal;

  private String dateUtc;
}
