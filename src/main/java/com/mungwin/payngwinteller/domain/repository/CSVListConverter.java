package com.mungwin.payngwinteller.domain.repository;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Converter
public class CSVListConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> authorities) {
        return authorities != null && !authorities.isEmpty() ?  String.join(",", authorities) : null;
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return s.contains(",") ? Arrays.asList(s.split(",")) : Collections.singletonList(s);
    }
}
