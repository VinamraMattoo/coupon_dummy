package com.portea.commp.service.domain;

import com.portea.commp.service.exception.IncompleteRequestException;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SendSmsRequest {

    private String senderName;
    private String key;
    private List<MessageBatchVo> lot;

    public SendSmsRequest() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<MessageBatchVo> getLot() {
        return lot;
    }

    public void setLot(List<MessageBatchVo> lot) {
        this.lot = lot;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Override
    public String toString() {
        return "SendSmsRequest{" +
                "key='" + key + '\'' +
                ", senderName='" + senderName + '\'' +
                ", lot=" + lot +
                '}';
    }

    public void validate() {

        String nullParam = getNullParam();

        if (nullParam != null) {
            throw new IncompleteRequestException(nullParam);
        }

    }

    private String getNullParam() {
        if (senderName == null) {
            return "senderName";
        }
        else if (key == null) {
            return "key";
        }
        else if (lot == null) {
            return "lot";
        }

        int index = 0;
        for (MessageBatchVo messageBatchVo : lot) {
            String nullParam = messageBatchVo.getNullParam();
            if (nullParam != null) {
                return "lot[" + index + "]." + nullParam;
            }
            index++;
        }
        return null;
    }

    public Integer getSizeSmsToBeSent() {
        return getLot().stream()
                       .mapToInt(MessageBatchVo::getSizeSmsToBeSent)
                       .sum();
    }
}
