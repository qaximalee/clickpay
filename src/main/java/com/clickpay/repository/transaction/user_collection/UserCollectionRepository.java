package com.clickpay.repository.transaction.user_collection;

import com.clickpay.model.transaction.UserCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {

}
