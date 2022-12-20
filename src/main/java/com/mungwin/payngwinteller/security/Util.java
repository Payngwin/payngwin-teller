package com.mungwin.payngwinteller.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Util {
    public static <T> T[] concatArray(T[] array1, T[] array2) {
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
    public static HttpHeaders createBasicAuthHeader(String username, String password) {
        return new HttpHeaders(){{
            String authHeader = "Basic " + generateBasicAuth(username, password);
            set("Authorization", authHeader);
        }};
    }
    public static String generateBasicAuth(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodeAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.US_ASCII)
        );
        return new String(encodeAuth);
    }
    public static BasicAuthParts parseBasicAuth(String authStr) {
        if(authStr == null || authStr.isEmpty() || !authStr.startsWith("Basic ")) {
            return null;
        }
        try {
            String str = authStr.split("Basic ")[1];
            String decodedStr = new String(Base64.decodeBase64(str));
            if (!decodedStr.contains(":")) {
                return  null;
            }
            String[] parts = decodedStr.split(":");
            return new BasicAuthParts(parts);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class BasicAuthParts {
        private String username;
        private String password;
        public BasicAuthParts(String [] parts) {
            this.username = parts[0];
            this.password = parts[1];
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
