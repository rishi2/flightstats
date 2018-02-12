package io.polarisdev.wsclientframework;

import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import io.polarisdev.wsclientframework.commons.exception.WServiceException;

import javax.annotation.PostConstruct;


public interface IService {

  @PostConstruct
  public void register() throws Exception;

  public ISvcContainer call(Object data) throws WServiceException;
}
