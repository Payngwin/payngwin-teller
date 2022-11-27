package com.mungwin.payngwinteller.domain.repository;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Converter
public class CSVMapConverter implements AttributeConverter<Map<Long, List<String>>, String> {
    @Override
    public String convertToDatabaseColumn(Map<Long, List<String>> authorityMap) {
        if (authorityMap == null || authorityMap.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        authorityMap.forEach((key, value) -> sb.append("--")
                .append(key).append("=")
                .append(String.join(",", value))
                .append(";"));
        return sb.toString();
    }

    @Override
    public Map<Long, List<String>> convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        if (s.contains(";")) {
            String[] authorityGroups = s.split(";");
            Map<Long, List<String>> authorityMap = new HashMap<>();
            for (String group: authorityGroups) {
                if (!group.isEmpty()) {
                    authorityMap.put(
                            Long.parseLong(
                                    group.substring(group.indexOf("--"), group.indexOf("="))
                                            .replace("--", "")),
                            Arrays.asList(group.substring(group.indexOf("=") + 1).split(",")));
                }
            }
            return authorityMap;
        }
        return null;
    }
}
