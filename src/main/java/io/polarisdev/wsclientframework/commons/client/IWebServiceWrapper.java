package io.polarisdev.wsclientframework.commons.client;

import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import io.polarisdev.wsclientframework.commons.exception.WServiceException;

public interface IWebServiceWrapper {

  public ISvcContainer process(ISvcContainer request) throws WServiceException;

}
