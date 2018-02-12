package io.polarisdev.wsclientframework.commons.client.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.polarisdev.wsclientframework.IService;
import io.polarisdev.wsclientframework.commons.client.IServiceClient;
import io.polarisdev.wsclientframework.commons.client.IServiceManager;
import io.polarisdev.wsclientframework.commons.client.IWebServiceWrapper;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import io.polarisdev.wsclientframework.commons.container.impl.RequestContainer;
import io.polarisdev.wsclientframework.commons.container.impl.ResponseContainer;
import io.polarisdev.wsclientframework.commons.exception.WSExceptionConstants;
import io.polarisdev.wsclientframework.commons.exception.WServiceException;
import io.polarisdev.wsclientframework.commons.framework.ServiceOrchestrator;
import io.polarisdev.wsclientframework.commons.framework.orchestration.impl.OrchestrationEndPoint;
import io.polarisdev.wsclientframework.commons.framework.orchestration.impl.RetryParam;
import io.polarisdev.wsclientframework.commons.transformer.ITransformer;
import io.polarisdev.wsclientframework.system.annotation.ApplicationComponent;
import io.polarisdev.wsclientframework.system.annotation.WebServiceComponent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationComponent
public class ServiceWrapper implements IWebServiceWrapper {

  @Autowired
  IServiceManager serviceManager;

  @Autowired
  BeanFactory beanFactory;

  @Override
  public ISvcContainer process(ISvcContainer svcContainer) throws WServiceException {
    ISvcContainer container = null;
    String serviceName = (String) svcContainer.getSvcField(svcContainer.SERVICE_NAME);
    if (StringUtils.isBlank(serviceName))
      throw new WServiceException(WSExceptionConstants.NO_SERVICE_NAME,
          WSExceptionConstants.NO_SERVICE_NAME_MSG);

    IService service = serviceManager.get(serviceName);
    WebServiceComponent webService = service.getClass().getAnnotation(WebServiceComponent.class);
    if (!Optional.ofNullable(webService).isPresent()) {
      log.error("service is not implementing  WebService annotation");
      throw new RuntimeException(
          serviceName.concat("service is not implementing  WebService annotation "));
    }

    String serviceClientName = webService.serviceClient();
    String serviceByName = webService.serviceName();
    String transformer = webService.transformer();
    if (StringUtils.isNotBlank(serviceByName) && !serviceByName.equalsIgnoreCase(serviceName)) {
      log.error("Calling web service is mis match with configured service");
      throw new RuntimeException("Calling web service is mis match with configured service");
    }
    IServiceClient serviceClient = (IServiceClient) beanFactory.getBean(serviceClientName);
    ITransformer serviceTransFormer = (ITransformer) beanFactory.getBean(transformer);
    List<OrchestrationEndPoint> endpointCollection = new ArrayList<>();
    OrchestrationEndPoint orchestrationEndPoint = new OrchestrationEndPoint();
    orchestrationEndPoint.setEndPointObj(serviceClient);
    orchestrationEndPoint.setTransformer(serviceTransFormer);
    orchestrationEndPoint.setRetryParam(new RetryParam());
    endpointCollection.add(orchestrationEndPoint);
    container = this.doProcess(svcContainer, serviceName, endpointCollection);
    return container;
  }


  private ISvcContainer doProcess(ISvcContainer request, String serviceName,
      List<OrchestrationEndPoint> endpointCollection) throws WServiceException {

    log.debug("Starting ISvcContainer process(ISvcContainer request) for{} ", serviceName);
    ResponseContainer responseContainer = new ResponseContainer();
    ISvcContainer svcContainer = null;
    try {
      RequestContainer requestContainer = RequestContainerPreparator.getRequestContainer(request);
      responseContainer =
          ServiceOrchestrator.orchestrate(endpointCollection, requestContainer, responseContainer);
      svcContainer = (ISvcContainer) responseContainer.getResponse();

    } catch (final WServiceException e) {
      log.error("Web Service Error when processiong {} ,{}", serviceName, e.getMessage());
    } catch (final Exception e) {
      log.error("Exception occured when processiong {} ,{}", serviceName, e.getMessage());
    }
    log.debug("service has been completed with service name{}", serviceName);
    return svcContainer;
  }

}
