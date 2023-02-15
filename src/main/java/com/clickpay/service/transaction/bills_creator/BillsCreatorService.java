package com.clickpay.service.transaction.bills_creator;

import com.clickpay.dto.transaction.bills_creator.BillsCreatorCreateRequest;
import com.clickpay.dto.transaction.user_collection.UserCollectionRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.SubLocality;
import com.clickpay.model.transaction.BillsCreator;
import com.clickpay.model.connection_type.ConnectionType;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.Customer;
import com.clickpay.repository.transaction.bills_creator.BillsCreatorRepository;
import com.clickpay.service.area.sub_locality.ISubLocalityService;
import com.clickpay.service.connection_type.IConnectionTypeService;
import com.clickpay.service.transaction.ITransactionService;
import com.clickpay.service.transaction.user_collection.IUserCollectionService;
import com.clickpay.service.user_profile.customer.ICustomerService;
import com.clickpay.utils.enums.Months;
import com.clickpay.utils.enums.PaymentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ICustomerService customerService;
    private final IUserCollectionService userCollectionService;

    @Transactional
    @Override
    public BillsCreator createBillsCreator(BillsCreatorCreateRequest request, User user) throws BadRequestException, EntityNotFoundException, EntityAlreadyExistException, EntityNotSavedException {

        // checking bill creator valid or not
        checkBillCreatorValid(request,user);

        ConnectionType connectionType = connectionTypeService.findById(request.getConnectionType());

        BillsCreator billsCreator = new BillsCreator();

        billsCreator.setConnectionType(connectionType);
        if(request.getSubLocality()!=null){
            SubLocality subLocality = subLocalityService.findById(request.getSubLocality());
            billsCreator.setSubLocality(subLocality);
        }
        billsCreator.setMonth(Months.of(request.getMonth()));
        billsCreator.setYear(request.getYear());
        billsCreator = repo.save(billsCreator);

        // get customers for create user collections
        List<Customer> customers;
        if (request.getSubLocality() == null){
            customers = customerService.findAllCustomerByIdAndConnectionTypeId(user.getId(), request.getConnectionType());
        }else {
            customers = customerService.findAllCustomerByIdAndConnectionTypeIdAndSubLocalityId(user.getId(),
                    request.getConnectionType(), request.getSubLocality());
        }

        double totalAmount = 0;

        // creating user collections
        for(Customer customer : customers){
            totalAmount = totalAmount + customer.getAmount();
            UserCollectionRequest userCollectionRequest = new UserCollectionRequest();
            userCollectionRequest.setCustomerId(customer.getId());
            userCollectionRequest.setAmount(customer.getAmount());
            userCollectionRequest.setMonth(request.getMonth());
            userCollectionRequest.setYear(request.getYear());
            userCollectionRequest.setPaymentType(PaymentType.MONTHLY.name());
            userCollectionRequest.setBillCreator(billsCreator);
            userCollectionService.createUserCollection(userCollectionRequest,user);
        }

        request.setAmount(totalAmount);
        request.setNoOfUsers(customers.size());

        // remaining bill creator field save
        billsCreator.setAmount(request.getAmount());
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
    public Page<BillsCreator> getAllBillCreatorsByUserId(Long userId, int pageNo, int pageSize) throws EntityNotFoundException {
        log.info("Fetching bills creator list by user id: "+userId);
        Pageable paging = PageRequest.of(pageNo,pageSize);
        Page<BillsCreator> billsCreatorsList = repo.findAllByCreatedByAndIsDeleted(userId,paging,false);
        if (CollectionUtils.isEmpty(billsCreatorsList.getContent())) {
            log.error("No Bills Creator data found.");
            throw new EntityNotFoundException("Bills Creator list not found.");
        }
        return billsCreatorsList;
    }

    @Override
    public void checkBillCreatorValid(BillsCreatorCreateRequest request, User user) throws EntityAlreadyExistException {
        BillsCreator billsCreator = repo.getIfExistsBillCreator(user.getId(),request.getSubLocality(),request.getConnectionType(),request.getMonth(),request.getYear(),false);
        if (billsCreator!=null) {
            log.error("Bill Creator Already Created.");
            throw new EntityAlreadyExistException("Bill Creator Already Created.");
        }
        System.out.println("Bill Creator Is Valid.");
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
