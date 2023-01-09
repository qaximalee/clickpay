package com.clickpay.repository.user_profile;

import com.clickpay.model.user_profile.BoxMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoxMediaRepository extends JpaRepository<BoxMedia, Long> {
    //List<BoxMedia> findAllByCreatedByAndActive(Long userId, boolean isActive);

    //Optional<BoxMedia> findByIdAndActive(Long id, boolean b);

    Optional<BoxMedia> findByIdAndActiveAndIsDeleted(Long id, boolean active, boolean isDeleted);

    List<BoxMedia> findAllByCreatedByAndActiveAndIsDeleted(Long userId, boolean active, boolean isDeleted);
}
