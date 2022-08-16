package com.eaton.example.dao;

import com.eaton.example.exceptions.EatonException;
import com.eaton.example.model.Device;

public interface DeviceDao {
    Device createDevice(Device device) throws EatonException;

    Device updateDevice(Device device);

    Device getDeviceById(Integer id) throws EatonException;

    Device getDeviceByName(String deviceName);

}
