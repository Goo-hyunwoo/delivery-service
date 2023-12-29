package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.convertor.StoreConvertor;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.convertor.StoreMenuConvertor;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.controller.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.convertor.UserOrderConvertor;
import org.delivery.api.domain.userorder.producer.UserOrderProducer;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.convertor.UserOrderMenuConvertor;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.store.StoreEntity;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderMenuService userOrderMenuService;

    private final StoreService storeService;

    private final UserOrderConvertor userOrderConvertor;
    private final UserOrderMenuConvertor userOrderMenuConvertor;

    private final StoreMenuConvertor storeMenuConvertor;
    private final StoreConvertor storeConvertor;

    private final UserOrderProducer userOrderProducer;


    public UserOrderResponse userOrder(User user, UserOrderRequest body ) {

        var storeMenuEntityList = body.getStoreMenuIdList()
                .stream()
                .map(it -> storeMenuService.getStoreMenuWithThrow(it))
                .collect(Collectors.toList());

        var userOrderEntity = userOrderConvertor.toEntity(user, body.getStoreId(), storeMenuEntityList);

        var savedUserOrderEntity = userOrderService.order(userOrderEntity);
        System.out.println(storeMenuEntityList.size());
        storeMenuEntityList.forEach(it -> {
                    System.out.println(it.getName());
                    var userOrderMenuEntity = userOrderMenuConvertor.toEntity(savedUserOrderEntity, it);
                    userOrderMenuService.order(userOrderMenuEntity);
                });

        userOrderProducer.sendOrder(userOrderEntity);

        return userOrderConvertor.toResponse(userOrderEntity);
    }

    public List<UserOrderDetailResponse> current(User user) {


        var userOrderEntityList = userOrderService.current(user.getId());
        var userOrderDetailResponseList = userOrderEntityList.stream().map(it -> {

            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
            var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity -> {
                return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
            }).collect(Collectors.toList());

            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConvertor.toResponse(it))
                    .storeMenuResponseList(storeMenuConvertor.toResponseList(storeMenuEntityList))
                    .storeResponse(storeConvertor.toResponse(storeEntity))
                    .build();

        }).collect(Collectors.toList());
        return userOrderDetailResponseList;
    }

    public List<UserOrderDetailResponse> history(User user) {
        var userOrderEntityList = userOrderService.history(user.getId());
        var userOrderDetailResponseList = userOrderEntityList.stream().map(it -> {

            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
            var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity -> {
                return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
            }).collect(Collectors.toList());

            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConvertor.toResponse(it))
                    .storeMenuResponseList(storeMenuConvertor.toResponseList(storeMenuEntityList))
                    .storeResponse(storeConvertor.toResponse(storeEntity))
                    .build();

        }).collect(Collectors.toList());
        return userOrderDetailResponseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {
        var userOrderEntity = userOrderService.getUserOrderWithoutStatusWithThrow(orderId, user.getId());
        var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
        var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity -> {
            return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
        }).collect(Collectors.toList());
        var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConvertor.toResponse(userOrderEntity))
                .storeMenuResponseList(storeMenuConvertor.toResponseList(storeMenuEntityList))
                .storeResponse(storeConvertor.toResponse(storeEntity))
                .build();
    }
}
