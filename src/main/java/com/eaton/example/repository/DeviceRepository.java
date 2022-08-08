package com.eaton.example.repository;

import com.eaton.example.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Device findDeviceByName(String deviceName);
}
