package com.eaton.example.rest_controller;

import com.eaton.example.exceptions.EatonException;
import com.eaton.example.dtos.DtoDevice;
import com.eaton.example.model.Device;
import com.eaton.example.model.ResponseError;
import com.eaton.example.service.DeviceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DeviceRestController {

    private static final Logger logger = LogManager.getLogger(DeviceRestController.class);

    private DeviceService deviceService;

    @Autowired
    public DeviceRestController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/device")
    @ResponseBody
    public ResponseEntity<Device> createDevice(@RequestBody DtoDevice dtoDevice) {
        try {
            logger.debug("Device create process starting !!");
            Device device = new Device();
            device.setActive(false);
            device.setName(dtoDevice.getDeviceName());
            Device savedDevice = deviceService.createDevice(device);
            logger.info("Device was created properly!!");
            return new ResponseEntity<>(savedDevice, HttpStatus.CREATED);
        } catch (EatonException ea) {
            logger.warn("Device already exist on db with same device name!!");
            return new ResponseEntity(new ResponseError(ea.getErrorCode().getCode(), ea.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error("Device creation process was failed");
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/device/{deviceId}")
    @ResponseBody
    public ResponseEntity<Device> getDeviceById(@PathVariable Integer deviceId) {
        Device existDevice = null;
        try {
            existDevice = deviceService.getDeviceById(deviceId);
            return new ResponseEntity<>(existDevice,HttpStatus.OK);
        } catch (EatonException ea) {
            logger.warn("Device was not found!!");
            return new ResponseEntity(new ResponseError(ea.getErrorCode().getCode(), ea.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/device/login/{deviceId}")
    @ResponseBody
    public ResponseEntity loginDevice(@PathVariable Integer deviceId) {
        try {
            Device existDevice = deviceService.getDeviceById(deviceId);
            existDevice.setActive(true);
            deviceService.updateDevice(existDevice);
            logger.debug("Device "+deviceId+" loggedIn !!");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EatonException ea) {
            logger.warn("Device was not found!!");
            return new ResponseEntity(new ResponseError(ea.getErrorCode().getCode(), ea.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
