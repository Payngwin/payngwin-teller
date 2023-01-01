package com.mungwin.payngwinteller.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;

import static com.mungwin.payngwinteller.i18n.I18nService.t;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
public class ApiException extends RuntimeException {
    public static final ApiException APP_UNAUTHORIZED = new ApiException("U401",
            t("bad_user_credentials"), HttpStatus.UNAUTHORIZED);
    public static final ApiException APP_ACCESS_DENIED = new ApiException("U403",
            t("user_access_denied"), HttpStatus.FORBIDDEN);
    public static final ApiException EMAIL_ALREADY_EXISTS = new ApiException("U422",
            t("sorry_email_in_use"), HttpStatus.UNPROCESSABLE_ENTITY);
    public static final ApiException DUPLICATE_ACCOUNT_NOT_ALLOWED = new ApiException("A409",
            t("account_creation_limit"), HttpStatus.UNPROCESSABLE_ENTITY);
    public static final ApiException INVALID_TOKEN = new ApiException("T400",
            t("invalid_token"), HttpStatus.UNAUTHORIZED);
    public static final ApiException INVALID_REFRESH_TOKEN = new ApiException("T403",
            t("invalid_token"), HttpStatus.UNAUTHORIZED);
    public static final ApiException EXPIRED_TOKEN = new ApiException("T401",
            t("expired_token"), HttpStatus.UNAUTHORIZED);
    public static final ApiException INVALID_RESOURCE_ID = new ApiException("T404",
            t("invalid_token_resource_id"), HttpStatus.UNAUTHORIZED);
    public static final ApiException RESOURCE_NOT_FOUND = new ApiException("R404",
            t("resource_not_found"),HttpStatus.NOT_FOUND);
    public static final ApiException RESOURCE_ALREADY_EXIST = new ApiException("R409",
            t("resource_already_exist"), HttpStatus.CONFLICT);
    public static final ApiException IN_COMPLETE_DOCUMENTS = new ApiException("D400",
            t("incomplete_documents"), HttpStatus.BAD_REQUEST);
    public static final ApiException UNSUPPORTED_MEDIA_TYPE = new ApiException("D415",
            t("unsupported_media_type"), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    public static final ApiException BAD_REQUEST_INPUTS = new ApiException("R422",
            t("input_validation_failed"), HttpStatus.UNPROCESSABLE_ENTITY);
    public static final ApiException EXTERNAL_ID_NOT_UNIQUE = new ApiException(
            "E422", t("external_id_unique_across_tags"), HttpStatus.UNPROCESSABLE_ENTITY);
    public static final ApiException UNDOCUMENTED_ERROR = new ApiException("E500",
            t("undocumented_error"), HttpStatus.INTERNAL_SERVER_ERROR);
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
