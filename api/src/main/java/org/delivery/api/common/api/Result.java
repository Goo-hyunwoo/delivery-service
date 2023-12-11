package org.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.ErrorCodeInf;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result OK() {
        return Result
                .builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage(ErrorCode.OK.getDescription())
                .resultDescription("성공")
                .build();
    }

    public static Result ERROR(ErrorCodeInf errorCodeInf) {
        return Result
                .builder()
                .resultCode(errorCodeInf.getErrorCode())
                .resultMessage(errorCodeInf.getDescription())
                .resultDescription(errorCodeInf.getDescription())
                .build();
    }

    //StackTrace를 내려주는 코드로 비추하지만 확인 필요
    public static Result ERROR(ErrorCodeInf errorCodeInf, Throwable tx) {
        return Result
                .builder()
                .resultCode(errorCodeInf.getErrorCode())
                .resultMessage(errorCodeInf.getDescription())
                .resultDescription(tx.getLocalizedMessage())
                .build();
    }

    public static Result ERROR(ErrorCodeInf errorCodeInf, String description) {
        return Result
                .builder()
                .resultCode(errorCodeInf.getErrorCode())
                .resultMessage(errorCodeInf.getDescription())
                .resultDescription(description)
                .build();
    }
}
