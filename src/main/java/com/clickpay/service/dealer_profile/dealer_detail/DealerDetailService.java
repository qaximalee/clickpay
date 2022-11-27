package com.clickpay.service.dealer_profile.dealer_detail;

import com.clickpay.dto.dealer_profile.dealer_detail.PaginatedDealerRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.dealer_profile.dealer_detail.Dealer;
import com.clickpay.repository.dealer_profile.dealer_detail.DealerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class DealerDetailService implements IDealerDetailService{

    private final DealerRepository repo;

    public DealerDetailService(DealerRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    @Override
    public Dealer findById(Long id) throws BadRequestException, EntityNotFoundException {
        log.info("Finding dealer by id: "+id);
        if (id == null || id < 1) {
            log.error("Dealer id "+id+ " is invalid.");
            throw new BadRequestException("Provided dealer id should be a valid and non null value.");
        }

        Optional<Dealer> dealer = repo.findByIdAndIsDeleted(id,false);
        if (dealer == null || !dealer.isPresent()) {
            log.error("No dealer found with dealer id: "+id);
            throw new EntityNotFoundException("No dealer found with provided dealer id.");
        }
        return dealer.get();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isExistsByDealerID(String dealerId) throws EntityAlreadyExistException {
        boolean isExists = repo.existsByDealerID(dealerId);
        if (isExists) {
            log.error("Dealer with dealerID: "+dealerId+" already exists.");
            throw new EntityAlreadyExistException("Dealer with dealerID: "+dealerId+" already exists.");
        }
        return isExists;
    }

    @Transactional
    @Override
    public Dealer save(Dealer dealer) throws EntityNotSavedException, BadRequestException {
        log.info("Saving dealer.");
        if (dealer == null) {
            log.error("Dealer should not be null.");
            throw new BadRequestException("Dealer should not be null.");
        }
        try {
            return repo.save(dealer);
        } catch (Exception e) {
            log.error("Dealer can not be saved.");
            throw new EntityNotSavedException("Dealer can not be saved.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Dealer> findAllDealerByUserId(Long userId, Boolean isDeleted, Pageable pageable) throws EntityNotFoundException {
        log.info("Fetching dealer list by user id: "+userId);
        Page<Dealer> dealerList = repo.findAllByCreatedByAndIsDeleted(userId,isDeleted,pageable);
        if (dealerList == null || dealerList.isEmpty()) {
            log.error("No dealer data found.");
            throw new EntityNotFoundException("Dealer list not found.");
        }
        return dealerList;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Dealer> findAllDealerByUserIdWithFilter(PaginatedDealerRequest dto, Long userId, Boolean isDeleted, Pageable pageable) throws EntityNotFoundException {
        log.info("Fetching dealer list by user id: "+userId);
        Page<Dealer> dealerList = repo.findAllByCreatedByAndIsDeletedAndCompany_NameAndLocality_NameAndStatus(
                userId,isDeleted,dto.getCompany(),dto.getLocality(),dto.getStatus(),pageable);
        if (dealerList == null || dealerList.isEmpty()) {
            log.error("No dealer data found.");
            throw new EntityNotFoundException("Dealer list not found.");
        }
        return dealerList;
    }

}
