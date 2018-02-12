
package io.polarisdev.wrsflightservice.response.data;

import com.google.protobuf.Timestamp;

import lombok.Getter;
import lombok.Setter;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;

@Getter
@Setter
@ProtoClass(io.polarisdev.flight.proto.data.FlightData.OperationalTimes.class)
public class OperationalTimes {
 
  @ProtoField
  private Timestamp scheduledGateDeparture;
  
  @ProtoField
  private Timestamp estimatedGateDeparture;
  
  @ProtoField
  private Timestamp scheduledGateArrival;
  
  @ProtoField
  private Timestamp estimatedGateArrival;
  
  @ProtoField
  private Timestamp estimatedRunwayArrival;
}
