package org.delivery.db.userorder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.userorder.enums.UserOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "user_order")
public class UserOrderEntity extends BaseEntity {
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserOrderStatus status;

    @Column(name = "amount", nullable = false, precision = 11, scale = 4)
    private BigDecimal amount;


    @Column(name = "ordered_at")
    private LocalDateTime orderedAt;


    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;


    @Column(name = "cooking_started_at")
    private LocalDateTime cookingStartedAt;


    @Column(name = "delivery_started_at")
    private LocalDateTime deliveryStartedAt;


    @Column(name = "received_at")
    private LocalDateTime receivedAt;
}
