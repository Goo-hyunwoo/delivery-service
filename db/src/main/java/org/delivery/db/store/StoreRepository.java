package org.delivery.db.store;

import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    //select * from store where id=? and status = 'REGISTERED'
    Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);

    //select * from store where status = ? order by id desc
    List<StoreEntity> findAllByStatusOrderByIdDesc(StoreStatus status);

    //select * from store where status = ? and category = ? order by star desc
    List<StoreEntity> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus status, StoreCategory category);

    Optional<StoreEntity> findFirstByNameAndStatusOrderByIdDesc(String email, StoreStatus status);
}
