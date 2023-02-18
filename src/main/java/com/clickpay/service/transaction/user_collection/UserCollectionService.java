package com.clickpay.service.transaction.user_collection;

import com.clickpay.dto.transaction.user_collection.UserCollectionRequest;
import com.clickpay.dto.transaction.user_collection.UserCollectionStatusUpdateAsPaidDTO;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.Bill;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.Customer;
import com.clickpay.repository.transaction.user_collection.UserCollectionRepository;
import com.clickpay.service.transaction.bill.IBillService;
import com.clickpay.service.user_profile.customer.ICustomerService;
import com.clickpay.utils.enums.Months;
import com.clickpay.utils.enums.PaymentMethod;
import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCollectionService implements IUserCollectionService {

    private final ICustomerService customerService;
    private final UserCollectionRepository repo;
    private final IBillService billService;

    @Transactional
    @Override
    public UserCollection createUserCollection(UserCollectionRequest requestDto, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException {
        //validate customer existing
        Customer customer = customerService.findById(requestDto.getCustomerId());

        UserCollectionStatus userCollectionStatus = UserCollectionStatus.UNPAID;
        PaymentType paymentType = PaymentType.of(requestDto.getPaymentType());
        Months month = Months.of(requestDto.getMonth());

        checkCollectionValid(requestDto, customer, user);

        UserCollection userCollection = null;

        if (PaymentType.of(requestDto.getPaymentType()).equals(PaymentType.INSTALLMENT)){
            if(existsByMonthOrYearOrTypeOfCustomerOrDeleted(
                    month,
                    requestDto.getYear(),
                    PaymentType.MONTHLY,
                    requestDto.getCustomerId(),
                    false
            )){
                // create installment collection when monthly already created
                return createMonthlyInstallmentIfAlreadyCreated(requestDto, user, customer, userCollectionStatus, paymentType, month);
            }else {
                // create installment collection when monthly not created
                return createMonthlyInstallmentIfNotCreated(requestDto, user, customer, userCollectionStatus, paymentType, month);
            }
        }else {
            // create monthly collection
            log.info("Populate user collection data.");
            userCollection = UserCollection.mapInUserCollection(customer,
                    userCollectionStatus,
                    customer.getAmount(),
                    paymentType,
                    month,
                    requestDto.getYear(),
                    requestDto.getRemarks(),
                    user.getId(),
                    new Date(),
                    requestDto.getBillCreator());

            return save(userCollection);
        }
    }

    private UserCollection createMonthlyInstallmentIfNotCreated(UserCollectionRequest requestDto, User user, Customer customer, UserCollectionStatus userCollectionStatus, PaymentType paymentType, Months month) throws BadRequestException, EntityNotSavedException {
        UserCollection userCollection;
        userCollection = UserCollection.mapInUserCollection(customer,
                userCollectionStatus,
                requestDto.getAmount(),
                paymentType,
                month,
                requestDto.getYear(),
                requestDto.getRemarks(),
                user.getId(),
                new Date(),
                null);

        save(userCollection);

        UserCollection userCollection1 = UserCollection.mapInUserCollection(customer,
                userCollectionStatus,
                (customer.getAmount()- requestDto.getAmount()),
                paymentType,
                month,
                requestDto.getYear(),
                requestDto.getRemarks(),
                user.getId(),
                new Date(),
                null);

        return save(userCollection1);
    }

    private UserCollection createMonthlyInstallmentIfAlreadyCreated(UserCollectionRequest requestDto, User user, Customer customer, UserCollectionStatus userCollectionStatus, PaymentType paymentType, Months month) throws EntityNotFoundException, BadRequestException, EntityNotSavedException {
        UserCollection userCollection;
        UserCollection oldUserCollection =
                getUserCollectionByCustomerIdAndMonthAndYear(customer.getId(), month, requestDto.getYear());

        deleteUserCollection(oldUserCollection.getId());

        userCollection = UserCollection.mapInUserCollection(customer,
                userCollectionStatus,
                requestDto.getAmount(),
                paymentType,
                month,
                requestDto.getYear(),
                requestDto.getRemarks(),
                user.getId(),
                new Date(),
                null);

        save(userCollection);

        oldUserCollection = UserCollection.mapInUserCollection(customer,
                oldUserCollection.getCollectionStatus(),
                (oldUserCollection.getAmount()- requestDto.getAmount()),
                paymentType,
                month,
                requestDto.getYear(),
                requestDto.getRemarks(),
                user.getId(),
                new Date(),
                null);


        return save(oldUserCollection);
    }

    @Transactional
    @Override
    public String updateUserCollectionStatusAsPaid(UserCollectionStatusUpdateAsPaidDTO dto, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException{
        log.info("Updating User collections status as Paid.");
        Bill bill = new Bill();
        bill.setPaymentMethod(PaymentMethod.of(dto.getPaymentMethod()));
        bill.setCreatedBy(user.getId());
        bill.setCreationDate(new Date());
        bill.setAmount(dto.getAmount());
        billService.save(bill);

        dto.getCollectionIds().forEach(e -> {

            try {
                //get user collection
                UserCollection userCollection = null;

                userCollection = getUserCollectionById(e, dto.getCustomerId(), user);

                // set user collection status as paid
                userCollection.setCollectionStatus(UserCollectionStatus.PAID);
                // set audits fields
                userCollection.setModifiedBy(user.getId());
                userCollection.setLastModifiedDate(new Date());

                //set bill object
                userCollection.setBill(bill);
                save(userCollection);

            } catch (BadRequestException|EntityNotFoundException|EntityNotSavedException ex) {
                throw new RuntimeException(ex);
            }

        });

        return "User Collections Paid Successfully.";

    }

    @Transactional
    @Override
    public String updateUserCollectionStatusAsUnPaid(Long billNo, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException{
        log.info("Updating User collections status as Unpaid.");

        Bill bill = billService.getBillByBillNumber(billNo);
        bill.setDeleted(true);
        billService.save(bill);

        List<UserCollection> userCollections = getUserCollectionsByBillNumber(billNo,user);
        for (UserCollection userCollection : userCollections){
            userCollection.setBill(null);
            userCollection.setCollectionStatus(UserCollectionStatus.UNPAID);
            save(userCollection);
        }

        return "User Collections UnPaid Successfully.";

    }


    @Transactional
    @Override
    public UserCollection getUserCollectionById(Long collectionId, Long customerId, User user) throws EntityNotFoundException {
        log.info("Fetching User collection by collection Id " + collectionId + " .");
        Optional<UserCollection> userCollection = repo.findByIdAndIsDeleted(collectionId,false);

        if (!userCollection.isPresent()) {
            log.error("User collection not found or may be deleted.");
            throw new EntityNotFoundException("User collection not found or may be deleted.");
        }
        return userCollection.get();
    }

    @Transactional
    @Override
    public UserCollection getUserCollectionByCustomerIdAndMonthAndYear(Long customerId, Months month, int year) throws EntityNotFoundException {
        log.info("Fetching User collection by Customer Id : " + customerId + " and Month : "+month+" and Year : "+year+" .");
        UserCollection userCollection = repo.findByCustomer_IdAndMonthAndYearAndIsDeleted(customerId,month,year,false);

        if (userCollection==null) {
            log.error("User collection not found or may be deleted.");
            throw new EntityNotFoundException("User collection not found or may be deleted.");
        }
        return userCollection;
    }

    @Transactional
    @Override
    public Page<UserCollection> getUserCollectionByCustomerId(Long customerId, int pageNo, int pageSize) throws EntityNotFoundException {
        log.info("Fetching User collection by customer Id " + customerId + " .");

        Pageable paging = PageRequest.of(pageNo,pageSize);
        Page<UserCollection> userCollection = repo.findByCustomer_IdAndIsDeleted(customerId,false,paging);

        if (userCollection.isEmpty()) {
            log.error("User collection not found.");
            throw new EntityNotFoundException("User collection not found.");
        }
        return userCollection;
    }

    @Transactional
    @Override
    public List<UserCollection> getUserCollectionsByBillNumber(Long billNo, User user) throws EntityNotFoundException {
        log.info("Fetching User collection by bill number " + billNo + " .");
        List<UserCollection> userCollections = repo.findByBill_BillNumberAndIsDeleted(billNo,false);

        if (userCollections.isEmpty()) {
            log.error("User collection by bill number not found.");
            throw new EntityNotFoundException("User collection by bill number not found.");
        }
        return userCollections;
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

    private boolean existsByMonthOrYearOrTypeOfCustomerOrDeleted(Months month, Integer year, PaymentType paymentType, Long customerId, boolean isDeleted) {
        return repo.existsByMonthAndYearAndPaymentTypeAndCustomer_IdAndIsDeleted(month, year, paymentType, customerId, isDeleted);
    }

    private boolean existsByMonthOrYearOrDeleted(Months month, Integer year, Long customerId, boolean isDeleted) {
        return repo.existsByMonthAndYearAndCustomer_IdAndIsDeleted(month, year, customerId, isDeleted);
    }

    private boolean existsByMonthOrYearOrCollectionStatusOfCustomer(Months month, Integer year, UserCollectionStatus collectionStatus, Long customerId) {
        return repo.existsByMonthAndYearAndCollectionStatusAndCustomer_Id(month, year, collectionStatus, customerId);
    }

    private void deleteUserCollection(Long userCollectionId){
        log.info("Deleting User Collection By Id : "+userCollectionId);
        repo.deleteById(userCollectionId);
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
        if (PaymentType.of(requestDto.getPaymentType()).equals(PaymentType.MONTHLY) ){
            isValid = existsByMonthOrYearOrTypeOfCustomerOrDeleted(
                    Months.of(requestDto.getMonth()),
                    requestDto.getYear(),
                    PaymentType.of(requestDto.getPaymentType()),
                    requestDto.getCustomerId(),
                    false
            ) || existsByMonthOrYearOrTypeOfCustomerOrDeleted(
                    Months.of(requestDto.getMonth()),
                    requestDto.getYear(),
                    PaymentType.INSTALLMENT,
                    requestDto.getCustomerId(),
                    false
            );
        }else if(PaymentType.of(requestDto.getPaymentType()).equals(PaymentType.INSTALLMENT)){
            isValid = existsByMonthOrYearOrTypeOfCustomerOrDeleted(
                    Months.of(requestDto.getMonth()),
                    requestDto.getYear(),
                    PaymentType.of(requestDto.getPaymentType()),
                    requestDto.getCustomerId(),
                    false);
        }
        if (isValid) {
            log.error("User collection already created.");
            throw new EntityAlreadyExistException("User collection already created.");
        }

        // checking the for collection already paid or not
        if (PaymentType.of(requestDto.getPaymentType()).equals(PaymentType.MONTHLY) ||
                PaymentType.of(requestDto.getPaymentType()).equals(PaymentType.INSTALLMENT)) {
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

    @Override
    public boolean checkBillCreatorCollectionPaid(Long billCreatorId, Long connectionTypeId, Long subLocalityId, String month, int year) throws BadRequestException {
        log.info("Checking any bill creator collection paid or not.");
        if (subLocalityId!=null){
            return repo.existsByMonthAndYearAndCollectionStatusAndPaymentTypeAndCustomer_SubLocality_IdAndCustomer_ConnectionType_IdAndBillsCreator_Id(
                    Months.of(month),
                    year,
                    UserCollectionStatus.PAID,
                    PaymentType.MONTHLY,
                    subLocalityId,
                    connectionTypeId,
                    billCreatorId
            );
        }else {
            return repo.existsByMonthAndYearAndCollectionStatusAndPaymentTypeAndCustomer_ConnectionType_IdAndBillsCreator_Id(
                    Months.of(month),
                    year,
                    UserCollectionStatus.PAID,
                    PaymentType.MONTHLY,
                    connectionTypeId,
                    billCreatorId
            );
        }

    }

    @Override
    public void deleteBillCreatorUserCollections(Long billCreatorId, Long connectionTypeId, Long subLocalityId, String month, int year, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("All Bill Creator's User Collections Deletion Started.");

        List<UserCollection> userCollections = new ArrayList<>();
        if (subLocalityId!=null){
            userCollections = repo.findAllByMonthAndYearAndCollectionStatusAndPaymentTypeAndCustomer_SubLocality_IdAndCustomer_ConnectionType_IdAndBillsCreator_Id(
                    Months.of(month),
                    year,
                    UserCollectionStatus.UNPAID,
                    PaymentType.MONTHLY,
                    subLocalityId,
                    connectionTypeId,
                    billCreatorId
            );
        }else {
            userCollections = repo.findAllByMonthAndYearAndCollectionStatusAndPaymentTypeAndCustomer_ConnectionType_IdAndBillsCreator_Id(
                    Months.of(month),
                    year,
                    UserCollectionStatus.UNPAID,
                    PaymentType.MONTHLY,
                    connectionTypeId,
                    billCreatorId
            );
        }

        for(UserCollection userCollection : userCollections){
            delete(userCollection.getId(),userCollection.getCustomer().getId(),user);
        }
        log.info("All Bill Creator User Collections Deleted Successfully.");
    }

    @Override
    public Page<UserCollection> getUserCollectionByOfficerId(long officerId, int pageNo, int pageSize) throws EntityNotFoundException {
        log.info("Fetching user collection by officer id " + officerId + " .");

        Pageable paging = PageRequest.of(pageNo,pageSize);
        Page<UserCollection> userCollection = repo.findByModifiedByAndIsDeleted(officerId,false,paging);

        if (userCollection.isEmpty()) {
            log.error("User collection not found or may be deleted.");
            throw new EntityNotFoundException("User collection not found.");
        }
        return userCollection;
    }
}
