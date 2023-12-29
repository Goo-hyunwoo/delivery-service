package org.delivery.storeadmin.domain.storemenu.convertor;

import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreMenuConvertor {
    public StoreMenuResponse toResponse(StoreMenuEntity storeMenuEntity) {
        return StoreMenuResponse.builder()
                .id(storeMenuEntity.getId())
                .name(storeMenuEntity.getName())
                .status(storeMenuEntity.getStatus())
                .amount(storeMenuEntity.getAmount())
                .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
                .likeCount(storeMenuEntity.getLikeCount())
                .sequence(storeMenuEntity.getSequence())
                .build();
    }

    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> list) {
        return list.stream().map(it -> {return this.toResponse(it);}).collect(Collectors.toList());
    }
}
