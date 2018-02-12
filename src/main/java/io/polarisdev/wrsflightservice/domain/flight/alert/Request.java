
package io.polarisdev.wrsflightservice.domain.flight.alert;

import java.util.List;
import io.polarisdev.wrsflightservice.domain.Airport;
import io.polarisdev.wrsflightservice.domain.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {

  private Airport airport;
  private String url;
  private AirlineCode airlineCode;
  private FlightNumber flightNumber;
  private Date date;
  private Name name;
  private Description description;
  private Type type;
  private DeliverTo deliverTo;
  private List<Event> events = null;
  private List<Object> nameValues = null;
  private CodeType codeType;
  private ExtendedOptions extendedOptions;
}
