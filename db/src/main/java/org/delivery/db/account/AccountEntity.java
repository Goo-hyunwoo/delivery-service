package org.delivery.db.account;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;

@SuperBuilder // 부모 필드 포함 여부
@Entity
@Table(name = "account")
@Data
@EqualsAndHashCode(callSuper = true) // 객체 비교(부모필드 포함여부)
public class AccountEntity extends BaseEntity {
}
