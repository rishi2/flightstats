package io.polarisdev.wrsflightservice.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Appendix {

  private List<Airline> airlines = null;

  private List<Airport> airports = null;

  private List<Equipment> equipments = null;
}
