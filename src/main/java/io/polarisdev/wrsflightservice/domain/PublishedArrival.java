package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PublishedArrival {

  private String dateLocal;

  private String dateUtc;
}
