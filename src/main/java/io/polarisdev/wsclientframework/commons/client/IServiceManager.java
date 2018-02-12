package io.polarisdev.wsclientframework.commons.client;

import io.polarisdev.wsclientframework.IService;

public interface IServiceManager {

  public void registerService(String key, IService service) throws Exception;

  public IService get(String key);
}
