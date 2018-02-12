package io.polarisdev.wsclientframework.commons.framework;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import io.polarisdev.wsclientframework.commons.container.impl.RequestContainer;
import io.polarisdev.wsclientframework.commons.container.impl.ResponseContainer;
import io.polarisdev.wsclientframework.commons.exception.WServiceException;
import io.polarisdev.wsclientframework.commons.framework.orchestration.impl.OrchestrationEndPoint;

public class ServiceOrchestrator {

  public static ResponseContainer orchestrate(Collection<OrchestrationEndPoint> endPointCollection,
      RequestContainer requestContainer, ResponseContainer responseContainer)
      throws WServiceException {

    for (Iterator<OrchestrationEndPoint> iterator = endPointCollection.iterator(); iterator
        .hasNext();) {

      OrchestrationEndPoint orchestrationEndPoint = iterator.next();
      responseContainer = callEndPoint(orchestrationEndPoint, requestContainer, responseContainer);

      if ((!isValidResponse(responseContainer))
          && orchestrationEndPoint.getRetryParam().isRetry()) {
        responseContainer = retry(orchestrationEndPoint, requestContainer, responseContainer);
      }
      if (isValidResponse(responseContainer)) {
        continue;
      } else {
        break;
      }
    }
    return responseContainer;
  }

  private static ResponseContainer callEndPoint(OrchestrationEndPoint endPoint,
      RequestContainer requestContainer, ResponseContainer responseContainer)
      throws WServiceException {
    if (endPoint.getTransformer() != null) {
      requestContainer =
          endPoint.getTransformer().transformRequest(requestContainer, responseContainer);
    }
    endPoint.getEndPointObj().process(requestContainer, responseContainer);
    responseContainer =
        endPoint.getTransformer().transformResponse(requestContainer, responseContainer);
    return responseContainer;
  }

  private static ResponseContainer retry(OrchestrationEndPoint endPoint,
      RequestContainer requestContainer, ResponseContainer responseContainer)
      throws WServiceException {
    if (Optional.ofNullable(endPoint.getRetryParam()).isPresent()
        && endPoint.getRetryParam().isRetry()) {
      int retryCounter = endPoint.getRetryParam().getRetryCount();
      for (int counter = 0; counter < retryCounter; counter++) {
        responseContainer = callEndPoint(endPoint, requestContainer, responseContainer);
        if (isValidResponse(responseContainer)) {
          break;
        }
      }
    }

    return responseContainer;
  }

  private static boolean isValidResponse(ResponseContainer responseContainer) {
    boolean isValid = false;
    if (!Optional.ofNullable(responseContainer.getErrorWarningType()).isPresent()) {
      isValid = true;
    }
    return isValid;
  }
}
