package org.delivery.api.common.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCodeInf;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;
    @Valid
    private T body;


    public static <T> Api OK(T data) {
        var api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }
    public static <Object> Api ERROR(Result result) {
        var api = new Api<Object>();
        api.result = result;
        return api;
    }
    public static <Object> Api ERROR(ErrorCodeInf errorCodeInf) {
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInf);
        return api;
    }
    public static <Object> Api ERROR(ErrorCodeInf errorCodeInf, Throwable tx) {
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInf, tx);
        return api;
    }
    public static <Object> Api ERROR(ErrorCodeInf errorCodeInf, String description) {
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInf, description);
        return api;
    }
}
