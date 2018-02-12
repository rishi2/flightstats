package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;

@Setter
@Getter
@ProtoClass(io.polarisdev.flight.proto.data.FlightData.Airline.class)
public class Airline {

  @ProtoField(name = "airlineCode")
  private String fs;

  private String iata;

  private String icao;

  @ProtoField(name = "airlineName")
  private String name;

  private String phoneNumber;

  private Boolean active;

  private String requestedCode;
}
