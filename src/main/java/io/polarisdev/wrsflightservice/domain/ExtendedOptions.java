package io.polarisdev.wrsflightservice.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExtendedOptions {

  private Map<String, Object> additionalProperties = new HashMap<>();
}
