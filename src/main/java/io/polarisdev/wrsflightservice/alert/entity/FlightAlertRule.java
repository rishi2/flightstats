package io.polarisdev.wrsflightservice.alert.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FlightAlertRule {

  @Id
  private String ruleId;

  @NotNull
  private String userId;

}
