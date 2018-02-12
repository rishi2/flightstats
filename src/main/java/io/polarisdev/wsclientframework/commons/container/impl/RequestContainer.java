package io.polarisdev.wsclientframework.commons.container.impl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestContainer {

  /**
   * Request would hold the request object required by the end point.
   */
  private Object request;
}
