package com.eaton.example.service;

import com.eaton.example.EatonException;
import com.eaton.example.ErrorCode;
import com.eaton.example.dao.DeviceDao;
import com.eaton.example.model.Device;
import com.eaton.example.repository.DeviceRepository;
import com.eaton.example.rest_controller.DeviceRestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DeviceService implements DeviceDao {

    private static final Logger logger = LogManager.getLogger(DeviceService.class);
    private DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device createDevice(Device device) throws EatonException {
            Device existDevice  = deviceRepository.findDeviceByName(device.getName());
            if(existDevice!=null){
                throw new EatonException(ErrorCode.DEVICE_ALREADY_EXIST);
            }
        return deviceRepository.save(device);
    }

    @Override
    public Device updateDevice(Device device) {
        return deviceRepository.saveAndFlush(device);
    }

    @Override
    public Device getDeviceById(Integer id) throws EatonException {
        Optional<Device> device = deviceRepository.findById(id);
        if(device.isPresent()){
            return device.get();
        }else{
            throw  new EatonException(ErrorCode.DEVICE_NOT_FOUND);
        }
    }

    @Override
    public Device getDeviceByName(String deviceName)  {
        return deviceRepository.findDeviceByName(deviceName);
    }

}
