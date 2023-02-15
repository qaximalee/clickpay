package com.clickpay.repository.area;

import com.clickpay.model.area.SubLocality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubLocalityRepository extends JpaRepository<SubLocality, Long> {
    //List<SubLocality> findAllByCreatedBy(Long userId);

    Optional<SubLocality> findByIdAndIsDeleted(Long id, boolean isDeleted);

    List<SubLocality> findAllByCreatedByAndIsDeleted(Long userId, boolean isDeleted);

    List<SubLocality> findAllByIdInAndCreatedBy(List<Long> subLocalitiesId, long id);
}
