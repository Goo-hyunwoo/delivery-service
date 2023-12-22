package org.delivery.api.domain.store.convertor;

import org.delivery.api.common.annotation.Convertor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.db.store.StoreEntity;

import java.util.Optional;

@Convertor
public class StoreConvertor {
    public StoreEntity toEntity(StoreRegisterRequest storeRegisterRequest) {
        return Optional.ofNullable(storeRegisterRequest)
                .map(it ->
                        StoreEntity
                                .builder()
                                .name(it.getName())
                                .address(it.getAddress())
                                .category(it.getCategory())
                                .minimumAmount(it.getMinimumAmount())
                                .minimumDeliveryAmount(it.getMinimumDeliveryAmount())
                                .thumbnailUrl(it.getThumbnailUrl())
                                .phoneNumber(it.getPhoneNumber())
                                .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreResponse toResponse(StoreEntity storeEntity) {
        return Optional.ofNullable(storeEntity)
                .map(it ->
                        StoreResponse.builder()
                                .id(it.getId())
                                .name(it.getName())
                                .address(it.getAddress())
                                .status(it.getStatus())
                                .category(it.getCategory())
                                .minimumAmount(it.getMinimumAmount())
                                .minimumDeliveryAmount(it.getMinimumDeliveryAmount())
                                .thumbnailUrl(it.getThumbnailUrl())
                                .phoneNumber(it.getPhoneNumber())
                                .star(it.getStar())
                                .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
