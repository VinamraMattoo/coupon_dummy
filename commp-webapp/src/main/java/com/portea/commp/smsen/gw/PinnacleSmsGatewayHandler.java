package com.portea.commp.smsen.gw;

import com.portea.common.config.domain.ConfigGatewayParam;
import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.util.ConnectionHolder;
import com.portea.commp.smsen.util.ConnectionUtil;
import com.portea.commp.smsen.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

public class PinnacleSmsGatewayHandler implements SmsGatewayHandler {

    public static final String SMS_GATEWAY_NAME = "PINNACLE";

    private static final String SENDER_BRAND_DEFAULT = "SENDER_BRAND_DEFAULT";
    private static final String SENDER_BRAND_PREFIX = "SENDER_BRAND_";
    private static final String ERROR_PATTERN = "ES";
    private static final Character SPACE = ' ';
    private static final Character HYPHEN = '-';
    private final String userName;
    private final String password;
    protected final String sendSmsUrl;
    protected final String pollUrl;
    private final Map<String, String> config;

    private final Logger LOG = LoggerFactory.getLogger(PinnacleSmsGatewayHandler.class);


    public PinnacleSmsGatewayHandler(Map<String,String> config){
        this.userName  = config.get(ConfigGatewayParam.USERNAME.name());
        this.password   = config.get(ConfigGatewayParam.USER_CREDENTIAL_PWD.name());
        this.sendSmsUrl = config.get(ConfigGatewayParam.ENDPOINT_URL_SUBMISSION.name());
        this.pollUrl    = config.get(ConfigGatewayParam.ENDPOINT_URL_POLLING.name());
        this.config = config;
    }

    @Override
    public ConnectionHolder getSubmissionConnectionHolder(SmsAssembly smsAssembly) {
        try{

            Integer brandId = smsAssembly.getBrand().getId();
            String senderId = config.get(SENDER_BRAND_PREFIX+brandId);

            if(senderId == null) {
                senderId = config.get(SENDER_BRAND_DEFAULT);
            }
            String url = MessageFormat.format("{0}?username={1}&pass={2}&senderid={3}&dest_mobileno={4}"+
                            "&message={5}&response=Y",sendSmsUrl, userName, password, senderId,
                    smsAssembly.getMobileNumber(), ConnectionUtil.encode(encode(smsAssembly.getMessage())));

            return ConnectionUtil.getConnectionReader(url);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String encode(String message) {

        message = message.replaceAll("[&]", "amp;");
        message = message.replaceAll("[#]", ";hash");
        message = message.replaceAll("[+]", "plus;");
        message = message.replaceAll("[,]", "comma;");
        return message;
    }

    @Override
    public ConnectionHolder getStatusCheckConnectionHolder(String correlationId) {
        String smsStatusURL = pollUrl+"?Scheduleid="+correlationId;
        try {
            return ConnectionUtil.getConnectionReader(smsStatusURL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GatewaySmsStatusCheckResponse readStatusResponse(ConnectionHolder holder) throws IOException {

        GatewaySmsStatusCheckResponse response = SmsGatewayHandler.super.readStatusResponse(holder);

        if (response != null) {
            response.setStatus(PinnacleSmsGatewayStatus.HTTP_NOT_OK);
            return response;
        }

        response = new GatewaySmsStatusCheckResponse();
        String statusResponse = StringUtil.read(holder.getReader());

        GatewaySmsStatus gatewaySmsStatus = findGatewayStatus(statusResponse);
        if ( gatewaySmsStatus != null) {
            response.setStatus(gatewaySmsStatus);
            response.setResponseMessage(statusResponse);
        }
        else {
            response.setError(true);
            response.setResponseMessage(statusResponse);
        }

        return response;
    }

    protected GatewaySmsStatus findGatewayStatus(String statusResponse) {
        for (GatewaySmsStatus gatewaySmsStatus : PinnacleSmsGatewayStatus.values()) {
            if (statusResponse.contains(gatewaySmsStatus.getName())) {
                return gatewaySmsStatus;
            }
        }
        return null;
    }

    @Override
    public String getSmsGatewayName() {
        return SMS_GATEWAY_NAME;
    }

    @Override
    public GatewaySmsSubmissionResponse readSubmissionResponse(ConnectionHolder holder) throws IOException{
        GatewaySmsSubmissionResponse gatewaySmsSubmissionResponse = SmsGatewayHandler.super.readSubmissionResponse(holder);

        if(gatewaySmsSubmissionResponse != null) {
            LOG.debug("Exception in HttpUrlConnection response code: "+gatewaySmsSubmissionResponse.getResponseCode());
            return gatewaySmsSubmissionResponse;
        }

        gatewaySmsSubmissionResponse = new GatewaySmsSubmissionResponse();
        String response = StringUtil.read(holder.getReader());

        LOG.debug("Gateway response on submission: "+response);

        if(isSubmissionError(response)) {

            setError(gatewaySmsSubmissionResponse, response);
        }
        else {
            gatewaySmsSubmissionResponse.setResponseMessage(response);
            gatewaySmsSubmissionResponse.setCorrelationId(response);
        }
        return gatewaySmsSubmissionResponse;
    }

    /**
     * Sets Error code and response by using the the following information.
     *
     * info: All the error messages don't contain an error code,
     * the ones that contain begin with {@link #ERROR_PATTERN}
     *
      */
    private void setError(GatewaySmsSubmissionResponse gatewaySmsSubmissionResponse, String response) {
        LOG.debug(" Error in gateway response ");
        gatewaySmsSubmissionResponse.setError(true);

        gatewaySmsSubmissionResponse.setFailureType(GssFailureType.FAILURE_SYNTAX);
        String initialPattern = response.substring(0, 2);

        LOG.debug("Response error code pattern: "+initialPattern);
        if(initialPattern.equals(ERROR_PATTERN)) {
            Integer endIndex = response.indexOf(SPACE);

            String errorCode = response.substring(0, endIndex);
            gatewaySmsSubmissionResponse.setResponseCode(errorCode);

            String errorMsg = response.substring(endIndex);
            gatewaySmsSubmissionResponse.setResponseMessage(errorMsg);
            LOG.debug("code:"+errorCode);
        }
        else {
            gatewaySmsSubmissionResponse.setResponseMessage(response);
        }
    }

    /**
     * Determines if there is an error in the response by using the following information.
     *
     * info: A successful response will have correlation id, which begins with an integer value all other responses
     * begin with a string.
     * successful response ex: 5068570-2008_12_29
     */
    private boolean isSubmissionError(String response) {
        int endIndex = response.indexOf(HYPHEN);
        if(endIndex == -1) {
            return true;
        }
        String beginCorrelationId = response.substring(0, endIndex);

        return ( ! StringUtil.isNumber(beginCorrelationId));
    }

    @Override
    public void resetSmsSubmissionCount() {

    }

    @Override
    public Integer getSmsSubmissionCount() {
        return null;
    }

}