package org.delivery.api.common.exception;

import org.delivery.api.common.error.ErrorCodeInf;

public interface ApiExceptionIfs {
    ErrorCodeInf getErrorCodeInf();
    String getErrorDescription();
}
