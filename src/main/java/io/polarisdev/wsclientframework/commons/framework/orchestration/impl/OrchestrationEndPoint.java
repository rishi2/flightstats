package io.polarisdev.wsclientframework.commons.framework.orchestration.impl;

import io.polarisdev.wsclientframework.commons.client.IServiceClient;
import io.polarisdev.wsclientframework.commons.transformer.ITransformer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrchestrationEndPoint {

  private IServiceClient endPointObj;
  private ITransformer transformer;
  private RetryParam retryParam;
}
