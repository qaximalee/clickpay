package com.clickpay.repository.bills_creator;

import com.clickpay.model.bills_creator.BillsCreator;
import com.clickpay.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillsCreatorRepository extends JpaRepository<BillsCreator, Long> {
    @Query(value = "  select * from bill_creator where ( month = 'JAN' AND year = 2023 AND connection_type_id = 1 AND created_by = 2 AND ( 1 is NULL OR sub_locality_id = 1 ));",
    nativeQuery = true)
    BillsCreator existsBillCreator(long id, Long subLocality, Long connectionType, String month, int year);

    List<BillsCreator> findAllByCreatedBy(Long userId);
}
