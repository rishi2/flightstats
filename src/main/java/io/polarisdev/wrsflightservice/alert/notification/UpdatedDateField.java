
package io.polarisdev.wrsflightservice.alert.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatedDateField {

  private String field;
  private String newDateLocal;
  private String newDateUtc;
  private String originalDateLocal;
  private String originalDateUtc;

}
