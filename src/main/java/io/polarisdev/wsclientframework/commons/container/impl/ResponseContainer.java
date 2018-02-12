package io.polarisdev.wsclientframework.commons.container.impl;

import io.polarisdev.wsclientframework.commons.exception.ErrorWarningType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseContainer {

  /**
   * Response would hold the response object returned by the end point.
   */
  private Object response;
  
  private ErrorWarningType errorWarningType;
}
