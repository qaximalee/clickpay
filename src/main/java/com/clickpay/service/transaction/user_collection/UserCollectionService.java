package com.clickpay.service.transaction.user_collection;

import com.clickpay.dto.transaction.PaginatedUserCollectionRequest;
import com.clickpay.dto.transaction.UserCollectionRequest;
import com.clickpay.dto.user_profile.customer.CustomerResponse;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.Customer;
import com.clickpay.repository.transaction.user_collection.UserCollectionRepository;
import com.clickpay.service.user_profile.customer.ICustomerService;
import com.clickpay.utils.enums.Months;
import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCollectionService implements IUserCollectionService {

    private final ICustomerService customerService;
    private final UserCollectionRepository repo;

    @Transactional
    @Override
    public UserCollection createUserCollection(UserCollectionRequest requestDto, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException {
        //validate customer existing
        Customer customer = customerService.findById(requestDto.getCustomerId());

        UserCollectionStatus userCollectionStatus = UserCollectionStatus.of(requestDto.getCollectionStatus());
        PaymentType paymentType = PaymentType.of(requestDto.getPaymentType());
        Months month = Months.of(requestDto.getMonth());

        checkCollectionValid(requestDto, customer, user);

        log.info("Populate user collection data.");
        UserCollection userCollection = new UserCollection();
        userCollection.setCustomer(customer);
        userCollection.setCollectionStatus(userCollectionStatus);
        userCollection.setAmount(requestDto.getAmount());
        userCollection.setPaymentType(paymentType);
        userCollection.setMonth(month);
        userCollection.setYear(requestDto.getYear());
        userCollection.setRemarks(requestDto.getRemarks());
        userCollection.setDeleted(false);

        // set audits fields
        userCollection.setCreatedBy(user.getId());
        userCollection.setCreationDate(new Date());

        return save(userCollection);
    }

    @Transactional
    @Override
    public UserCollection updateUserCollectionStatus(String status, Long collectionId, Long customerId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException{
        log.info("Updating User collection status by collection Id " + collectionId + " .");
        //get user collection
        UserCollection userCollection = getUserCollectionById(collectionId, customerId, user);

        // set user collection status
        userCollection.setCollectionStatus(UserCollectionStatus.of(status));

        // set audits fields
        userCollection.setModifiedBy(user.getId());
        userCollection.setLastModifiedDate(new Date());
        save(userCollection);

        return userCollection;

    }

//    @Override
//    public List<CustomerResponse> getCustomersByFilter(PaginatedUserCollectionRequest request, User user){
//
//        log.info("Fetching User collection by collection Id ");
//
//        List<Object[]> customersFiltered = repo.findCustomersWithFilter(request.getSubLocality(),
//                request.getCustomerStatus(),
//                request.getUserCollectionStatus(),
//                request.getConnectionType(),
//                request.getSearchInput(),
//                user.getId());
//
//        return CustomerResponse.mapListOfCustomerDetail(customersFiltered);
//
//    }


    @Transactional
    @Override
    public UserCollection getUserCollectionById(Long collectionId, Long customerId, User user) throws EntityNotFoundException {
        log.info("Fetching User collection by collection Id " + collectionId + " .");
        Optional<UserCollection> userCollection = repo.findById(collectionId);

        if (!userCollection.isPresent()) {
            log.error("User collection not found.");
            throw new EntityNotFoundException("User collection not found.");
        }
        return userCollection.get();
    }

    @Transactional
    @Override
    public UserCollection save(UserCollection userCollection) throws BadRequestException, EntityNotSavedException {
        log.info("Creating User collection.");

        if (userCollection == null) {
            log.error("User collection should not be null.");
            throw new BadRequestException("User collection should not be null.");
        }
        try {
            userCollection = repo.save(userCollection);
            log.debug("User collection with id: " + userCollection.getId() + " created successfully.");
            return userCollection;
        } catch (Exception e) {
            log.error("User collection can not be saved.");
            throw new EntityNotSavedException("User collection can not be saved.");
        }
    }

    @Transactional
    @Override
    public UserCollection delete(Long collectionId, Long customerId, User user) throws EntityNotFoundException, BadRequestException, EntityNotSavedException {
        log.info("Deleting User collection.");
        //get user collection
        UserCollection userCollection = getUserCollectionById(collectionId, customerId, user);

        // soft delete of user collection
        userCollection.setDeleted(true);

        // set audits fields
        userCollection.setModifiedBy(user.getId());
        userCollection.setLastModifiedDate(new Date());
        save(userCollection);

        return userCollection;
    }

    private boolean existsByMonthOrYearOrTypeOfCustomer(Months month, Integer year, PaymentType paymentType, Long customerId) {
        return repo.existsByMonthAndYearAndPaymentTypeAndCustomer_Id(month, year, paymentType, customerId);
    }

    private boolean existsByMonthOrYearOrCollectionStatusOfCustomer(Months month, Integer year, UserCollectionStatus collectionStatus, Long customerId) {
        return repo.existsByMonthAndYearAndCollectionStatusAndCustomer_Id(month, year, collectionStatus, customerId);
    }

    private void checkCollectionValid(UserCollectionRequest requestDto, Customer customer, User user) throws EntityAlreadyExistException, BadRequestException {
        boolean isValid = false;

        Date date = customer.getCreationDate();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int customerCreatedMonth = localDate.getMonthValue();
        int customerCreatedYear = localDate.getYear();

        int monthOfCollection = Months.of(requestDto.getMonth()).getValue();
        int yearOfCollection = requestDto.getYear();

        if (yearOfCollection < customerCreatedYear) {
            log.error("User collection month is invalid.");
            throw new EntityAlreadyExistException("User collection month is invalid.");
        } else if (monthOfCollection < customerCreatedMonth && yearOfCollection == customerCreatedYear) {
            log.error("User collection month is invalid.");
            throw new EntityAlreadyExistException("User collection month is invalid.");
        }

        // checking the for collection already created or not
        if (PaymentType.of(requestDto.getPaymentType()).equals(PaymentType.MONTHLY)) {
            isValid = existsByMonthOrYearOrTypeOfCustomer(
                    Months.of(requestDto.getMonth()),
                    requestDto.getYear(),
                    PaymentType.of(requestDto.getPaymentType()),
                    requestDto.getCustomerId()
            );
        }
        if (isValid) {
            log.error("User collection already created.");
            throw new EntityAlreadyExistException("User collection already created.");
        }

        // checking the for collection already paid or not
        if (PaymentType.of(requestDto.getPaymentType()).equals(PaymentType.INSTALLMENT)) {
            isValid = existsByMonthOrYearOrCollectionStatusOfCustomer(
                    Months.of(requestDto.getMonth()),
                    requestDto.getYear(),
                    UserCollectionStatus.PAID,
                    requestDto.getCustomerId()
            );
        }
        if (isValid) {
            log.error("User collection already paid.");
            throw new EntityAlreadyExistException("User collection already paid.");
        }

    }

}
