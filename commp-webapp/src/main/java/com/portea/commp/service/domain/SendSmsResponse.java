package com.portea.commp.service.domain;

import java.util.List;

public class SendSmsResponse {

    private String lotId;
    private List<MessageBatch> batches;

    public SendSmsResponse() {
    }

    public List<MessageBatch> getBatches() {
        return batches;
    }

    public void setBatches(List<MessageBatch> batches) {
        this.batches = batches;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public static class MessageBatch {
        private String batchId;
        private List<String> trackingIds;

        public MessageBatch() {
        }

        public String getBatchId() {
            return batchId;
        }

        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        public List<String> getTrackingIds() {
            return trackingIds;
        }

        public void setTrackingIds(List<String> trackingIds) {
            this.trackingIds = trackingIds;
        }
    }
}
