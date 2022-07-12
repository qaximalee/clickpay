package com.clickpay.repository.user;

import com.clickpay.model.user.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findByTypeIgnoreCase(String userTypeName);
}
