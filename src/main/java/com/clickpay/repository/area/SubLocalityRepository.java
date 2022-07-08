package com.clickpay.repository.area;

import com.clickpay.model.area.SubLocality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubLocalityRepository extends JpaRepository<SubLocality, Long> {
    List<SubLocality> findAllByCreatedBy(Long userId);
}
