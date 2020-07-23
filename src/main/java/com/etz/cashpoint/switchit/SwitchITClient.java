package com.etz.cashpoint.switchit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author mishael.harry
 */
@Slf4j
@Service
public class SwitchITClient {

    private String url;
    private String pin;
    private String terminalId;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private XMLUtils xMLUtils;

    public SwitchITClient() {
        log.info("Initializing SwitchIT Client");
    }

    public void setCredential(String url, String pin, String terminalId) {
        this.url = url;
        this.pin = pin;
        this.terminalId = terminalId;
        log.info("ROUTING TRANSACTIONS to " + url + " with termainl ID " + terminalId);
    }

    public SwitchResponse sendMoney(FundTransfer ft) throws Exception {
        String identifier;
        if (ft.isCardTransfer()) {
            identifier = ft.getCardNumber();
           // log.info("Funds Transfer to Account: " + identifier);
        } else {
            identifier = ft.getAccountNumber();
          //  log.info("Funds Transfer to Card: " + identifier);
        }

        String xmlPayload = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.fundgate.etranzact.com/\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <ws:process>\n"
                + "         <request>\n"
                + "            <direction>request</direction>\n"
                + "            <action>FT</action>\n"
                + "            <terminalId>" + terminalId + "</terminalId>\n"
                + "            <transaction>\n"
                + "               <pin>" + pin + "</pin>\n"
                + "               <bankCode>" + ft.getBankCode() + "</bankCode>\n"
                + "               <currency>NGN</currency>\n"
                + "               <amount>" + ft.getAmount() + "</amount>\n"
                + "               <description>" + ft.getDescription() + "</description>\n"
                + "               <destination>" + identifier + "</destination>\n"
                + "               <reference>" + ft.getReference() + "</reference>\n"
                + "               <endPoint>" + ft.getEndpoint() + "</endPoint>\n"
                + "            </transaction>\n"
                + "         </request>\n"
                + "      </ws:process>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";

        String response = sendPost(url, xmlPayload);
        xMLUtils.setXml(response);
        return new SwitchResponse(xMLUtils.getXMLValue("reference"), Double.parseDouble(xMLUtils.getXMLValue("amount")), xMLUtils.getXMLValue("totalFailed"), xMLUtils.getXMLValue("totalSuccess"), xMLUtils.getXMLValue("error"), xMLUtils.getXMLValue("message"), xMLUtils.getXMLValue("otherReference"), xMLUtils.getXMLValue("action"), xMLUtils.getXMLValue("openingBalance"), xMLUtils.getXMLValue("closingBalance"));
    }

    private String sendPost(String url, String payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        HttpEntity<String> request = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return response.getBody();
    }

}
