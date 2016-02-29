package com.portea.cpnen.web.rapi.domain;

import com.portea.cpnen.domain.EventType;

import java.util.Date;
import java.util.List;

public class CouponCodeCreationRequest {
    private String code;
    private String channelName;
    private List<Reservation> reservations;

    public CouponCodeCreationRequest(){}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public static class Reservation {

        private Integer userId;
        private String remarks;
        private Date reservationFrom;
        private Date reservationTill;
        private EventType reservationType;

        public Reservation() {}

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Date getReservationFrom() {
            return reservationFrom;
        }

        public void setReservationFrom(Date reservationFrom) {
            this.reservationFrom = reservationFrom;
        }

        public Date getReservationTill() {
            return reservationTill;
        }

        public void setReservationTill(Date reservationTill){
            this.reservationTill = reservationTill;
        }

        public EventType getReservationType() {
            return reservationType;
        }

        public void setReservationType(EventType reservationType) {
            this.reservationType = reservationType;
        }
    }
}
