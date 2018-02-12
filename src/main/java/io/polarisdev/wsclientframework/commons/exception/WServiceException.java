package io.polarisdev.wsclientframework.commons.exception;



public class WServiceException extends Exception {
  /**
   * 
   */
  private static final long serialVersionUID = -8161471348536385633L;
  private String message;
  private String code;
  private Object request;
  private Exception rootCause;

  public WServiceException(String code, String message, Exception rootCause, Object request) {
    super();
    this.code = code;
    this.message = message;
    this.rootCause = rootCause;
    this.request = request;
  }

  public WServiceException(String code, String message, Exception rootCause) {
    super();
    this.code = code;
    this.message = message;
    this.rootCause = rootCause;
    this.request = null;
  }

  public WServiceException(String code, String message) {
    super();
    this.code = code;
    this.message = message;
    this.rootCause = null;
    this.request = null;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getRequest() {
    return request;
  }

  public void setRequest(Object request) {
    this.request = request;
  }

  public Exception getRootCause() {
    return rootCause;
  }

  public void setRootCause(Exception rootCause) {
    this.rootCause = rootCause;
  }
}
