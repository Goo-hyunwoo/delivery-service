package org.delivery.db.store;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;

import java.math.BigDecimal;

@Entity
@Table(name = "store")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoreEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    @Column(nullable = false)
    private Double star;

    @Column(name = "thumbnail_url", nullable = false, length = 200)
    private String thumbnailUrl;

    @Column(name = "minimum_amount", precision = 11, scale = 4)
    private BigDecimal minimumAmount;

    @Column(name = "minimum_delivery_amount", precision = 11, scale = 4)
    private BigDecimal minimumDeliveryAmount;

    @Column(nullable = false, length = 20)
    private String phoneNumber;
}
