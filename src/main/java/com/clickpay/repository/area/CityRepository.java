package com.clickpay.repository.area;

import com.clickpay.model.area.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    //List<City> findAllByCreatedBy(Long userId);

    Optional<City> findByIdAndIsDeleted(Long id, boolean isDeleted);

    List<City> findAllByCreatedByAndIsDeleted(Long userId, boolean isDeleted);
}
