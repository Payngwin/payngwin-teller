package com.mungwin.payngwinteller.i18n;

import java.util.Locale;

public class I18nContext {
    private static final ThreadLocal<Locale> lang = ThreadLocal.withInitial(() -> Locale.ENGLISH);
    public static Locale getLang() {
        return lang.get();
    }
    public static void setLang(String lang) {
        I18nContext.lang.set(Locale.forLanguageTag(lang));
    }
    public static void flushContext() {
        I18nContext.lang.remove();
    }
}
