package com.eaton.example.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "device")
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    private List<DeviceMessage> deviceMessageList;

    public Device() {
    }

    public Device(String name, Boolean active, List<DeviceMessage> deviceMessageList) {
        this.name = name;
        this.active = active;
        this.deviceMessageList = deviceMessageList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<DeviceMessage> getDeviceMessageList() {
        return deviceMessageList;
    }

    public void setDeviceMessageList(List<DeviceMessage> deviceMessageList) {
        this.deviceMessageList = deviceMessageList;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", deviceMessageList=" + deviceMessageList +
                '}';
    }
}
