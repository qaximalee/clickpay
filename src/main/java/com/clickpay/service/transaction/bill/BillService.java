package com.clickpay.service.transaction.bill;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.bill.Bill;
import com.clickpay.repository.bill.BillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillService implements IBillService{

    private final BillRepository repo;

    @Transactional
    @Override
    public Bill save(Bill userBill) throws BadRequestException, EntityNotSavedException {
        log.info("Creating User collection's Bill.");

        if (userBill == null) {
            log.error("User Bill should not be null.");
            throw new BadRequestException("User Bill should not be null.");
        }
        try {
            userBill = repo.save(userBill);
            log.debug("User Bill with number: " + userBill.getBillNumber() + " created successfully.");
            return userBill;
        } catch (Exception e) {
            log.error("User Bill can not be saved.");
            throw new EntityNotSavedException("User Bill can not be saved.");
        }
    }
}
