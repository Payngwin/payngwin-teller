package com.mungwin.payngwinteller.domain.model.iam.utils;

import javax.validation.constraints.Pattern;

public class Document {

    @Pattern(regexp = "^(http|https|data:image)://.*", message = "Invalid Document src! document src must start with http:// or https:// or data:image")
    private String src;

    private String mediaType;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
