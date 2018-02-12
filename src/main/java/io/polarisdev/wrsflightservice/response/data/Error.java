package io.polarisdev.wrsflightservice.response.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {

  private String httpStatusCode;

  private String errorCode;

  private String errorId;

  private String errorMessage;

}
