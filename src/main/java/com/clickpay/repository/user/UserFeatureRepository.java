package com.clickpay.repository.user;

import com.clickpay.model.user.UserFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeatureRepository extends JpaRepository<UserFeature, Long> {
    UserFeature findByUserIdAndFeature_Value(Long id, String moduleName);
}
