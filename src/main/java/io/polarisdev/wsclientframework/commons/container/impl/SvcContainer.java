package io.polarisdev.wsclientframework.commons.container.impl;

import java.util.HashMap;
import java.util.Map;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;

public class SvcContainer implements ISvcContainer {
  private Map<String, Object> requestMap = new HashMap<>();

  @Override
  public Map<String, Object> getSvcMap() {
    return requestMap;
  }

  @Override
  public void setSvcMap(Map<String, Object> svcMap) {
    this.requestMap = svcMap;
  }

  @Override
  public void setSvcField(String sKey, Object oValue) {
    this.requestMap.put(sKey, oValue);
  }

  @Override
  public Object getSvcField(String sKey) {
    return this.requestMap.get(sKey);
  }
}
