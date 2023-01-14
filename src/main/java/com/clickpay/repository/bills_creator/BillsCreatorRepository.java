package com.clickpay.repository.bills_creator;

import com.clickpay.model.bills_creator.BillsCreator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillsCreatorRepository extends JpaRepository<BillsCreator,Long> {
}
