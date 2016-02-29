package com.portea.commp.smsen.vo;

import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.domain.SmsGroupGatewayMapping;
import com.portea.commp.smsen.util.ConnectionHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This class captures information about an SMS, the handling for which is under progress.
 */
public class SmsInAssembly implements Comparable<SmsInAssembly> {

    private SmsAssembly smsAssembly;


    private ConnectionHolder statusConnectionHolder;
    private ConnectionHolder submissionConnectionHolder;
    private Integer statusCheckTrialCount = 0;
    private Integer gatewayFindingFailureCount = 0;
    private Integer correlationIdCheckTrialCount = 0;
    private String gatewayStatus;
    /**
     * Denotes the last time when the gateway was queried for either submitting a sms or
     * checking for sms status.
     */
    private Date lastGatewayQueriedOn;
    private boolean statusValidationDone;
    private boolean submissionValidationDone;
    private List<SmsGroupGatewayMapping> gatewayMappings;
    private int currentGatewayMappingIndex;

    public SmsInAssembly() {
        /*
          A small number '3' is used as it is not expected that on an average more than 3
          gateways will be used in the life of an SMS
         */
        gatewayMappings = new ArrayList<>(3);
        this.currentGatewayMappingIndex = 0;
    }

    /**
     * Any server component that updates SmsAssembly should also update the reference in this
     * object. This is to ensure that the SmsAssembly data is always up-to-date and can be used
     * by a server component
     * @param smsAssembly the reference of the updated SmsAssembly object which is in sync with the
     *                    database
     */
    public void updateSmsAssembly(SmsAssembly smsAssembly) {
        this.smsAssembly = smsAssembly;
    }

    public SmsAssembly getSmsAssembly() {
        return smsAssembly;
    }


    @Override
    public int compareTo(SmsInAssembly smsInAssembly) {

        int compare = this.smsAssembly.getSmsGroup().getPriority() - smsInAssembly.smsAssembly.getSmsGroup().getPriority();

        if(compare != 0){
            return compare;
        }else{
            Date thisScheduleDate = smsAssembly.getScheduledTime();
            Date otherScheduleDate = smsInAssembly.getSmsAssembly().getScheduledTime();
            if(thisScheduleDate.compareTo(otherScheduleDate) == 0){
                Date thisExpiry = smsAssembly.getSendBefore();
                Date otherExpiry = smsInAssembly.getSmsAssembly().getSendBefore();

                return (thisExpiry.compareTo(otherExpiry));
            }else {
                return (thisScheduleDate.compareTo(otherScheduleDate));
            }
        }
    }

    /**
     * Adds group gateway mapping to the list
     * @param smsGroupGatewayMapping the sms-group gateway mapping to be used
     */
    public void addGatewayMapping(SmsGroupGatewayMapping smsGroupGatewayMapping) {
        gatewayMappings.add(smsGroupGatewayMapping);
    }

    /**
     * Returns unmodifiable version of the current list of names of used gateways
     * @return list of gateway names
     */
    public List<SmsGroupGatewayMapping> getGatewayMappings() {
        return Collections.unmodifiableList(gatewayMappings);
    }

    /**
     * Returns sms submitted status
     */
    public boolean isSmsRequested() {
        Date smsSubmitted = smsAssembly.getSmsRequestedAt();
        return smsSubmitted != null;
    }

    public BufferedReader getSubmissionConnectionReader() {
        return submissionConnectionHolder.getReader();
    }

    public BufferedReader getStatusConnectionReader() {
        return statusConnectionHolder.getReader();
    }

    public void closeStatusConnection() {
        BufferedReader statusConnectionReader = statusConnectionHolder.getReader();
        HttpURLConnection connection = statusConnectionHolder.getConnection();

        if(statusConnectionReader != null) {
            try {
                statusConnectionReader.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            finally {
                if(connection != null) {
                    connection.disconnect();
                }
            }
        }
    }

    public void closeSubmissionConnection() {
        BufferedReader submissionConnectionReader = submissionConnectionHolder.getReader();
        HttpURLConnection connection = submissionConnectionHolder.getConnection();
        if(submissionConnectionReader != null) {
            try {
                submissionConnectionReader.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            finally {
                if(connection != null) {
                    connection.disconnect();
                }
            }
        }
    }

    public void setStatusConnectionHolder(ConnectionHolder statusConnectionHolder) {
        this.statusConnectionHolder = statusConnectionHolder;
    }

    public ConnectionHolder getStatusConnectionHolder() {
        return statusConnectionHolder;
    }

    public void setSubmissionConnectionHolder(ConnectionHolder submissionConnectionHolder) {
        this.submissionConnectionHolder = submissionConnectionHolder;
    }

    public ConnectionHolder getSubmissionConnectionHolder() {
        return submissionConnectionHolder;
    }

    public Integer getStatusCheckTrialCount() {
        return statusCheckTrialCount;
    }

    public void setStatusCheckTrialCount(Integer statusCheckTrialCount) {
        this.statusCheckTrialCount = statusCheckTrialCount;
    }

    public void setGatewayFindingFailureCount(Integer gatewayFindingFailureCount) {
        this.gatewayFindingFailureCount = gatewayFindingFailureCount;
    }

    public Integer getGatewayFindingFailureCount() {
        return gatewayFindingFailureCount;
    }

    public void setCorrelationIdCheckTrialCount(Integer correlationIdCheckTrialCount) {
        this.correlationIdCheckTrialCount = correlationIdCheckTrialCount;
    }

    public Integer getCorrelationIdCheckTrialCount() {
        return correlationIdCheckTrialCount;
    }

    public void setGatewayStatus(String gatewayStatus) {
        this.gatewayStatus = gatewayStatus;
    }

    public String getGatewayStatus() {
        return gatewayStatus;
    }

    public void setLastGatewayQueriedOn(Date lastGatewayQueriedOn) {
        this.lastGatewayQueriedOn = lastGatewayQueriedOn;
    }

    public Date getLastGatewayQueriedOn() {
        return lastGatewayQueriedOn;
    }

    public boolean isStatusValidationDone() {
        return statusValidationDone;
    }

    public void setStatusValidationDone(boolean statusValidationDone) {
        this.statusValidationDone = statusValidationDone;
    }

    public boolean isSubmissionValidationDone() {
        return submissionValidationDone;
    }

    public void setSubmissionValidationDone(boolean submissionValidationDone) {
        this.submissionValidationDone = submissionValidationDone;
    }

    public int getCurrentGatewayMappingIndex() {
        return currentGatewayMappingIndex;
    }

    public void setCurrentGatewayMappingIndex(int currentGatewayMappingIndex) {
        this.currentGatewayMappingIndex = currentGatewayMappingIndex;
    }

    public SmsGroupGatewayMapping getGatewayMapping() {
        try {

            return gatewayMappings.get(currentGatewayMappingIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public SmsGroupGatewayMapping getNextGatewayMapping() {
        if (currentGatewayMappingIndex >= gatewayMappings.size()) {

            currentGatewayMappingIndex = 0;
        }
        else {
            currentGatewayMappingIndex++;
        }
        return getGatewayMapping();
    }
}