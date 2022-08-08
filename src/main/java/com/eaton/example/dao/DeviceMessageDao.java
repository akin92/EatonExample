package com.eaton.example.dao;

import com.eaton.example.model.Device;
import com.eaton.example.model.DeviceMessage;

public interface DeviceMessageDao {
    DeviceMessage getMessageById(Integer id);
    DeviceMessage saveMessage(DeviceMessage message);

    Integer getMessageCountByDevice(Integer id);
}
