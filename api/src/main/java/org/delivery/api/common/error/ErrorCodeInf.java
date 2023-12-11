package org.delivery.api.common.error;

public interface ErrorCodeInf {
    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getDescription();
}
