package com.mungwin.payngwinteller.domain.response.payment;


import com.mungwin.payngwinteller.domain.model.payment.CollectionOrder;
import com.mungwin.payngwinteller.domain.response.LinkResponse;

import java.time.Instant;

public class InitCollectionResponse {
    private String token;
    private Instant timestamp;
    private String status;
    private LinkResponse link;

    public InitCollectionResponse() {
    }

    public InitCollectionResponse(String token, Instant timestamp, String status, String uiHost) {
        this.token = token;
        this.timestamp = timestamp;
        this.status = status;
        LinkResponse l = new LinkResponse();
        l.setRel("capture");
        l.setRedirect(true);
        l.setMethod("GET");
        l.setHref(String.format("%s/pay/%s", uiHost, token));
        this.link = l;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LinkResponse getLink() {
        return link;
    }

    public void setLink(LinkResponse link) {
        this.link = link;
    }
}
