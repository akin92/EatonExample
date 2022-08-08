package com.eaton.example.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message")
public class DeviceMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String messageTitle;

    @Column
    private String messageBody;

    public DeviceMessage(Integer id, String messageTitle, String messageBody) {
        this.id = id;
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
    }
    public DeviceMessage(String messageTitle, String messageBody) {
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
    }

    public DeviceMessage() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "DeviceMessage{" +
                "id=" + id +
                ", messageTitle='" + messageTitle + '\'' +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }
}
