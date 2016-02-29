package com.portea.commp.service.domain;

import java.util.Date;
import java.util.List;

public class MessageBatchVo {

    private List<MessageVo> messages;
    private String receiverType;
    private String smsType;
    private Date sendBefore;
    private String scheduledTimeZone;
    private Date scheduledTime;

    public MessageBatchVo() {
    }

    public List<MessageVo> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageVo> messages) {
        this.messages = messages;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public Date getSendBefore() {
        return sendBefore;
    }

    public void setSendBefore(Date sendBefore) {
        this.sendBefore = sendBefore;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public String toString() {
        return "MessageBatchVo{" +
                "messages=" + messages +
                ", receiverType='" + receiverType + '\'' +
                ", smsType='" + smsType + '\'' +
                ", sendBefore=" + sendBefore +
                ", scheduledTimeZone='" + scheduledTimeZone + '\'' +
                ", scheduledTime=" + scheduledTime +
                '}';
    }

    public String getScheduledTimeZone() {
        return scheduledTimeZone;
    }

    public void setScheduledTimeZone(String scheduledTimeZone) {
        this.scheduledTimeZone = scheduledTimeZone;
    }

    public String getNullParam() {
        if (this.messages == null) {
            return  "messages";
        }
        else if (this.receiverType == null) {
            return  "receiverType";
        }
        else if (this.smsType == null) {
            return  "smsType";
        }
        int index = 0;
        for (MessageVo message : messages) {
            String nullParam = message.getNullParam();

            if (nullParam != null) {
                return "messages[" + index + "]." + nullParam;
            }
            index++;
        }
        return null;
    }

    public Integer getSizeSmsToBeSent() {
        return getMessages().stream().mapToInt(messageVo -> messageVo.getReceivers().size()).sum();
    }
}
