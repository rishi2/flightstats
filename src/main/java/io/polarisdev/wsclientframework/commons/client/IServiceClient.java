package io.polarisdev.wsclientframework.commons.client;

import io.polarisdev.wsclientframework.commons.container.impl.RequestContainer;
import io.polarisdev.wsclientframework.commons.container.impl.ResponseContainer;
import io.polarisdev.wsclientframework.commons.exception.WServiceException;

public interface IServiceClient {

  public static final String POST_METHOD = "POST";
  public static final String GET_METHOD = "GET";
  public static final String METHOD = "METHOD";

  public ResponseContainer process(RequestContainer requestContainer,
      ResponseContainer responseContainer) throws WServiceException;

}
