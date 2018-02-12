
package io.polarisdev.wrsflightservice.alert.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Airport {

  private String fs;
  private String iata;
  private String icao;
  private String faa;
  private String name;
  private String street1;
  private String city;
  private String cityCode;
  private String stateCode;
  private String postalCode;
  private String countryCode;
  private String countryName;
  private String regionName;
  private String timeZoneRegionName;
  private String weatherZone;
  private String localTime;
  private String utcOffsetHours;
  private String latitude;
  private String longitude;
  private String elevationFeet;
  private String classification;
  private String active;
}
