package com.clickpay.repository.user;

import com.clickpay.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByUsernameIgnoreCase(String trim);

    boolean existsByUsernameOrEmail(String internetId, String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE (u.username = :username OR u.email = :email) AND u.id <> :id")
    boolean existsByUsernameOrEmailAndNotId(@Param("username") String username, @Param("email") String email, @Param("id") Long id);
}
