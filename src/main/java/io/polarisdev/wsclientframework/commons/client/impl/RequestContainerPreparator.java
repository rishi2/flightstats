package io.polarisdev.wsclientframework.commons.client.impl;

import io.polarisdev.wsclientframework.commons.container.impl.RequestContainer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestContainerPreparator {

  /**
   * Prepares the request container with global configurations for the request type
   * 
   * @param Object request
   * @return Request Container
   */
  public static RequestContainer getRequestContainer(Object request) {

    if (log.isDebugEnabled())
      log.debug("Entering getRequestContainer(Object request) ");

    RequestContainer requestContainer = new RequestContainer();

    // Add global request logic
    requestContainer.setRequest(request);

    if (log.isDebugEnabled())
      log.debug("Returning getRequestContainer(Object request) ");
    return requestContainer;
  }
}
