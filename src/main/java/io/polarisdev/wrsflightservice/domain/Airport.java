package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;

@Setter
@Getter
@ProtoClass(io.polarisdev.flight.proto.data.FlightData.Airport.class)
public class Airport {

  @ProtoField(name ="airportCode")
  private String fs;

  private String iata;

  private String icao;

  private String faa;

  @ProtoField(name ="airportName")
  private String name;

  private String street1;

  @ProtoField(name ="airportCity")
  private String city;

  @ProtoField(name ="airportCityCode")
  private String cityCode;

  private String stateCode;

  private String postalCode;

  private String countryCode;

  private String countryName;

  private String regionName;

  private String timeZoneRegionName;

  private String weatherZone;

  private String localTime;

  private Integer utcOffsetHours;

  private Double latitude;

  private Double longitude;

  private Integer elevationFeet;

  private Integer classification;

  private Boolean active;

  private String delayIndexUrl;

  private String weatherUrl;
}
