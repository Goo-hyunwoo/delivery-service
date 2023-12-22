package org.delivery.api.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(ApiException apiException) {
        log.error("{}", apiException);

        var errorCodeInf = apiException.getErrorCodeInf();
        return ResponseEntity
                .status(errorCodeInf.getHttpStatusCode())
                .body(Api.ERROR(errorCodeInf, apiException.getErrorDescription()));
    }
}
