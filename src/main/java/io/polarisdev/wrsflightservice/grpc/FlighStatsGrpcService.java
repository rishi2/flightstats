package io.polarisdev.wrsflightservice.grpc;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang.StringUtils;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.protobuf.Timestamp;
import io.polarisdev.flight.proto.data.FlightData.FlightDetail;
import io.polarisdev.flight.proto.service.FlightStatusServiceGrpc.FlightStatusServiceImplBase;
import io.polarisdev.flight.proto.service.servicetypes.FlightProto.RetrieveFlightStatusByDepartureDateRequest;
import io.polarisdev.flight.proto.service.servicetypes.FlightProto.RetrieveFlightStatusByDepartureDateResponse;
import io.polarisdev.wrsflightservice.request.data.RequestData;
import io.polarisdev.wrsflightservice.response.data.flightstatus.FilteredFlightStatus;
import io.polarisdev.wrsflightservice.response.data.flightstatus.FilteredFlightStatusResponse;
import io.polarisdev.wrsflightservice.service.FlightStatusService;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import lombok.extern.slf4j.Slf4j;
import net.badata.protobuf.converter.Converter;


@GRpcService
@Slf4j
public class FlighStatsGrpcService extends FlightStatusServiceImplBase {

  @Autowired
  private FlightStatusService flightStatusService;


  /**
   * Retrieve FLight Status on the basis of given departure date , airlineCode and flight number
   * 
   * @param io.polarisdev.flight.proto.service.servicetypes.FlightProto.RetrieveFlightStatusByDepartureDateRequest,
   *        io.grpc.stub.StreamObserver
   */
  @Override
  public void retrieveFlightStatusByDepartureDate(
      RetrieveFlightStatusByDepartureDateRequest request,
      io.grpc.stub.StreamObserver<RetrieveFlightStatusByDepartureDateResponse> responseObserver) {
    RetrieveFlightStatusByDepartureDateResponse.Builder retrieveFlightStatusByDepartureDateResponse =
        RetrieveFlightStatusByDepartureDateResponse.newBuilder();
    try {
      if (validateRequest(request, responseObserver)) {
        RequestData requestData = prepareRequestData(request);

        FilteredFlightStatusResponse filteredFlightStatusResponse =
            flightStatusService.getFlightStatusByDepartureDate(requestData);

        if (filteredFlightStatusResponse.getFlightStatuses() != null
            && !filteredFlightStatusResponse.getFlightStatuses().isEmpty()) {
          Iterator<FilteredFlightStatus> filteredFlightStatusIterator =
              filteredFlightStatusResponse.getFlightStatuses().iterator();
          while (filteredFlightStatusIterator.hasNext()) {
            retrieveFlightStatusByDepartureDateResponse.addFlightsDetail(Converter.create()
                .toProtobuf(FlightDetail.class, filteredFlightStatusIterator.next()));
          }
          responseObserver.onNext(retrieveFlightStatusByDepartureDateResponse
              .setStatus(RetrieveFlightStatusByDepartureDateResponse.Status.SUCCESS).build());
        } else {
          responseObserver.onNext(retrieveFlightStatusByDepartureDateResponse
              .setStatus(RetrieveFlightStatusByDepartureDateResponse.Status.ERROR).build());
        }

      }
    } catch (Exception ex) {
      log.error("Exception in FlighStatsGrpcService : {} ", ex.getMessage());
      responseObserver.onNext(retrieveFlightStatusByDepartureDateResponse
          .setStatus(RetrieveFlightStatusByDepartureDateResponse.Status.ERROR).build());
    }
    responseObserver.onCompleted();
  }


  /**
   * Prepare Request Data with given values
   * 
   * @param request
   * @return
   */
  private RequestData prepareRequestData(RetrieveFlightStatusByDepartureDateRequest request) {
    Timestamp timeStamp = request.getTimestamp();
    LocalDate localDate =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(timeStamp.getSeconds(), timeStamp.getNanos()),
            ZoneId.of("UTC")).toLocalDate();
    RequestData requestData = new RequestData();
    Map<String, Object> requestDataMap = new HashMap<>();
    requestDataMap.put(ISvcContainer.CARRIER, request.getAirlineCode());
    requestDataMap.put(ISvcContainer.FLIGHT_NUMBER, request.getFlightNumber());
    requestDataMap.put(ISvcContainer.YEAR, localDate.getYear());
    requestDataMap.put(ISvcContainer.MONTH, localDate.getMonthValue());
    requestDataMap.put(ISvcContainer.DAY, localDate.getDayOfMonth());
    requestDataMap.put(ISvcContainer.REQUEST_TYPE, ISvcContainer.FLIGHT_STATUS);
    requestData.setRequestDataMap(requestDataMap);
    return requestData;
  }


  /**
   * Validation of given input
   * 
   * @param request
   * @param responseObserver
   * @return boolean
   */
  private boolean validateRequest(RetrieveFlightStatusByDepartureDateRequest request,
      io.grpc.stub.StreamObserver<RetrieveFlightStatusByDepartureDateResponse> responseObserver) {

    RetrieveFlightStatusByDepartureDateResponse.Builder retrieveFlightStatusByDepartureDateResponseBuiilder =
        RetrieveFlightStatusByDepartureDateResponse.newBuilder();

    if (StringUtils.isBlank(request.getAirlineCode())) {
      responseObserver.onNext(retrieveFlightStatusByDepartureDateResponseBuiilder
          .setStatus(RetrieveFlightStatusByDepartureDateResponse.Status.ERROR).build());
      log.error("Error: AirlineCode is blank ");
      return false;
    }

    if (StringUtils.isBlank(request.getFlightNumber())) {
      responseObserver.onNext(retrieveFlightStatusByDepartureDateResponseBuiilder
          .setStatus(RetrieveFlightStatusByDepartureDateResponse.Status.ERROR).build());
      log.error("Error: FlightNumber is blank");
      return false;
    }

    RequestData requestData = prepareRequestData(request);

    if (!Optional.ofNullable(requestData.getRequestDataMap().get(ISvcContainer.YEAR)).isPresent()) {
      responseObserver.onNext(retrieveFlightStatusByDepartureDateResponseBuiilder
          .setStatus(RetrieveFlightStatusByDepartureDateResponse.Status.ERROR).build());
      log.error("Error: year is not present");
      return false;
    }

    if (!Optional.ofNullable(requestData.getRequestDataMap().get(ISvcContainer.MONTH))
        .isPresent()) {
      responseObserver.onNext(retrieveFlightStatusByDepartureDateResponseBuiilder
          .setStatus(RetrieveFlightStatusByDepartureDateResponse.Status.ERROR).build());
      log.error("Error: month is not present");
      return false;
    }

    if (!Optional.ofNullable(requestData.getRequestDataMap().get(ISvcContainer.DAY)).isPresent()) {
      responseObserver.onNext(retrieveFlightStatusByDepartureDateResponseBuiilder
          .setStatus(RetrieveFlightStatusByDepartureDateResponse.Status.ERROR).build());
      log.error("Error: day is not present");
      return false;
    }
    return true;
  }
}
