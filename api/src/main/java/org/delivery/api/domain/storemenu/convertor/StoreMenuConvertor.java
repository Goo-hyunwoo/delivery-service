package org.delivery.api.domain.storemenu.convertor;

import org.delivery.api.common.annotation.Convertor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.db.storemenu.StoreMenuEntity;

import java.util.Optional;

@Convertor
public class StoreMenuConvertor {
    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(it -> StoreMenuEntity.builder()
                        .storeId(it.getStoreId())
                        .name(it.getName())
                        .amount(it.getAmount())
                        .thumbnailUrl(it.getThumbnailUrl())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }


    public StoreMenuResponse toResponse(StoreMenuEntity entity){
        return Optional.ofNullable(entity)
                .map(it -> StoreMenuResponse.builder()
                        .id(it.getId())
                        .storeId(it.getStoreId())
                        .name(it.getName())
                        .amount(it.getAmount())
                        .status(it.getStatus())
                        .thumbnailUrl(it.getThumbnailUrl())
                        .likeCount(it.getLikeCount())
                        .sequence(it.getSequence())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
