package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.convertor.UserConvertor;
import org.delivery.api.domain.user.service.UserService;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;
    private final UserConvertor userConvertor;

    /**
     * 사용자에 대한 가입처리 로직
     * 1. request -> entity
     * 2. entity -> save
     * 3. save Entity -> response
     * 4. return response
     */
    public UserResponse register(UserRegisterRequest request) {

        var entity = userConvertor.toEntity(request);
        var newEntity = userService.save(entity);
        var response = userConvertor.toResponse(newEntity);

        return response;
        /*
        return Optional.ofNullable(request)
                .map(userConvertor::toEntity)
                .map(userService::save)
                .map(userConvertor::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request Null"));
        */
    }
}
