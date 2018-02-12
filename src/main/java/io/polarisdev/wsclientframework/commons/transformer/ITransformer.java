package io.polarisdev.wsclientframework.commons.transformer;


import io.polarisdev.wsclientframework.commons.container.impl.RequestContainer;
import io.polarisdev.wsclientframework.commons.container.impl.ResponseContainer;


public interface ITransformer {

  public RequestContainer transformRequest(RequestContainer requestContainer,
      ResponseContainer responseContainer);

  public ResponseContainer transformResponse(RequestContainer requestContainer,
      ResponseContainer responseContainer);

}
