package com.clickpay.repository.connection_type;

import com.clickpay.model.connection_type.ConnectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionTypeRepository extends JpaRepository<ConnectionType, Long> {
    List<ConnectionType> findAllByCreatedByAndActive(Long userId, boolean isActive);

    Optional<ConnectionType> findByIdAndActive(Long id, boolean b);
}
