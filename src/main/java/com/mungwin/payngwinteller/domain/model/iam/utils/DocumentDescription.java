package com.mungwin.payngwinteller.domain.model.iam.utils;

import java.util.List;

public class DocumentDescription {
    private String description;
    private List<String> mediaTypes;

    public DocumentDescription(String description, List<String> mediaTypes) {
        this.description = description;
        this.mediaTypes = mediaTypes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMediaTypes() {
        return mediaTypes;
    }

    public void setMediaTypes(List<String> mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

}
