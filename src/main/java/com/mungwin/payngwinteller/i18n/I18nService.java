package com.mungwin.payngwinteller.i18n;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class I18nService {
    private final ResourceBundleMessageSource bundleMessageSource;
    private static I18nService instance;

    private I18nService() {
        this.bundleMessageSource = new ResourceBundleMessageSource();
        this.bundleMessageSource.setBasename("lang/messages");
    }
    public static I18nService getInstance() {
        if (instance == null) return new I18nService();
        return instance;
    }
    public static String t(String key, Object... extras) {
        return getInstance().bundleMessageSource.getMessage(key, extras, I18nContext.getLang());
    }
}
