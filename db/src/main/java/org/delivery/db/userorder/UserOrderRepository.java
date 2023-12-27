package org.delivery.db.userorder;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {


    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus userOrderStatus);

    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> status);

    Optional<UserOrderEntity> findFirstByIdAndStatusAndUserId(Long id, UserOrderStatus userOrderStatus, Long userId);

    Optional<UserOrderEntity> findFirstByIdAndUserId(Long id, Long userId);
}
