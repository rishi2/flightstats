
package io.polarisdev.wrsflightservice.alert.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatedTextField {

  private String field;
  private String originalText;
  private String newText;

}
