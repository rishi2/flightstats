package io.polarisdev.wrsflightservice.grpc.client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.polarisdev.flight.proto.data.FlightData;
import io.polarisdev.flight.proto.data.FlightData.FlightStatusUpdate;
import io.polarisdev.flight.proto.data.FlightData.Rule;
import io.polarisdev.flight.proto.service.FlightAlertServiceGrpc;
import io.polarisdev.flight.proto.service.servicetypes.FlightProto.ProcessFlightAlertNotificationByDepartureRequest;
import io.polarisdev.flight.proto.service.servicetypes.FlightProto.ProcessFlightAlertNotificationByDepartureResponse;
import io.polarisdev.wrsflightservice.alert.notification.AlertNotificationData;
import io.polarisdev.wrsflightservice.alert.notification.UpdatedDateField;
import io.polarisdev.wrsflightservice.alert.notification.UpdatedDateFields;
import io.polarisdev.wrsflightservice.alert.notification.UpdatedTextField;
import io.polarisdev.wrsflightservice.alert.notification.UpdatedTextFields;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class FlightAlertNotificationClient {

  private FlightAlertServiceGrpc.FlightAlertServiceBlockingStub blockingStub;
  private ManagedChannel channel;

  @Value("${flight.alert.notification.host}")
  private String flightAlertNotificationHost;

  @Value("${flight.alert.notification.port}")
  private int flightAlertNotifcationPort;

  /**
   * Make Connection with GRPC protocol
   */
  @PostConstruct
  private void init() {
    channel =
        ManagedChannelBuilder.forAddress(flightAlertNotificationHost, flightAlertNotifcationPort)
            .usePlaintext(true).build();
    blockingStub = FlightAlertServiceGrpc.newBlockingStub(channel);
  }

  /**
   * shutdown channel
   * 
   * @throws InterruptedException
   */
  @PreDestroy
  private void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /**
   * submit alert notification data to grpc service
   * 
   * @param alertNotificationData
   * @param userId
   * @return ProcessFlightAlertNotificationByDepartureResponse
   */
  @Retryable(backoff = @Backoff(multiplierExpression = "2", delay = 50L, maxDelay = 300L),
      maxAttempts = 3, include = {io.grpc.StatusRuntimeException.class})
  public ProcessFlightAlertNotificationByDepartureResponse processFlightAlertNotificationData(
      AlertNotificationData alertNotificationData, String userId) {

    log.debug("Make Grpc connection to process flight alert notification data");
    try {
      io.polarisdev.flight.proto.data.FlightData.Alert prepareAlertRequest =
          prepareRequest(alertNotificationData);
      ProcessFlightAlertNotificationByDepartureRequest processFlightAlertNotificationByDepartureRequest =
          ProcessFlightAlertNotificationByDepartureRequest.newBuilder()
              .setAlert(prepareAlertRequest).build();
      // when caller service is implemented, then we get value of
      // processFlightAlertNotificationByDepartureResponse object
      ProcessFlightAlertNotificationByDepartureResponse processFlightAlertNotificationByDepartureResponse =
          blockingStub.processFlightAlertNotificationByDeparture(
              processFlightAlertNotificationByDepartureRequest);
      log.debug(" process flight alert notification:: {}",
          processFlightAlertNotificationByDepartureResponse);
      return processFlightAlertNotificationByDepartureResponse;
    } catch (final io.grpc.StatusRuntimeException e) {
      final io.grpc.Status status = e.getStatus();
      log.error("Exception calling Order Grpc Service . Status: {}:{}", status.getCode(),
          status.getDescription());
      throw e;
    } catch (final Exception e) {
      log.error("error in submitFlightAlertNotificationData:{}");
      throw e;
    }
  }

  /**
   * preparing request
   * 
   * @param alertNotificationData
   * @return FlightData.Alert
   */
  private FlightData.Alert prepareRequest(AlertNotificationData alertNotificationData) {
    FlightStatusUpdate.Builder flightStatusUpdateBuilder = FlightStatusUpdate.newBuilder();
    prepareFlightStatusUpdate(flightStatusUpdateBuilder, alertNotificationData);
    return FlightData.Alert.newBuilder()
        .setDataSource(alertNotificationData.getAlert().getDataSource())
        .setRule(Rule.newBuilder().setRuleId(alertNotificationData.getAlert().getRule().getId())
            .setRuleName(alertNotificationData.getAlert().getRule().getName()).build())
        .setFlightStatusUpdate(flightStatusUpdateBuilder.build())
        .setFlightAlertEventType(alertNotificationData.getAlert().getEvent().getType()).build();
  }

  /**
   * preparing flight status update
   * 
   * @param flightStatusUpdateBuilder
   * @param alertNotificationData
   */
  private void prepareFlightStatusUpdate(FlightStatusUpdate.Builder flightStatusUpdateBuilder,
      AlertNotificationData alertNotificationData) {
    List<io.polarisdev.wrsflightservice.alert.notification.FlightStatusUpdate> flightStatusUpdateList =
        alertNotificationData.getAlert().getFlightStatus().getFlightStatusUpdates()
            .getFlightStatusUpdate();
    Iterator<io.polarisdev.wrsflightservice.alert.notification.FlightStatusUpdate> flightStatusUpdateIterator =
        flightStatusUpdateList.iterator();
    while (flightStatusUpdateIterator.hasNext()) {
      io.polarisdev.wrsflightservice.alert.notification.FlightStatusUpdate flightStatusUpdate =
          flightStatusUpdateIterator.next();
      UpdatedTextFields updatedTextFields = flightStatusUpdate.getUpdatedTextFields();
      UpdatedDateFields updatedDateFields = flightStatusUpdate.getUpdatedDateFields();
      if (Optional.ofNullable(updatedTextFields).isPresent())
        prepareUpdateTextField(flightStatusUpdateBuilder, updatedTextFields);
      if (Optional.ofNullable(updatedDateFields).isPresent())
        prepareUpdateDateFields(flightStatusUpdateBuilder, updatedDateFields);
    }
  }

  /**
   * preparing updated text field values
   * 
   * @param flightStatusUpdateBuilder
   * @param updatedTextFields
   */
  private void prepareUpdateTextField(FlightStatusUpdate.Builder flightStatusUpdateBuilder,
      UpdatedTextFields updatedTextFields) {
    List<UpdatedTextField> updatedTextFieldList = updatedTextFields.getUpdatedTextField();
    Iterator<UpdatedTextField> updatedTextFieldIterator = updatedTextFieldList.iterator();
    while (updatedTextFieldIterator.hasNext()) {
      UpdatedTextField updatedTextField = updatedTextFieldIterator.next();
      io.polarisdev.flight.proto.data.FlightData.UpdatedTextField.Builder updatedTextFieldBuilder =
          io.polarisdev.flight.proto.data.FlightData.UpdatedTextField.newBuilder()
              .setUpdatedAlertText(updatedTextField.getField())
              .setNewText(updatedTextField.getNewText());
      if (StringUtils.isNotBlank(updatedTextField.getOriginalText()))
        updatedTextFieldBuilder.setOriginalText(updatedTextField.getOriginalText());
      flightStatusUpdateBuilder.addUpdatedTextFields(updatedTextFieldBuilder.build());
    }
  }

  /**
   * preparing update date fields
   * 
   * @param flightStatusUpdateBuilder
   * @param updatedTextFields
   */
  private void prepareUpdateDateFields(FlightStatusUpdate.Builder flightStatusUpdateBuilder,
      UpdatedDateFields updatedDateFields) {
    List<UpdatedDateField> updatedDateFieldList = updatedDateFields.getUpdatedDateField();
    Iterator<UpdatedDateField> updatedDateFieldIterator = updatedDateFieldList.iterator();
    while (updatedDateFieldIterator.hasNext()) {
      UpdatedDateField updatedDateField = updatedDateFieldIterator.next();
      io.polarisdev.flight.proto.data.FlightData.UpdatedDateField.Builder updatedDateFieldBuilder =
          io.polarisdev.flight.proto.data.FlightData.UpdatedDateField.newBuilder()
              .setUpdatedAlertDateField(updatedDateField.getField());
      if (StringUtils.isNotBlank(updatedDateField.getOriginalDateUtc()))
        updatedDateFieldBuilder
            .setOriginalDate(convertDateToTimeStamp(updatedDateField.getOriginalDateUtc()));
      if (StringUtils.isNotBlank(updatedDateField.getNewDateUtc()))
        updatedDateFieldBuilder
            .setUpdatedDate(convertDateToTimeStamp(updatedDateField.getNewDateUtc()));
      flightStatusUpdateBuilder.addUpdatedDateFields(updatedDateFieldBuilder.build());
    }
  }

  /**
   * convert string date into timestamp
   * 
   * @param date
   * @return Timestamp
   */
  private Timestamp convertDateToTimeStamp(String date) {
    Timestamp.Builder timeStampBuilder = Timestamp.newBuilder();
    try {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");
      return timeStampBuilder.setSeconds(dateFormat.parse(date).getTime()).build();
    } catch (ParseException ex) {
      log.error("Date Parse Exception {}", ex.getMessage());
    }
    return timeStampBuilder.build();
  }

}
