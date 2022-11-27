package com.mungwin.payngwinteller.security.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
public class ApiException extends RuntimeException {
    public static final ApiException USER_UNAUTHORIZED = new ApiException("U401",
            "User is not authenticated", HttpStatus.UNAUTHORIZED);
    public static final ApiException USER_ACCESS_DENIED = new ApiException("U403",
            "Access is denied for this user", HttpStatus.FORBIDDEN);
    public static final ApiException EMAIL_ALREADY_EXISTS = new ApiException("U422",
            "User email already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    public static final ApiException PHONE_ALREADY_EXISTS = new ApiException("U422B",
            "User phone already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    public static final ApiException DUPLICATE_ACCOUNT_NOT_ALLOWED = new ApiException("A409",
            "Duplicate account not allowed", HttpStatus.UNPROCESSABLE_ENTITY);
    public static final ApiException INVALID_TOKEN = new ApiException("T400",
            "Invalid token", HttpStatus.UNAUTHORIZED);
    public static final ApiException INVALID_REFRESH_TOKEN = new ApiException("T403",
            "Invalid Refresh token", HttpStatus.UNAUTHORIZED);
    public static final ApiException EXPIRED_TOKEN = new ApiException("T401",
            "Expired token", HttpStatus.UNAUTHORIZED);
    public static final ApiException INVALID_RESOURCE_ID = new ApiException("T404",
            "Invalid Resource id supplied with token", HttpStatus.UNAUTHORIZED);
    public static final ApiException RESOURCE_NOT_FOUND = new ApiException("R404",
            "Resource not found",HttpStatus.NOT_FOUND);
    public static final ApiException RESOURCE_ALREADY_EXIST = new ApiException("R409",
            "Resource already exist", HttpStatus.CONFLICT);
    public static final ApiException IN_COMPLETE_DOCUMENTS = new ApiException("D400",
            "Incomplete Documents", HttpStatus.BAD_REQUEST);
    public static final ApiException UNSUPPORTED_MEDIA_TYPE = new ApiException("D415",
            "Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    public static final ApiException BAD_REQUEST_INPUTS = new ApiException("R422",
            "One or more inputs were invalid", HttpStatus.UNPROCESSABLE_ENTITY);

    private String code;
    private final StringBuilder details = new StringBuilder();
    private HttpStatus status;

    public ApiException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetails() {
        return details.toString();
    }

    public StringBuilder setDetails(String details) {
        this.details.append(details);
        return this.details;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
