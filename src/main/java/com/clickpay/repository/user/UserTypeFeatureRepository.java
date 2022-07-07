package com.clickpay.repository.user;

import com.clickpay.model.user.UserTypeFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeFeatureRepository extends JpaRepository<UserTypeFeature, Long> {
    UserTypeFeature findByUserTypeIdAndFeature_Value(Long userTypeId, String moduleName);
}
