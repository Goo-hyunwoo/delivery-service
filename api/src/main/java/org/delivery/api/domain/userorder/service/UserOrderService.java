package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderService {
    private final UserOrderRepository userOrderRepository;

    public UserOrderEntity getUserOrderWithThrow(
            Long id, Long userId
    ) {
        return userOrderRepository.findFirstByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public List<UserOrderEntity> getUserOrderList(Long userId) {
        return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId,UserOrderStatus.REGISTERED);
    }

    public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList) {
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statusList);
    }

    // 현재 진행중인 내역
    public List<UserOrderEntity> current(Long userId) {
        return getUserOrderList(userId, List.of(UserOrderStatus.ACCEPT, UserOrderStatus.ORDER, UserOrderStatus.COOKING, UserOrderStatus.DELIVERY));
    }
    // 과거 주문 내역
    public List<UserOrderEntity> history(Long userId) {
        return getUserOrderList(userId, List.of(UserOrderStatus.RECEIVE));
    }


    public UserOrderEntity order(UserOrderEntity userOrderEntity) {
        return Optional.ofNullable(userOrderEntity)
                .map(it -> {
                    it.setOrderedAt(LocalDateTime.now());
                    it.setStatus(UserOrderStatus.ORDER);
                    return userOrderRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public UserOrderEntity setStatus(UserOrderEntity userOrderEntity, UserOrderStatus status) {
        userOrderEntity.setStatus(status);
        return userOrderRepository.save(userOrderEntity);
    }

    public UserOrderEntity accept(UserOrderEntity userOrderEntity) {
        userOrderEntity.setStatus(UserOrderStatus.ACCEPT);
        return userOrderRepository.save(userOrderEntity);
    }

    public UserOrderEntity cooking(UserOrderEntity userOrderEntity) {
        userOrderEntity.setStatus(UserOrderStatus.COOKING);
        return userOrderRepository.save(userOrderEntity);
    }

    public UserOrderEntity delivery(UserOrderEntity userOrderEntity) {
        userOrderEntity.setStatus(UserOrderStatus.DELIVERY);
        return userOrderRepository.save(userOrderEntity);
    }

    public UserOrderEntity receive(UserOrderEntity userOrderEntity) {
        userOrderEntity.setStatus(UserOrderStatus.RECEIVE);
        return userOrderRepository.save(userOrderEntity);
    }
}
