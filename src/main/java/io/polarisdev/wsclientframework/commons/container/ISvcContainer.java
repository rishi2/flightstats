package io.polarisdev.wsclientframework.commons.container;

import java.util.Map;

public interface ISvcContainer {

  public static final String INPUT_KEY = "inputObject";
  public static final String OUTPUT_KEY = "outputObject";
  public static final String ERROR_KEY = "errorObject";
  public static final String ORCHESTRATION_ID = "endPoint";
  public static final String CRITICAL_KEY = "critical";
  public static final String CAN_CONTINUE = "canContinue";
  public static final String INPUT_TYPE = "inputType";
  public static final String QUERY_STRING = "queryString";
  public static final String RESPONSE_CODE = "ResponseCode";
  public static final String TIMEOUT_MS = "timeOutMs";
  public static final String PARAMETER_MAP = "parameterMap";
  public static final String REQ_CONTENT = "reqContent";
  public static final String IS_TIMEOUT = "public static final isTimeOut";
  public static final String SERVICE_NAME = "serviceName";
  public static final String NOTIFICATION_EMAIL = "notificationEmail";
  public static final String NOTIFICATION_SENDER = "notificationSender";
  public static final int DEFAULT_TIMEOUT = 10000;
  public static final String RETRY_PARAM = "retry";
  public static final String REQ_CONTENT_TYPE = "requestContentType";
  public static final String SERVICE_URL = "serviceURL";
  public static final String HTTP_METHOD = "httpMethod";
  public static final String HEADER_MAP = "headerMap";
  public static final String APP_ID = "&appId=";
  public static final String APP_KEY = "&appKey=";
  public static final String PARTY_KEY = "partyKey";
  public static final String PARTY_ID = "partyId";
  public static final String TYPE_JSON = "json";
  public static final String DELIVER_TO = "&deliverTo=";
  public static final String TYPE = "?type=";
  public static final String FROM_AIRPORT ="from";
  public static final String DEPARTING = "departing";
  public static final String CARRIER = "carrier";
  public static final String FLIGHT_NUMBER = "flightNumber";
  public static final String DEPARTING_AIRPORT = "departureAirport";
  public static final String YEAR = "year";
  public static final String MONTH = "month";
  public static final String DAY = "day";
  public static final String ERROR = "error";
  public static final String SUCCESS = "success";
  public static final String RULE_NAME = "ruleName";
  public static final String NAME = "&name=";
  public static final String REQUEST_TYPE = "requestType";
  public static final String CREATE_ALERT_RULE = "createAlertRule";
  public static final String FLIGHT_STATUS = "flightStatus";
  public static final String POST_SERVICE_DELIVER_TO_URL = "http://localhost:8080/alert/notification";
  public static final String UTC = "utc";
  public static final String AIRPORT = "airport";
  public static final String CODE_TYPE = "codeType";
  public static final String UTC_URL = "?utc=";
  public static final String AIRPORT_URL = "&airport=";
  public static final String CODE_TYPE_URL = "&codeType=";
  public static final String UTC_TIME = "UTC";

  public Map<String, Object> getSvcMap();

  public void setSvcMap(Map<String, Object> svcMap);

  public void setSvcField(String sKey, Object oValue);

  public Object getSvcField(String sKey);

}
