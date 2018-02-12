
package io.polarisdev.wrsflightservice.domain.flight.alert;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtendedOptions {

  private Map<String, Object> additionalProperties = new HashMap<>();

}
