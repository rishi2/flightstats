package io.polarisdev.wrsflightservice.grpc;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.protobuf.Timestamp;

import io.grpc.stub.StreamObserver;
import io.polarisdev.flight.proto.data.FlightData.FlightDetail;
import io.polarisdev.flight.proto.service.servicetypes.FlightProto.RetrieveFlightStatusByDepartureDateRequest;
import io.polarisdev.flight.proto.service.servicetypes.FlightProto.RetrieveFlightStatusByDepartureDateResponse;
import io.polarisdev.flight.proto.service.servicetypes.FlightProto.RetrieveFlightStatusByDepartureDateResponse.Builder;
import io.polarisdev.wrsflightservice.request.data.RequestData;
import io.polarisdev.wrsflightservice.response.data.flightstatus.FilteredFlightStatus;
import io.polarisdev.wrsflightservice.response.data.flightstatus.FilteredFlightStatusResponse;
import io.polarisdev.wrsflightservice.service.FlightStatusService;
import net.badata.protobuf.converter.Converter;

@RunWith(MockitoJUnitRunner.class)
public class FlighStatsGrpcServiceTest {

  @Mock
  private FlightStatusService mockedFlightStatusService;

  @InjectMocks
  private FlighStatsGrpcService flighStatsGrpcService;

  @Mock
  private StreamObserver<RetrieveFlightStatusByDepartureDateResponse> retrieveFlightStatusByDepartureDateResponse;

  @Mock
  private List<FilteredFlightStatus> mockedFilteredFlightStatusList;


  @Test
  public void retrieveFlightStatusByDepartureDate_given_valid_RetrieveFlightStatusByDepartureDateRequest_verify_success() {

    RetrieveFlightStatusByDepartureDateRequest retrieveFlightStatusByDepartureDateRequest =
        mock(RetrieveFlightStatusByDepartureDateRequest.class);
    List<FilteredFlightStatus> dummyFilteredFlightStatusList =
        new ArrayList<FilteredFlightStatus>();
    dummyFilteredFlightStatusList.add(new FilteredFlightStatus());
    Builder retrieveFlightStatusByDepartureDateResponseBuilder =
        RetrieveFlightStatusByDepartureDateResponse.newBuilder();
    retrieveFlightStatusByDepartureDateResponseBuilder.addFlightsDetail(
        Converter.create().toProtobuf(FlightDetail.class, dummyFilteredFlightStatusList.get(0)));

    /*
     * Mocking RequestData class which uses as request for FlightStatusService
     */
    when(retrieveFlightStatusByDepartureDateRequest.getAirlineCode()).thenReturn("AA");
    when(retrieveFlightStatusByDepartureDateRequest.getFlightNumber()).thenReturn("100");
    when(retrieveFlightStatusByDepartureDateRequest.getTimestamp())
        .thenReturn(Timestamp.newBuilder().setSeconds(1507626678).build());
    FilteredFlightStatusResponse mockedFilteredFlightStatusResponse =
        mock(FilteredFlightStatusResponse.class);
    when(mockedFlightStatusService.getFlightStatusByDepartureDate(any(RequestData.class)))
        .thenReturn(mockedFilteredFlightStatusResponse);
    when(mockedFilteredFlightStatusResponse.getFlightStatuses())
        .thenReturn(dummyFilteredFlightStatusList);
    flighStatsGrpcService.retrieveFlightStatusByDepartureDate(
        retrieveFlightStatusByDepartureDateRequest, retrieveFlightStatusByDepartureDateResponse);
    verify(mockedFlightStatusService, times(1))
        .getFlightStatusByDepartureDate(any(RequestData.class));
    assertNotNull(retrieveFlightStatusByDepartureDateResponse);
    // Verifying that OnNext method of stream observer is called with
    // SUCCESS status.
    verify(retrieveFlightStatusByDepartureDateResponse)
        .onNext(retrieveFlightStatusByDepartureDateResponseBuilder
            .setStatus(RetrieveFlightStatusByDepartureDateResponse.Status.SUCCESS).build());
  }

  @Test
  public void retrieveFlightStatusByDepartureDate_given_invalid_RetrieveFlightStatusByDepartureDateRequest_verify_error() {
    RetrieveFlightStatusByDepartureDateRequest retrieveFlightStatusByDepartureDateRequest =
        mock(RetrieveFlightStatusByDepartureDateRequest.class);

    // Airline Code is not blank/NUll in retrieve flight status request
    when(retrieveFlightStatusByDepartureDateRequest.getAirlineCode()).thenReturn("");
    flighStatsGrpcService.retrieveFlightStatusByDepartureDate(
        retrieveFlightStatusByDepartureDateRequest, retrieveFlightStatusByDepartureDateResponse);
    verify(mockedFlightStatusService, never())
        .getFlightStatusByDepartureDate(any(RequestData.class));
    assertNotNull(retrieveFlightStatusByDepartureDateResponse);

    // Verifying that OnNext method of stream observer is called with
    // ERROR status.
    verify(retrieveFlightStatusByDepartureDateResponse)
        .onNext(RetrieveFlightStatusByDepartureDateResponse.newBuilder()
            .setStatus(RetrieveFlightStatusByDepartureDateResponse.Status.ERROR).build());

  }

  @Test
  public void retrieveFlightStatusByDepartureDate_given_valid_RetrieveFlightStatusByDepartureDateRequest_but_service_fails_verify_error() {

    RetrieveFlightStatusByDepartureDateRequest retrieveFlightStatusByDepartureDateRequest =
        mock(RetrieveFlightStatusByDepartureDateRequest.class);
    /*
     * Mocking RequestData class which uses as request for FlightStatusService
     */
    when(retrieveFlightStatusByDepartureDateRequest.getAirlineCode()).thenReturn("AA");
    when(retrieveFlightStatusByDepartureDateRequest.getFlightNumber()).thenReturn("100");
    // Date is one week before.
    when(retrieveFlightStatusByDepartureDateRequest.getTimestamp())
        .thenReturn(Timestamp.newBuilder().setSeconds(1506861281).build());
    FilteredFlightStatusResponse mockedFilteredFlightStatusResponse =
        mock(FilteredFlightStatusResponse.class);
    when(mockedFlightStatusService.getFlightStatusByDepartureDate(any(RequestData.class)))
        .thenReturn(mockedFilteredFlightStatusResponse);
    flighStatsGrpcService.retrieveFlightStatusByDepartureDate(
        retrieveFlightStatusByDepartureDateRequest, retrieveFlightStatusByDepartureDateResponse);
    verify(mockedFlightStatusService, times(1))
        .getFlightStatusByDepartureDate(any(RequestData.class));
    assertNotNull(retrieveFlightStatusByDepartureDateResponse);
    // Verifying that OnNext method of stream observer is called with
    // ERROR status.
    verify(retrieveFlightStatusByDepartureDateResponse)
        .onNext(RetrieveFlightStatusByDepartureDateResponse.newBuilder()
            .setStatus(RetrieveFlightStatusByDepartureDateResponse.Status.ERROR).build());
  }

}
