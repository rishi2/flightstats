package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Equipment {

  private String iata;

  private String name;

  private Boolean turboProp;

  private Boolean jet;

  private Boolean widebody;

  private Boolean regional;
}
