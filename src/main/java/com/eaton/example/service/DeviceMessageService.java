package com.eaton.example.service;

import com.eaton.example.dao.DeviceMessageDao;
import com.eaton.example.model.DeviceMessage;
import com.eaton.example.repository.DeviceMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeviceMessageService implements DeviceMessageDao {
    private DeviceMessageRepository deviceMessageRepository;

    @Autowired
    public DeviceMessageService(DeviceMessageRepository deviceMessageRepository) {
        this.deviceMessageRepository = deviceMessageRepository;
    }

    @Override
    public DeviceMessage getMessageById(Integer id) {
        return deviceMessageRepository.findById(id).get();
    }

    @Override
    public DeviceMessage saveMessage(DeviceMessage message) {
        return deviceMessageRepository.save(message);
    }

    @Override
    public Integer getMessageCountByDevice(Integer id) {
        return deviceMessageRepository.getCountMessageByDeviceId(id);
    }
}
