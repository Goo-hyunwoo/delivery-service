package org.delivery.api.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;

import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.convertor.StoreMenuConvertor;
import org.delivery.api.domain.storemenu.service.StoreMenuService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StoreMenuBusiness {
    private final StoreMenuService storeMenuService;
    private final StoreMenuConvertor storeMenuConvertor;

    public StoreMenuResponse register(
            StoreMenuRegisterRequest storeMenuRegisterRequest
    ) {
        var entity = storeMenuConvertor.toEntity(storeMenuRegisterRequest);
        var savedEntity = storeMenuService.register(entity);
        var res = storeMenuConvertor.toResponse(savedEntity);
        return res;
    }

    public List<StoreMenuResponse> searchByStoreId(Long storeId) {
        var list = storeMenuService.getStoreMenuByStoreId(storeId);
        return list.stream()
                .map(storeMenuConvertor::toResponse)
                .collect(Collectors.toList());
    }
}
