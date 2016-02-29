package com.portea.commp.smsen.gw;

import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.util.ConnectionHolder;
import com.portea.commp.smsen.util.StringUtil;

import java.io.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A mock handler that can be used for testing the SMS engine flow.
 */
public class MockSmsGatewayHandler implements SmsGatewayHandler {

    public final static String GATEWAY_NAME = "DUMMY_GATEWAY_1";
    private static final Integer FAILURE_COUNT = 8;
    private static final Integer DELIVERY_COUNT = 3;
    private static final String SUBMISSION_RESPONSE_CODE = "SUB-MOCK";
    private static final String SUBMISSION_RESPONSE_MESSAGE = "SUB-MOCK-SUCCESS";
    private static final String STATUS_RESPONSE_CODE = "STATUS_MOCK";
    private static final String STATUS_RESPONSE_IN_PROGRESS = "STATUS-MOCK-IN-PROGRESS";
    private static final String STATUS_RESPONSE_FAILURE = "STATUS-MOCK-FAILURE";
    private static final String STATUS_RESPONSE_DELIVERED = "STATUS-MOCK-DELIVERED";

    private static AtomicInteger submissionCount = new AtomicInteger(0);
    private static AtomicInteger statusCheckCount = new AtomicInteger(0);

    public MockSmsGatewayHandler(Map<String,String> config){
    }

    @Override
    public ConnectionHolder getSubmissionConnectionHolder(SmsAssembly smsAssembly) {

        Integer currentCount = submissionCount.incrementAndGet();

        String str = "mock_count_"+currentCount;

        // convert String into InputStream
        InputStream is = new ByteArrayInputStream(str.getBytes());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        return new ConnectionHolder(null, bufferedReader);
    }

    @Override
    public ConnectionHolder getStatusCheckConnectionHolder(String correlationId) {

        // convert String into InputStream
        InputStream is = new ByteArrayInputStream(correlationId.getBytes());

        return new ConnectionHolder(null, new BufferedReader(new InputStreamReader(is)));
    }

    @Override
    public GatewaySmsStatusCheckResponse readStatusResponse(ConnectionHolder statusResponse) {
        statusCheckCount.incrementAndGet();

        GatewaySmsStatusCheckResponse gatewaySmsStatusCheckResponse = new GatewaySmsStatusCheckResponse();
        gatewaySmsStatusCheckResponse.setResponseCode(STATUS_RESPONSE_CODE);

        gatewaySmsStatusCheckResponse.setResponseMessage(STATUS_RESPONSE_DELIVERED);
        gatewaySmsStatusCheckResponse.setStatus(MockSmsGatewayStatus.MOCK_DELIVERED);
        return gatewaySmsStatusCheckResponse;
    }

    @Override
    public String getSmsGatewayName() {
        return GATEWAY_NAME;
    }

    @Override
    public GatewaySmsSubmissionResponse readSubmissionResponse(ConnectionHolder holder) throws IOException{
        GatewaySmsSubmissionResponse gatewaySmsSubmissionResponse = new GatewaySmsSubmissionResponse();
        gatewaySmsSubmissionResponse.setCorrelationId(StringUtil.read(holder.getReader()));
        gatewaySmsSubmissionResponse.setResponseCode(SUBMISSION_RESPONSE_CODE);
        gatewaySmsSubmissionResponse.setResponseMessage(SUBMISSION_RESPONSE_MESSAGE);
        return gatewaySmsSubmissionResponse;
    }

    @Override
    public void resetSmsSubmissionCount() {
        submissionCount.set(0);
    }

    @Override
    public Integer getSmsSubmissionCount() {
        return submissionCount.intValue();
    }
}