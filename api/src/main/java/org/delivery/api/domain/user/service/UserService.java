package org.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserEntity save(UserEntity userEntity) {
        var entity =  Optional
                .ofNullable(userEntity)
                .map(it -> {
                    userEntity.setStatus(UserStatus.REGISTERED);
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    return userEntity;
                })
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "User Entity Null"));

        return userRepository.save(entity);
    }

    public UserEntity login(String email, String password) {
        return userRepository.findFirstByEmailAndPassword(email, password)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "유저가 없습니다."));
    }

    public UserEntity getUserWithThrow(String email, String password) {
    return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(email, password, UserStatus.REGISTERED)
            .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "유저가 없습니다."));
    }

    public UserEntity getUserWithThrow(Long userId) {
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(userId, UserStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "유저가 없습니다."));
    }
}
