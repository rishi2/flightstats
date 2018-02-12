package io.polarisdev.wsclientframework.commons.exception;

public class WSExceptionConstants {

  private WSExceptionConstants() {

  }

  public static final String NO_SERVICE_NAME = "1001";
  public static final String NO_SERVICE_NAME_MSG =
      "There are no service name in the svcContainer SERVICE_NAME field.";

  public static final String NO_SERVICE_MAP = "1002";
  public static final java.lang.String NO_SERVICE_MAP_MSG =
      "There is no service map assigned to registry";

  public static final String NO_TRANSFORMER_MAP = "1003";
  public static final java.lang.String NO_TRANSFORMER_MAP_MSG =
      "There is no transformer map assigned to registry";


  public static final String NO_CONTENT_TYPE = "1004";
  public static final java.lang.String NO_CONTENT_TYPE_MSG =
      "Entity content sent but content type defined";

  public static final String GENERAL_ERROR = "1005";
  public static final java.lang.String GENERAL_ERROR_MSG =
      "General Web Service Client Framework Error";

  public static final String NO_CONFIGURATION_PROVIDER = "1006";
  public static final java.lang.String NO_CONFIGURATION_PROVIDED_MSG = "No Configuration injected";

}
