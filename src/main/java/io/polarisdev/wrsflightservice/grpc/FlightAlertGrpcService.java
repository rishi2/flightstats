package io.polarisdev.wrsflightservice.grpc;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import io.grpc.stub.StreamObserver;
import io.polarisdev.flight.proto.service.FlightAlertServiceGrpc;
import io.polarisdev.flight.proto.service.servicetypes.FlightProto.CreateFlightRuleByDepartureRequest;
import io.polarisdev.flight.proto.service.servicetypes.FlightProto.CreateFlightRuleByDepartureResponse;
import io.polarisdev.wrsflightservice.request.data.RequestData;
import io.polarisdev.wrsflightservice.service.FlightAlertService;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;


@GRpcService
public class FlightAlertGrpcService extends FlightAlertServiceGrpc.FlightAlertServiceImplBase {

  @Autowired
  private FlightAlertService flightAlertsService;

  @Override
  public void createFlightRuleByDeparture(CreateFlightRuleByDepartureRequest request,
      StreamObserver<CreateFlightRuleByDepartureResponse> responseObserver) {

    CreateFlightRuleByDepartureResponse.Builder createFlightRuleByDepartureResponse =
        CreateFlightRuleByDepartureResponse.newBuilder();
    try {
      String flightRuleId =
          flightAlertsService.createFlightRuleByDeparture(prepareRequestData(request));
      if (flightRuleId.equalsIgnoreCase(ISvcContainer.ERROR)) {
        responseObserver.onNext(createFlightRuleByDepartureResponse
            .setStatus(CreateFlightRuleByDepartureResponse.Status.ERROR).build());
      } else {
        responseObserver.onNext(createFlightRuleByDepartureResponse
            .setStatus(CreateFlightRuleByDepartureResponse.Status.SUCCESS).setRuleId(flightRuleId)
            .build());
      }
    } catch (final Exception e) {
      responseObserver.onNext(createFlightRuleByDepartureResponse
          .setStatus(CreateFlightRuleByDepartureResponse.Status.ERROR).build());
    }
    responseObserver.onCompleted();
  }

  /**
   * preparing request data to call service method.
   * 
   * @param request
   * @return
   */
  private RequestData prepareRequestData(CreateFlightRuleByDepartureRequest request) {
    RequestData requestData = new RequestData();
    LocalDate localDate = LocalDateTime
        .ofInstant(Instant.ofEpochSecond(request.getDepartureDate().getSeconds(),
            request.getDepartureDate().getNanos()), ZoneId.of(ISvcContainer.UTC_TIME))
        .toLocalDate();
    Map<String, Object> requestDataMap = new HashMap<>();
    requestDataMap.put(ISvcContainer.CARRIER, request.getAirlineCode());
    requestDataMap.put(ISvcContainer.FLIGHT_NUMBER, request.getFlightNumber());
    requestDataMap.put(ISvcContainer.DEPARTING_AIRPORT, request.getDepartureAirport());
    requestDataMap.put(ISvcContainer.PARTY_ID, request.getPartyId());
    requestDataMap.put(ISvcContainer.YEAR, localDate.getYear());
    requestDataMap.put(ISvcContainer.MONTH, localDate.getMonthValue());
    requestDataMap.put(ISvcContainer.DAY, localDate.getDayOfMonth());
    requestDataMap.put(ISvcContainer.RULE_NAME, request.getRuleName());
    // this request is for flight alert create rule
    requestDataMap.put(ISvcContainer.REQUEST_TYPE, ISvcContainer.CREATE_ALERT_RULE);
    requestData.setRequestDataMap(requestDataMap);
    return requestData;
  }
}
