package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.common.annotation.Business;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.convertor.StoreMenuConvertor;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.convertor.UserOrderConvertor;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {
    private final UserOrderService userOrderService;
    private final UserOrderConvertor userOrderConvertor;
    private final SseConnectionPool sseConnectionPool;

    private final UserOrderMenuService userOrderMenuService;
    private final StoreMenuService storeMenuService;
    private final StoreMenuConvertor storeMenuConvertor;

    /**
     * 주문
     * 주문 내역 찾기
     * 스토어 찾기
     * 연결된 세션 찾기
     * push
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage) {
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElseThrow(() -> new RuntimeException("사용자 주문 없음"));

        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());
        var storeMenuResponseList = userOrderMenuList.stream()
                .map(it -> {
                    return storeMenuService.getStoreMenuWithThrow(it.getStoreMenuId());
                })
                .map(it -> {
                    return storeMenuConvertor.toResponse(it);
                })
                .collect(Collectors.toList());

        var userOrderResponse = userOrderConvertor.toResponse(userOrderEntity);

        var push = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeMenuResponseList(storeMenuResponseList)
                .build();


        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        userConnection.sendMessage(push);
    }
}
