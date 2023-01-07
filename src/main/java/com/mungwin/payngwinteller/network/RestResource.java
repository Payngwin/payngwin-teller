package com.mungwin.payngwinteller.network;
import com.mungwin.payngwinteller.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class RestResource {
    protected static final String SERVER_RESPONDED = "Server Responded with";
    protected Logger logger = LoggerFactory.getLogger(RestResource.class);
    protected String prepareRequestUri(String uri) {
        logger.info("Contacting Server: {}", uri);
        return uri;
    }
    protected void handleResponseStatus(HttpStatus httpStatus){
        String message = "";
        throw new ApiException("", "", httpStatus);
    }

    public HttpHeaders createBearerAuthHeader(String token) {
        return new HttpHeaders(){{
            String authHeader = "Bearer " + token;
            set("Authorization", authHeader);
        }};
    }
}
