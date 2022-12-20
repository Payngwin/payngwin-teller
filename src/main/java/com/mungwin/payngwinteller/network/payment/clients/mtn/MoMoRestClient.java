package com.mungwin.payngwinteller.network.payment.clients.mtn;

import com.mungwin.payngwinteller.exception.ApiException;
import com.mungwin.payngwinteller.network.payment.RestResource;
import com.mungwin.payngwinteller.network.payment.dto.mtn.*;
import com.mungwin.payngwinteller.network.payment.props.mtn.MoMoProps;
import com.mungwin.payngwinteller.security.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class MoMoRestClient extends RestResource {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate;
    private final MoMoProps moMoProps;

    @Autowired
    public MoMoRestClient(@Qualifier("paymentRestTemplate") RestTemplate restTemplate, MoMoProps moMoProps) {
        this.restTemplate = restTemplate;
        this.moMoProps = moMoProps;
    }
    public MoMoTokenDTO login() {
        try {
            HttpHeaders headers = Util.createBasicAuthHeader(moMoProps.getCollectionApiUserId(),
                    moMoProps.getCollectionApiUserKey());
            headers.set("Ocp-Apim-Subscription-Key", moMoProps.getCollectionPrimaryKey());
            HttpEntity<Object> request = new HttpEntity<>(headers);
            ResponseEntity<MoMoTokenDTO> entity = restTemplate.postForEntity(
                    prepareRequestUri(moMoProps.getCollectionHost() + moMoProps.getCollectionTokenEndpoint()),
                        request, MoMoTokenDTO.class
                    );
            if (entity.getStatusCode() == HttpStatus.OK) {
                return entity.getBody();
            } else {
                handleResponseStatus(entity.getStatusCode());
            }
        } catch (RestClientResponseException httpEx) {
            httpEx.printStackTrace();
        }
        throw ApiException.UNDOCUMENTED_ERROR;
    }
    public Boolean requestToPay(MoMoPayRequest moMoPayRequest, String referenceId, String accessToken) {
        try {
            HttpHeaders headers = createBearerAuthHeader(accessToken);
            headers.set("Ocp-Apim-Subscription-Key", moMoProps.getCollectionPrimaryKey());
//            headers.set("X-Callback-Url", HttpUtils.getBaseUrl() + moMoProps.getCollectionCallbackUrl());
            headers.set("X-Reference-Id", referenceId);
            headers.set("X-Target-Environment", MoMoProps.getEnvironment(moMoProps.getCollectionHost()));
            headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            // this is to accommodate sandbox currency
            if (MoMoProps.getEnvironment(moMoProps.getCollectionHost()).equals("sandbox")) {
                moMoPayRequest.setCurrency("EUR");
            }
            HttpEntity<MoMoPayRequest> request = new HttpEntity<>(moMoPayRequest, headers);
            ResponseEntity<Object> entity = restTemplate.postForEntity(
                    prepareRequestUri(moMoProps.getCollectionHost() + moMoProps.getCollectionRequestToPayEndpoint()),
                    request, Object.class
            );
            if (entity.getStatusCode() == HttpStatus.ACCEPTED) {
                return true;
            } else {
                handleResponseStatus(entity.getStatusCode());
            }
        } catch (RestClientResponseException httpEx) {
            httpEx.printStackTrace();
        }
        logger.warn("Nothing to process if code reaches here");
        throw ApiException.UNDOCUMENTED_ERROR;
    }
    public MoMoPayResponse getPayStatus(String referenceId, String accessToken) {
        return peekForPayStatus(referenceId, accessToken);
    }
    public MoMoPayResponse peekForPayStatus(String referenceId, String authToken) {
        try {
            HttpHeaders headers = createBearerAuthHeader(authToken);
            headers.set("Ocp-Apim-Subscription-Key", moMoProps.getCollectionPrimaryKey());
            headers.set("X-Target-Environment", MoMoProps.getEnvironment(moMoProps.getCollectionHost()));
            HttpEntity<Object> request = new HttpEntity<>(headers);
            ResponseEntity<MoMoPayResponse> entity = restTemplate.exchange(
                    prepareRequestUri(moMoProps.getCollectionHost() +
                            moMoProps.getCollectionRequestToPayEndpoint() + "/" + referenceId),
                    HttpMethod.GET,
                    request, MoMoPayResponse.class
            );
            if (entity.getStatusCode() == HttpStatus.OK) {
                return entity.getBody();
            } else {
                handleResponseStatus(entity.getStatusCode());
            }
        } catch (RestClientResponseException httpEx) {
            httpEx.printStackTrace();
        }
        logger.warn("Nothing to process if code reaches here");
        throw ApiException.UNDOCUMENTED_ERROR;
    }

    public MoMoPayResponse monitorForPayStatus(String referenceId, String authToken, boolean init, int times) {
        try {
            // slack 20s
            if (init) {
                Thread.sleep(90000);
            }
            // peek
            MoMoPayResponse payResponse = peekForPayStatus(referenceId, authToken);
            switch (payResponse.getStatus()) {
                case "PENDING" :
                    Thread.sleep(30000);
                    if (times > 10) {
                        payResponse.setReason("Payment took too long to complete");
                        return payResponse;
                    }
                    logger.info("Trying to peek for payment status again, done so {} times already", times + 1);
                    logger.info("PayResponse: {}", payResponse);
                    return monitorForPayStatus(referenceId, authToken, false, times + 1);
                case "SUCCESSFUL" :
                case "SUCCESSFULL" :
                default:
                    return payResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Cannot monitor the current payment");
        }
        logger.warn("Nothing to process if code reaches here");
        throw ApiException.UNDOCUMENTED_ERROR;
    }
}
