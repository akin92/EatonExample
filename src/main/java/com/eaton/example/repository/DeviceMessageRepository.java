package com.eaton.example.repository;

import com.eaton.example.model.DeviceMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DeviceMessageRepository extends JpaRepository<DeviceMessage, Integer> {
    @Query(value = "select count(*) from message  where device_id = :deviceId",nativeQuery = true)
    Integer getCountMessageByDeviceId(@Param("deviceId") Integer deviceId);
}
