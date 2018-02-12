package io.polarisdev.wrsflightservice.controller;


import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.polarisdev.flight.proto.service.servicetypes.FlightProto.ProcessFlightAlertNotificationByDepartureResponse;
import io.polarisdev.wrsflightservice.alert.entity.FlightAlertRule;
import io.polarisdev.wrsflightservice.alert.notification.AlertNotificationData;
import io.polarisdev.wrsflightservice.alert.repository.FlightAlertRepository;
import io.polarisdev.wrsflightservice.grpc.client.FlightAlertNotificationClient;
import io.polarisdev.wsclientframework.commons.container.ISvcContainer;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin
@RestController
@RequestMapping("/alert")
@Slf4j
public class FlightAlertNotifcationController {

  private FlightAlertNotificationClient flightAlertNotificationClient;
  private FlightAlertRepository flightAlertRepository;

  @Autowired
  public FlightAlertNotifcationController(
      FlightAlertNotificationClient flightAlertNotificationClient,
      FlightAlertRepository flightAlertRepository) {
    this.flightAlertNotificationClient = flightAlertNotificationClient;
    this.flightAlertRepository = flightAlertRepository;
  }

  /**
   * getting alert notifcation data from flight stats api
   * 
   * @param alertNotificationData
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "/notification", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String getFlightAlertNotificationData(@RequestBody String alertNotificationData) {
    try {
      AlertNotificationData parseRawAlertNotificationData =
          parseRawAlertNotificationData(alertNotificationData);
      if (null == parseRawAlertNotificationData || null == parseRawAlertNotificationData.getAlert()
          || null == parseRawAlertNotificationData.getAlert().getRule()) {
        log.debug("alert notifcation data not proper");
        return ISvcContainer.ERROR;
      }
      String ruleId = parseRawAlertNotificationData.getAlert().getRule().getId();
      FlightAlertRule flightAlertRule = flightAlertRepository.findOne(ruleId);
      if (null != flightAlertRule && null != flightAlertRule.getUserId()) {
        ProcessFlightAlertNotificationByDepartureResponse processFlightAlertNotificationData =
            flightAlertNotificationClient.processFlightAlertNotificationData(
                parseRawAlertNotificationData, flightAlertRule.getUserId());
        if (Optional.ofNullable(processFlightAlertNotificationData).isPresent())
          return ISvcContainer.SUCCESS;
        else {
          log.error("getFlightAlertNotificationData:{}", parseRawAlertNotificationData);
          return ISvcContainer.ERROR;
        }
      } else {
        log.error("user is not found for ruleId coming in alert notification");
        return ISvcContainer.ERROR;
      }
    } catch (final Exception e) {
      log.error("error in getFlightAlertNotificationData:{}", e);
      return ISvcContainer.ERROR;
    }
  }

  AlertNotificationData parseRawAlertNotificationData(String alertNotificationData)
      throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper.readValue(alertNotificationData, AlertNotificationData.class);
  }

}
