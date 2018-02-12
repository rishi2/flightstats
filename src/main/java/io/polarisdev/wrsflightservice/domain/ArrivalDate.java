package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArrivalDate {

  private String dateLocal;

  private String dateUtc;
}
