package com.eaton.example.dtos;

public class DtoDevice {

    private String deviceName;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public DtoDevice(String deviceName) {
        this.deviceName = deviceName;
    }

    public DtoDevice() {
    }
}
