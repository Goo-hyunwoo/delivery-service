package org.delivery.api.domain.userorder.convertor;

import org.delivery.api.common.annotation.Convertor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.controller.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Convertor
public class UserOrderConvertor {

    public UserOrderEntity toEntity(User user, List<StoreMenuEntity> storeMenuEntityList) {
        var totalAmount = storeMenuEntityList.stream()
                .map(it -> it.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return UserOrderEntity.builder()
                .userId(user.getId())
                .amount(totalAmount)
                .build();
    }

    public UserOrderResponse toResponse(UserOrderEntity userOrderEntity) {
        return Optional.ofNullable(userOrderEntity)
                .map(it -> UserOrderResponse.builder()
                        .id(it.getId())
                        .status(it.getStatus())
                        .amount(it.getAmount())
                        .orderedAt(it.getOrderedAt())
                        .acceptedAt(it.getAcceptedAt())
                        .cookingStartedAt(it.getCookingStartedAt())
                        .deliveryStartedAt(it.getDeliveryStartedAt())
                        .receivedAt(it.getReceivedAt())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
