package com.clickpay.repository.area;

import com.clickpay.model.area.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {
    List<Locality> findAllByCreatedBy(Long userId);
}
