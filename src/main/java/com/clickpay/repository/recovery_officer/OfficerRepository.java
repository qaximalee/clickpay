package com.clickpay.repository.recovery_officer;

import com.clickpay.model.recovery_officer.Officer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, Long> {
   // List<Officer> findAllByCreatedBy(Long userId);

    @Query(value = "  SELECT * FROM officer " +
            " where (status = :status OR :status is null) AND created_by = :userId ",
    nativeQuery = true)
    Page<Officer> findAllByCreatedByAndStatus(Long userId, String status, Pageable pageable);

    Page<Officer> findAllByCreatedBy(Long userId, Pageable pageable);

    List<Officer> findAllByCreatedBy(Long userId);

    Optional<Officer> findByUserIdAndActive(long id, boolean isActive);
}
