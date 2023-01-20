package com.clickpay.service.transaction.bills_creator;

import com.clickpay.dto.transaction.bills_creator.BillsCreatorRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.area.SubLocality;
import com.clickpay.model.bills_creator.BillsCreator;
import com.clickpay.model.connection_type.ConnectionType;
import com.clickpay.model.user.User;
import com.clickpay.repository.bills_creator.BillsCreatorRepository;
import com.clickpay.service.area.sub_locality.ISubLocalityService;
import com.clickpay.service.connection_type.IConnectionTypeService;
import com.clickpay.utils.enums.Months;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillsCreatorService implements IBillsCreatorService{
    private final BillsCreatorRepository repo;
    private final IConnectionTypeService connectionTypeService;
    private final ISubLocalityService subLocalityService;

    @Transactional
    @Override
    public BillsCreator createBillsCreator(BillsCreatorRequest request, User user) throws BadRequestException, EntityNotFoundException {
        ConnectionType connectionType = connectionTypeService.findById(request.getConnectionType());

        BillsCreator billsCreator = new BillsCreator();

        billsCreator.setAmount(request.getAmount());
        billsCreator.setConnectionType(connectionType);
        if(request.getSubLocality()!=null){
            SubLocality subLocality = subLocalityService.findById(request.getSubLocality());
            billsCreator.setSubLocality(subLocality);
        }
        billsCreator.setMonth(Months.of(request.getMonth()));
        billsCreator.setYear(request.getYear());
        billsCreator.setNoOfUsers(request.getNoOfUsers());
        billsCreator.setDeleted(false);
        billsCreator.setBy(user);
        // set audits
        billsCreator.setCreatedBy(user.getId());
        billsCreator.setCreationDate(new Date());

        repo.save(billsCreator);

        return billsCreator;

    }

    @Transactional(readOnly = true)
    @Override
    public List<BillsCreator> getAllBillCreatorsByUserId(Long userId) throws EntityNotFoundException {
        log.info("Fetching bills creator list by user id: "+userId);
        List<BillsCreator> billsCreatorsList = repo.findAllByCreatedBy(userId);
        if (CollectionUtils.isEmpty(billsCreatorsList)) {
            log.error("No Bills Creator data found.");
            throw new EntityNotFoundException("Bills Creator list not found.");
        }
        return billsCreatorsList;
    }

    private void checkBillCreatorValid(BillsCreatorRequest request, User user){
        BillsCreator billsCreator = repo.existsBillCreator(user.getId(),request.getSubLocality(),request.getConnectionType(),request.getMonth(),request.getYear());
    }

    @Transactional
    @Override
    public BillsCreator deleteBillCreators(Long billCreatorId, User user) throws EntityNotFoundException {
        log.info("Deleting bills creator by id: "+billCreatorId);

        Optional<BillsCreator> billsCreator = repo.findById(billCreatorId);
        if (!billsCreator.isPresent()) {
            log.error("No Bills Creator data found by Id.");
            throw new EntityNotFoundException("Bills Creator list not found by Id.");
        }

        billsCreator.get().setDeleted(true);
        // set audits
        billsCreator.get().setModifiedBy(user.getId());
        billsCreator.get().setLastModifiedDate(new Date());

        repo.save(billsCreator.get());

        return billsCreator.get();
    }

}
