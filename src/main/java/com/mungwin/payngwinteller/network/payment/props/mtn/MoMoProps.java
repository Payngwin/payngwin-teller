package com.mungwin.payngwinteller.network.payment.props.mtn;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "momo")
@Component
public class MoMoProps {
    private String collectionPrimaryKey;
    private String collectionSecondaryKey;
    private String collectionApiUserId;
    private String collectionApiUserKey;
    private String collectionHost;
    private String collectionTokenEndpoint;
    private String collectionRequestToPayEndpoint;
    private String collectionCallbackUrl;

    public static String getEnvironment(String host) {
        if (host.contains("sandbox")){
            return "sandbox";
        } else {
            return "mtncameroon";
        }
    }

    public String getCollectionPrimaryKey() {
        return collectionPrimaryKey;
    }

    public void setCollectionPrimaryKey(String collectionPrimaryKey) {
        this.collectionPrimaryKey = collectionPrimaryKey;
    }

    public String getCollectionSecondaryKey() {
        return collectionSecondaryKey;
    }

    public void setCollectionSecondaryKey(String collectionSecondaryKey) {
        this.collectionSecondaryKey = collectionSecondaryKey;
    }

    public String getCollectionApiUserId() {
        return collectionApiUserId;
    }

    public void setCollectionApiUserId(String collectionApiUserId) {
        this.collectionApiUserId = collectionApiUserId;
    }

    public String getCollectionApiUserKey() {
        return collectionApiUserKey;
    }

    public void setCollectionApiUserKey(String collectionApiUserKey) {
        this.collectionApiUserKey = collectionApiUserKey;
    }

    public String getCollectionHost() {
        return collectionHost;
    }

    public void setCollectionHost(String collectionHost) {
        this.collectionHost = collectionHost;
    }

    public String getCollectionTokenEndpoint() {
        return collectionTokenEndpoint;
    }

    public void setCollectionTokenEndpoint(String collectionTokenEndpoint) {
        this.collectionTokenEndpoint = collectionTokenEndpoint;
    }

    public String getCollectionRequestToPayEndpoint() {
        return collectionRequestToPayEndpoint;
    }

    public void setCollectionRequestToPayEndpoint(String collectionRequestToPayEndpoint) {
        this.collectionRequestToPayEndpoint = collectionRequestToPayEndpoint;
    }

    public String getCollectionCallbackUrl() {
        return collectionCallbackUrl;
    }

    public void setCollectionCallbackUrl(String collectionCallbackUrl) {
        this.collectionCallbackUrl = collectionCallbackUrl;
    }
}
