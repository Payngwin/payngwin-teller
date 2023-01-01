package com.mungwin.payngwinteller.util;

import com.mungwin.payngwinteller.i18n.I18nContext;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Util {
    public static DecimalFormat decimalFormat() {
        return new DecimalFormat("#,###.00", new DecimalFormatSymbols(I18nContext.getLang()));
    }
    public static DecimalFormat decimalFormat(String pattern) {
        return new DecimalFormat(pattern, new DecimalFormatSymbols(I18nContext.getLang()));
    }
}
