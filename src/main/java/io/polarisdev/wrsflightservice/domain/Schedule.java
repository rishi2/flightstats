package io.polarisdev.wrsflightservice.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Schedule {

  private String flightType;

  private String serviceClasses;

  private String restrictions;

  private List<Downline> downlines = null;

  private List<Upline> uplines = null;
}
