package com.portea.commp.service.domain;

import java.util.List;

public class MessageVo {

    private List<String> receivers;
    private String text;

    public MessageVo() {
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "MessageVo{" +
                "receivers=" + receivers +
                ", text='" + text + '\'' +
                '}';
    }

    public String getNullParam() {
        if (this.receivers == null) {
            return "receivers";
        }
        else if (text == null) {
            return "text";
        }
        int index = 0;
        for (String receiver : receivers) {
            if (receiver == null) {
                return "receivers["+index+"]";
            }
            index++;
        }
        return null;
    }
}
