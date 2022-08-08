package com.eaton.example.dtos;

import javax.persistence.Column;

public class DtoMessage {
    private String messageTitle;
    private String messageBody;

    public DtoMessage() {
    }

    public DtoMessage(String messageTitle, String messageBody) {
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
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
        return "DtoMessage{" +
                "messageTitle='" + messageTitle + '\'' +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }
}
