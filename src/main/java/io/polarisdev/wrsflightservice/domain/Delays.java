package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;

@Getter
@Setter
@ProtoClass(io.polarisdev.flight.proto.data.FlightData.Delays.class)
public class Delays {

  @ProtoField
  private int departureGateDelayMinutes;

  @ProtoField
  private int departureRunwayDelayMinutes;

  @ProtoField
  private int arrivalGateDelayMinutes;

  @ProtoField
  private int arrivalRunwayDelayMinutes;

}


