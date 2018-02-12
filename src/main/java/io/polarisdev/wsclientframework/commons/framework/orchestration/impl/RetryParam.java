package io.polarisdev.wsclientframework.commons.framework.orchestration.impl;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetryParam {
  private boolean retry;
  private int retryCount;
  private Collection<String> errorCode;

}
