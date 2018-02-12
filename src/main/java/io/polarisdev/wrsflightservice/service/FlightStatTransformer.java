package io.polarisdev.wrsflightservice.service;



import java.util.Map;
import java.util.Optional;

import io.polarisdev.wrsflightservice.constant.FlightServiceConstant;
import io.polarisdev.wrsflightservice.request.data.RequestData;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import io.polarisdev.wsclientframework.commons.container.impl.RequestContainer;
import io.polarisdev.wsclientframework.commons.container.impl.ResponseContainer;
import io.polarisdev.wsclientframework.commons.transformer.ITransformer;
import io.polarisdev.wsclientframework.system.annotation.ApplicationComponent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationComponent("flightStatTransformer")
public class FlightStatTransformer implements ITransformer {

  @Override
  public RequestContainer transformRequest(RequestContainer requestContainer,
      ResponseContainer responseContainer) {

    ISvcContainer containerRequest = (ISvcContainer) requestContainer.getRequest();

    StringBuilder restURI = new StringBuilder();

    RequestData requestData = (RequestData) containerRequest.getSvcField(ISvcContainer.INPUT_KEY);

    Map<String, Object> requestDataMap = requestData.getRequestDataMap();
    try {
      if (requestDataMap.get(ISvcContainer.REQUEST_TYPE).toString()
          .equalsIgnoreCase(ISvcContainer.CREATE_ALERT_RULE)) {
        restURI.append(prepareParam(requestDataMap.get(ISvcContainer.CARRIER).toString()))
            .append(prepareParam(requestDataMap.get(ISvcContainer.FLIGHT_NUMBER).toString()))
            .append(prepareParam(ISvcContainer.FROM_AIRPORT))
            .append(prepareParam(requestDataMap.get(ISvcContainer.DEPARTING_AIRPORT).toString()))
            .append(prepareParam(ISvcContainer.DEPARTING))
            .append(prepareParam(requestDataMap.get(ISvcContainer.YEAR).toString()))
            .append(prepareParam(requestDataMap.get(ISvcContainer.MONTH).toString()))
            .append(prepareParam(requestDataMap.get(ISvcContainer.DAY).toString()))
            .append(ISvcContainer.TYPE).append(ISvcContainer.TYPE_JSON)
        .append(ISvcContainer.DELIVER_TO).append(ISvcContainer.POST_SERVICE_DELIVER_TO_URL);
            if(Optional.ofNullable(requestDataMap.get(ISvcContainer.RULE_NAME)).isPresent()){
              restURI.append(ISvcContainer.NAME)
              .append(requestDataMap.get(ISvcContainer.RULE_NAME));
            }
            
      } else {
        restURI.append(prepareParam(requestDataMap.get(ISvcContainer.CARRIER).toString()))
            .append(prepareParam(requestDataMap.get(ISvcContainer.FLIGHT_NUMBER).toString()))
            .append(prepareParam(FlightServiceConstant.DEPARUTRE_URL))
            .append(prepareParam(requestDataMap.get(ISvcContainer.YEAR).toString()))
            .append(prepareParam(requestDataMap.get(ISvcContainer.MONTH).toString()))
            .append(prepareParam(requestDataMap.get(ISvcContainer.DAY).toString()))
            .append(ISvcContainer.UTC_URL);

        if (requestDataMap.get(ISvcContainer.UTC) != null) {
          restURI.append(Boolean.valueOf(requestDataMap.get(ISvcContainer.UTC).toString()));
        }else{
          //by default, utc is false
          restURI.append(false);
        }

        if (requestDataMap.get(ISvcContainer.AIRPORT) != null)
          restURI.append(ISvcContainer.AIRPORT_URL)
              .append(requestDataMap.get(ISvcContainer.AIRPORT));

        if (requestDataMap.get(ISvcContainer.CODE_TYPE) != null)
          restURI.append(ISvcContainer.CODE_TYPE_URL)
              .append(requestDataMap.get(ISvcContainer.CODE_TYPE));
      }
    } catch (Exception e) {
      log.error("error in preparing url to call flight status api with url{}" + restURI);
    }
    // set the request type
    if (requestDataMap.get(ISvcContainer.REQUEST_TYPE).toString()
        .equalsIgnoreCase(ISvcContainer.CREATE_ALERT_RULE)) {
      containerRequest.setSvcField(ISvcContainer.INPUT_TYPE,
          requestDataMap.get(ISvcContainer.REQUEST_TYPE).toString());
      containerRequest.setSvcField(ISvcContainer.PARTY_KEY,
          requestDataMap.get(ISvcContainer.PARTY_ID).toString());
    } else {
      containerRequest.setSvcField(ISvcContainer.INPUT_TYPE,
          requestDataMap.get(ISvcContainer.REQUEST_TYPE).toString());
    }

    containerRequest.setSvcField(ISvcContainer.INPUT_KEY, restURI);
    containerRequest.setSvcField(ISvcContainer.CAN_CONTINUE, true);
    requestContainer.setRequest(containerRequest);
    return requestContainer;
  }

  @Override
  public ResponseContainer transformResponse(RequestContainer requestContainer,
      ResponseContainer responseContainer) {
    return responseContainer;
  }

  public static String prepareParam(String param) {
    return param.concat(FlightServiceConstant.BACKSLASH);
  }

}
