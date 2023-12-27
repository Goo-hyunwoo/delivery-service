package org.delivery.db.storeuser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "store_user")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class StoreUserEntity extends BaseEntity {

        @Column(name = "store_id", nullable = false)
        private Long storeId;

        @Column(nullable = false, length = 100)
        private String email;

        @Column(nullable = false, length = 100)
        private String password;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private StoreUserStatus status;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private StoreUserRole role;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "registered_at")
        private LocalDateTime registeredAt;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "unregistered_at")
        private LocalDateTime unregisteredAt;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "last_login_at")
        private LocalDateTime lastLoginAt;

}
