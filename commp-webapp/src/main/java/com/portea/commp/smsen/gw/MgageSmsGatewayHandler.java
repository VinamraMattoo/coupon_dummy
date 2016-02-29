package com.portea.commp.smsen.gw;

import com.portea.common.config.domain.ConfigGatewayParam;
import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.util.ConnectionHolder;
import com.portea.commp.smsen.util.ConnectionUtil;
import com.portea.commp.smsen.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Map;

public class MgageSmsGatewayHandler implements SmsGatewayHandler {

    public static final String SMS_GATEWAY_NAME = "MGAGE";

    private static final String SENDER_BRAND_DEFAULT = "SENDER_BRAND_DEFAULT";
    private static final String SENDER_BRAND_PREFIX = "SENDER_BRAND_";
    private static final String XML_ELEMENT_DN_RESPONSE = "DNResponse";
    private static final String XML_ELEMENT_CARRIER_STATUS = "carrierstatus";
    private static final String EMPTY_STRING = " ";
    private static final String BEGIN_CODE_PATTERN = "code=";
    private static final String END_CODE_PATTERN = "&";
    private static final String BEGIN_CORRELATION_PATTERN = "ID=";
    private static final String END_CORRELATION_PATTERN = "~";
    private static final String BEGIN_ERROR_MSG = "info=";
    private static final String END_ERROR_MSG = "&";
    private static final String XML_ELEMENT_CODE = "code";
    private static final String XML_ELEMENT_DESC = "desc";
    private static final String XML_ELEMENT_ERROR_CODE = "a2werrcode";
    private static final String XML_ELEMENT_ERROR = "Error";
    private static final String SUCCESS_RESPONSE_CODE = "0";
    private static final String NOT_AVAILABLE = "N/A";
    private static final String INVALID_XML = " Custom message - Could not parse xml ";

    private final String sendSmsUrl;
    private final String accountId;
    private final String password;
    private final String pollUrl;
    private final Map<String, String> config;

    private final Logger LOG = LoggerFactory.getLogger(MgageSmsGatewayHandler.class);

    public MgageSmsGatewayHandler(Map<String,String> config){
        this.accountId  = config.get(ConfigGatewayParam.USERNAME.name());
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

            if(senderId == null){
                senderId = config.get(SENDER_BRAND_DEFAULT);
            }
            String url = MessageFormat.format("{0}?aid={1}&pin={2}&mnumber={3}&message={4}&signature={5}",
                    sendSmsUrl, accountId, password, smsAssembly.getMobileNumber(), ConnectionUtil.encode(smsAssembly.getMessage()), senderId);

            return  ConnectionUtil.getConnectionReader(url);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Response format is xml which is parsed to find response code and response message.
     * Response code can signify if the response contains an error or not while response message
     * contains the description for the code.
     */
    @Override
    public GatewaySmsStatusCheckResponse readStatusResponse(ConnectionHolder holder) throws IOException {

        GatewaySmsStatusCheckResponse response = SmsGatewayHandler.super.readStatusResponse(holder);

        if(response != null) {
            response.setStatus(MgageSmsGatewayStatus.HTTP_NOT_OK);
            return response;
        }

        response = new GatewaySmsStatusCheckResponse();

        String statusResponse = StringUtil.read(holder.getReader());

        LOG.debug("mgage status response: "+statusResponse);

        Document document = getXMLDocument(statusResponse);

        if( document == null ) {
            LOG.error(" Couldn't parse xml "+statusResponse);
            response.setResponseMessage(INVALID_XML);
            response.setStatus(MgageSmsGatewayStatus.UNKNOWN);
            return response;
        }

        if(document.getDocumentElement().getNodeName().equals(XML_ELEMENT_ERROR)) {

            String code = extractElementValue(document, XML_ELEMENT_CODE);
            String message = extractElementValue(document, XML_ELEMENT_DESC);
            response.setResponseCode(code);
            response.setResponseMessage(message);
            GatewaySmsStatus gatewaySmsStatus = findGatewaySmsStatus(code);
            response.setStatus(gatewaySmsStatus);
        }
        else {
            String code = extractElementValue(document, XML_ELEMENT_DN_RESPONSE, XML_ELEMENT_ERROR_CODE);
            String carrierStatus = extractElementValue(document, XML_ELEMENT_DN_RESPONSE, XML_ELEMENT_CARRIER_STATUS);

            if( code.equals(SUCCESS_RESPONSE_CODE) || code.equals(NOT_AVAILABLE)) {
                response.setStatus(findGatewaySmsStatus(carrierStatus));
            }
            else {
                response.setError(true);
            }
            response.setResponseCode(code);
            response.setResponseMessage(carrierStatus);
        }

        return response;
    }

    protected GatewaySmsStatus findGatewaySmsStatus(String statusName) {
        for(GatewaySmsStatus gatewaySmsStatus : MgageSmsGatewayStatus.values()){
            if(gatewaySmsStatus.getName().equals(statusName)){
                return gatewaySmsStatus;
            }
        }
        return null;
    }

    private String extractElementValue(Document document, String elementName) {
        NodeList nodes = document.getElementsByTagName(elementName);

        Element firstDNResponseNode = (Element) nodes.item(0);

        return getCharacterDataFromElement(firstDNResponseNode);
    }

    @Override
    public ConnectionHolder getStatusCheckConnectionHolder(String correlationId){
        String smsStatusURL = pollUrl+"?aid="+accountId+"&pin="+password+"&ackid="+correlationId;
        try {

            return ConnectionUtil.getConnectionReader(smsStatusURL);
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return null;
    }

    @Override
    public void resetSmsSubmissionCount() {

    }

    @Override
    public Integer getSmsSubmissionCount() {
        return null;
    }

    @Override
    public String getSmsGatewayName() {
        return SMS_GATEWAY_NAME;
    }

    private String extractElementValue(Document document, String parentElement, String childElement) {

        NodeList nodes = document.getElementsByTagName(parentElement);

        Element firstDNResponseNode = (Element) nodes.item(0);

        NodeList statusNode = firstDNResponseNode.getElementsByTagName(childElement);
        Element line = (Element) statusNode.item(0);

        return getCharacterDataFromElement(line);
    }

    private Document getXMLDocument(String xml) {
        DocumentBuilder db;
        Document doc;
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            try {
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is);
                
                return doc;
                
                
            } catch (SAXException | IOException e) {
                LOG.error("Error", e);
            }

        } catch (ParserConfigurationException e) {
            LOG.error("Error", e);
        }
        return null;   
    }

    private String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    /**
     * Parses through the response to find information like response code,
     * response message and correlation id.
     */
    @Override
    public GatewaySmsSubmissionResponse readSubmissionResponse(ConnectionHolder holder) throws IOException{
        GatewaySmsSubmissionResponse gatewaySmsSubmissionResponse = SmsGatewayHandler.super.readSubmissionResponse(holder);

        if(gatewaySmsSubmissionResponse != null) {
            return gatewaySmsSubmissionResponse;
        }

        gatewaySmsSubmissionResponse = new GatewaySmsSubmissionResponse();

        String response = StringUtil.read(holder.getReader());

        LOG.debug("Gateway response on submission: "+response);

        String correlationId = StringUtil.getSubString(response, BEGIN_CORRELATION_PATTERN, END_CORRELATION_PATTERN);

        String responseCode = StringUtil.getSubString(response, BEGIN_CODE_PATTERN, END_CODE_PATTERN);
        String responseMsg = StringUtil.getSubString(response, BEGIN_ERROR_MSG, END_ERROR_MSG);

        if(correlationId == null || correlationId.equals(EMPTY_STRING)) {
            LOG.debug(" Error in gateway response ");
            gatewaySmsSubmissionResponse.setFailureType(GssFailureType.FAILURE_CARRIER);
            gatewaySmsSubmissionResponse.setError(true);
        }
        else {
            gatewaySmsSubmissionResponse.setCorrelationId(correlationId);
        }
        gatewaySmsSubmissionResponse.setResponseCode(responseCode);
        gatewaySmsSubmissionResponse.setResponseMessage(responseMsg);
        return gatewaySmsSubmissionResponse;
    }

}
