package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.controller.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.convertor.UserOrderConvertor;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.convertor.UserOrderMenuConvertor;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderMenuService userOrderMenuService;

    private final UserOrderConvertor userOrderConvertor;
    private final UserOrderMenuConvertor userOrderMenuConvertor;


    public UserOrderResponse userOrder(User user, UserOrderRequest body ) {

        var storeMenuEntityList = body.getStoreMenuIdList()
                .stream()
                .map(it -> storeMenuService.getStoreMenuWithThrow(it))
                .collect(Collectors.toList());

        var userOrderEntity = userOrderConvertor.toEntity(user, storeMenuEntityList);

        var savedUserOrderEntity = userOrderService.order(userOrderEntity);

        storeMenuEntityList.stream()
                .map(it -> {
                    var userOrderMenuEntity = userOrderMenuConvertor.toEntity(savedUserOrderEntity, it);
                    return userOrderMenuService.order(userOrderMenuEntity);
                });

        return userOrderConvertor.toResponse(userOrderEntity);
    }
}
