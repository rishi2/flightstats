package io.polarisdev.wrsflightservice.alert.repository;

import org.springframework.data.repository.CrudRepository;
import io.polarisdev.wrsflightservice.alert.entity.FlightAlertRule;

public interface FlightAlertRepository extends CrudRepository<FlightAlertRule, String> {

}

