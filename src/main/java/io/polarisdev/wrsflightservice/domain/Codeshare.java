package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Codeshare {

  private String fsCode;

  private String flightNumber;

  private String relationship;
}
