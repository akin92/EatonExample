package com.eaton.example.rest_controller;

import com.eaton.example.exceptions.EatonException;
import com.eaton.example.enums.ErrorCode;
import com.eaton.example.dtos.DtoMessage;
import com.eaton.example.model.Device;
import com.eaton.example.model.DeviceMessage;
import com.eaton.example.model.ResponseError;
import com.eaton.example.service.DeviceMessageService;
import com.eaton.example.service.DeviceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageRestController {
    private static final Logger logger = LogManager.getLogger(MessageRestController.class);

    private DeviceMessageService deviceMessageService;

    private DeviceService deviceService;

    @Autowired
    public MessageRestController(DeviceMessageService deviceMessageService, DeviceService deviceService) {
        this.deviceMessageService = deviceMessageService;
        this.deviceService = deviceService;
    }


    @PostMapping("/message/{deviceId}")
    @ResponseBody
    public ResponseEntity<Device> crateDeviceMessage(@RequestBody DtoMessage dtoMessage, @PathVariable Integer deviceId) {
        Device existDevice = null;
        Device updatedDevice = null;
        List<DeviceMessage> deviceMessageList = null;
        try {
            logger.debug("Message receive process starting !!");
            existDevice = validateDeviceCheck(deviceId);
            prepareDeviceWithMessages(dtoMessage, existDevice);

            deviceService.updateDevice(existDevice);
            updatedDevice = deviceService.getDeviceById(deviceId);
            logger.info("Message was received properly!!");
            return new ResponseEntity<>(updatedDevice, HttpStatus.CREATED);
        } catch (EatonException ea) {
            return new ResponseEntity(new ResponseError(ea.getErrorCode().getCode(), ea.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error("Device message creation process was failed");
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/message/count/{deviceId}")
    @ResponseBody
    public ResponseEntity<Integer> loginDevice(@PathVariable Integer deviceId) {
        try {
            Device existDevice = deviceService.getDeviceById(deviceId);
            Integer count = deviceMessageService.getMessageCountByDevice(deviceId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (EatonException ea) {
            logger.warn("Device was not found!!");
            return new ResponseEntity(new ResponseError(ea.getErrorCode().getCode(), ea.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private Device validateDeviceCheck(Integer deviceId) throws EatonException {
        Device existDevice;
        existDevice = deviceService.getDeviceById(deviceId);
        //Device Active Check
        if (!existDevice.getActive()) {
            throw new EatonException(ErrorCode.DEVICE_NOT_ACTIVE);
        }
        return existDevice;
    }

    private void prepareDeviceWithMessages(DtoMessage dtoMessage, Device existDevice) {
        List<DeviceMessage> deviceMessageList;
        if (existDevice.getDeviceMessageList().isEmpty() || existDevice.getDeviceMessageList() == null) {
            deviceMessageList = new ArrayList<>();
        }
        deviceMessageList = existDevice.getDeviceMessageList();
        DeviceMessage deviceMessage = new DeviceMessage(dtoMessage.getMessageTitle(), dtoMessage.getMessageBody());
        deviceMessageList.add(deviceMessage);
        existDevice.setDeviceMessageList(deviceMessageList);
    }
}
