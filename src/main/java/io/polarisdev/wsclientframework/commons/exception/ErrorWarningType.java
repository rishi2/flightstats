

package io.polarisdev.wsclientframework.commons.exception;

import lombok.Data;

@Data
public class ErrorWarningType implements java.io.Serializable {

  private static final long serialVersionUID = -2781760500683366605L;
  private java.lang.String type;
  private java.lang.String code;
  private java.lang.String shortText;

}
