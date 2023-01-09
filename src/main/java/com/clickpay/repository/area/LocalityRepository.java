package com.clickpay.repository.area;

import com.clickpay.model.area.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {
    //List<Locality> findAllByCreatedBy(Long userId);

    Optional<Locality> findByIdAndIsDeleted(Long id, boolean isDeleted);

    List<Locality> findAllByCreatedByAndIsDeleted(Long userId, boolean isDeleted);
}
