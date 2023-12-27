package org.delivery.storeadmin.domain.user.convertor;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.storeadmin.common.annotation.Convertor;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;

@RequiredArgsConstructor
@Convertor
public class StoreUserConvertor {

    public StoreUserEntity toEntity(StoreUserRegisterRequest request, StoreEntity storeEntity) {
        var storeName = request.getStoreName();

        return StoreUserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .storeId(storeEntity.getId()) //TODO NULL 대비 체크 필요
                .build();
    }

    public StoreUserResponse toResponse(StoreUserEntity storeUserEntity, StoreEntity storeEntity) {
        return StoreUserResponse.builder()
                .user(StoreUserResponse.UserResponse.builder()
                        .id(storeUserEntity.getId())
                        .email(storeUserEntity.getEmail())
                        .status(storeUserEntity.getStatus())
                        .role(storeUserEntity.getRole())
                        .registeredAt(storeUserEntity.getRegisteredAt())
                        .unregisteredAt(storeUserEntity.getUnregisteredAt())
                        .lastLoginAt(storeUserEntity.getLastLoginAt())
                        .build())
                .store(StoreUserResponse.StoreResponse.builder()
                        .id(storeEntity.getId())
                        .name(storeEntity.getName())
                        .build())
                .build();
    }
}
