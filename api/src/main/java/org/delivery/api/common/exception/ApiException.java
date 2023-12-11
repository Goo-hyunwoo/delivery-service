package org.delivery.api.common.exception;

import lombok.Getter;
import org.delivery.api.common.error.ErrorCodeInf;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs{
    private final ErrorCodeInf errorCodeInf;
    private final String errorDescription;

    public ApiException(ErrorCodeInf errorCodeInf) {
        super(errorCodeInf.getDescription());
        this.errorCodeInf = errorCodeInf;
        this.errorDescription = errorCodeInf.getDescription();
    }

    public ApiException(ErrorCodeInf errorCodeInf, String errorDescription) {
        super(errorCodeInf.getDescription());
        this.errorCodeInf = errorCodeInf;
        this.errorDescription = errorDescription;
    }

    public ApiException(ErrorCodeInf errorCodeInf, Throwable tx) {
        super(tx);
        this.errorCodeInf = errorCodeInf;
        this.errorDescription = errorCodeInf.getDescription();
    }

    public ApiException(ErrorCodeInf errorCodeInf, Throwable tx, String errorDescription) {
        super(tx);
        this.errorCodeInf = errorCodeInf;
        this.errorDescription = errorDescription;
    }
}
