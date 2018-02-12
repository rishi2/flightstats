package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;

@Setter
@Getter
@ProtoClass(io.polarisdev.flight.proto.data.FlightData.AirportResources.class)
public class AirportResources {

  @ProtoField(name = "departureGate")
  private String departureGate;

  @ProtoField(name = "arrivalTerminal")
  private String arrivalTerminal;

  @ProtoField(name = "arrivalGate")
  private String arrivalGate;

  @ProtoField(name = "baggageCounter")
  private String baggage;

  @ProtoField(name = "departureTerminal")
  private String departureTerminal;
}
