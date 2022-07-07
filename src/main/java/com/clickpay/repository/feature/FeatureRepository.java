package com.clickpay.repository.feature;

import com.clickpay.model.feature.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    Feature findByValueAndActive(String value, Boolean aTrue);
}
