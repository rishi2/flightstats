package io.polarisdev.wrsflightservice.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Error {

  private String httpStatusCode;

  private String errorCode;

  private String errorId;

  private String errorMessage;
}
