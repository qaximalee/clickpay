package com.clickpay.repository.transaction.bills_creator;

import com.clickpay.model.transaction.BillsCreator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillsCreatorRepository extends JpaRepository<BillsCreator, Long> {
    @Query(value = " select * from bill_creator " +
            "  where ( month =:month AND " +
            "  year =:year AND " +
            "  connection_type_id = :connectionTypeId AND " +
            "  created_by = :userId AND ( sub_locality_id is NULL OR sub_locality_id =:subLocalityId OR :subLocalityId is NULL) ); ",
    nativeQuery = true)
    BillsCreator getBillCreator(long userId, Long subLocalityId, Long connectionTypeId, String month, int year);

    Page<BillsCreator> findAllByCreatedBy(Long userId, Pageable pageable);
}
