package org.delivery.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.convertor.StoreConvertor;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.db.store.enums.StoreCategory;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConvertor storeConvertor;

    public StoreResponse register(StoreRegisterRequest storeRegisterRequest) {
        var entity = storeConvertor.toEntity(storeRegisterRequest);
        var savedEntity = storeService.register(entity);
        var response = storeConvertor.toResponse(savedEntity);
        return response;
    }


    public List<StoreResponse> searchCategory(StoreCategory storeCategory) {
        var storeList = storeService.searchByCategory(storeCategory);
        return storeList.stream()
                .map(storeConvertor::toResponse)
                .collect(Collectors.toList());
    }
}
