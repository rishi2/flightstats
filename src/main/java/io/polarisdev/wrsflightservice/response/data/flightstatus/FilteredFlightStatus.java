
package io.polarisdev.wrsflightservice.response.data.flightstatus;

import com.google.protobuf.Timestamp;
import io.polarisdev.flight.proto.data.FlightData.FlightDetail;
import io.polarisdev.flight.proto.enums.flightstatus.FlightStatusEnum.FlightStatus;
import io.polarisdev.wrsflightservice.domain.Airline;
import io.polarisdev.wrsflightservice.domain.Airport;
import io.polarisdev.wrsflightservice.domain.AirportResources;
import io.polarisdev.wrsflightservice.domain.Delays;
import io.polarisdev.wrsflightservice.response.data.OperationalTimes;
import lombok.Getter;
import lombok.Setter;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;

@Getter
@Setter
@ProtoClass(FlightDetail.class)
public class FilteredFlightStatus {

  @ProtoField
  private Airline airline;

  @ProtoField
  private String flightNumber;

  @ProtoField
  private Timestamp departureDate;

  @ProtoField
  private Timestamp arrivalDate;

  @ProtoField
  private FlightStatus flightStatus;

  @ProtoField
  private OperationalTimes operationalTimes;

  @ProtoField
  private AirportResources airportResources;

  @ProtoField
  private Airport departureAirport;

  @ProtoField
  private Airport arrivalAirport;

  @ProtoField
  private Airport divertedAirport;

  @ProtoField
  private Delays delays;
}
