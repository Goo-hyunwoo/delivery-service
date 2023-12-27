package org.delivery.storeadmin.domain.user.business;


import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.common.annotation.Business;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.convertor.StoreUserConvertor;
import org.delivery.storeadmin.domain.user.service.StoreUserService;

@Business
@RequiredArgsConstructor
public class StoreUserBusiness {

    private final StoreUserConvertor storeUserConvertor;
    private final StoreUserService storeUserService;
    //TODO 변환 필요
    private final StoreRepository storeRepository;

    public StoreUserResponse register(StoreUserRegisterRequest request) {
        var storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(request.getStoreName(), StoreStatus.REGISTERED);
        var entity = storeUserConvertor.toEntity(request, storeEntity.get());
        var newEntity = storeUserService.register(entity);
        return storeUserConvertor.toResponse(newEntity, storeEntity.get());
    }

}
