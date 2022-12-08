package com.clickpay.service.transaction.user_collection;

import com.clickpay.dto.transaction.UnpaidCollectionResponse;
import com.clickpay.dto.transaction.UnpaidCollections;
import com.clickpay.dto.transaction.UserCollectionRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.Customer;
import com.clickpay.repository.transaction.user_collection.UserCollectionRepository;
import com.clickpay.service.user_profile.customer.ICustomerService;
import com.clickpay.utils.Message;
import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCollectionService implements IUserCollectionService {

    private final ICustomerService customerService;
    private final UserCollectionRepository repo;

    @Override
    public Message<UnpaidCollectionResponse> getUserUnpaidCollections(Long userId) throws EntityNotFoundException {
        log.info("Getting unpaid collection of user id: " + userId);

        Customer customer = customerService.findAllCustomerByUserId(userId);

        Date userCreatedDate = customer.getUser().getCreationDate();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = userCreatedDate.toInstant();
        LocalDate userCreatedLocalDate = instant.atZone(defaultZoneId).toLocalDate();
        int startMonth = userCreatedLocalDate.getMonthValue();
        LocalDate currentMonth = LocalDate.now();
        int endMonth = currentMonth.getMonthValue();
        int numMonths = endMonth - startMonth;

        List<UnpaidCollections> unpaidCollections = new ArrayList<>();

        for (int i = userCreatedLocalDate.getDayOfMonth() < 16 ? 0 : 1; i <= numMonths; i++) {

            //Converting the Date to LocalDate
            LocalDate localDate = userCreatedLocalDate.plusMonths(i);

            Month month = localDate.getMonth();
            int year = localDate.getYear();

            UnpaidCollections unpaidCollection = new UnpaidCollections();
            unpaidCollection.setBalanceAmount(customer.getAmount());
            unpaidCollection.setMonthAndYear(month + " " + year);
            unpaidCollection.setCollectionStatus(UserCollectionStatus.UNPAID);
            unpaidCollection.setPaymentType(PaymentType.MONTHLY);
            unpaidCollections.add(unpaidCollection);
        }

        UnpaidCollectionResponse response = UnpaidCollectionResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .internetId(customer.getInternetId())
                .connectionType(customer.getConnectionType().getType())
                .unpaidCollections(unpaidCollections)
                .build();

        return new Message<UnpaidCollectionResponse>()
                .setCode(HttpStatus.OK.toString())
                .setStatus(HttpStatus.OK.value())
                .setMessage("Getting unpaid collection of user successfully.")
                .setData(response);
    }

    @Transactional
    @Override
    public Message<UserCollection> createUserCollection(UserCollectionRequest requestDto, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException {

        boolean isValid = false;
        //checking requested collection is valid or not
        if (PaymentType.of(requestDto.getPaymentType()).equals(PaymentType.MONTHLY)){
            isValid = existsByMonthOrYearOrTypeOfCustomer(
                    requestDto.getMonth(),
                    requestDto.getYear(),
                    PaymentType.of(requestDto.getPaymentType()),
                    requestDto.getCustomerId()
            );
        }

        if (isValid){
            log.error("User collection already created.");
            throw new EntityAlreadyExistException("User collection already created.");
        }

        //validate customer existing
        Customer customer = customerService.findById(requestDto.getCustomerId());

        UserCollectionStatus userCollectionStatus = UserCollectionStatus.of(requestDto.getCollectionStatus());
        PaymentType paymentType = PaymentType.of(requestDto.getPaymentType());

        log.info("Populate user collection data.");
        UserCollection userCollection = new UserCollection();
        userCollection.setCustomer(customer);
        userCollection.setCollectionStatus(userCollectionStatus);
        userCollection.setAmount(requestDto.getAmount());
        userCollection.setPaymentType(paymentType);
        userCollection.setMonth(requestDto.getMonth());
        userCollection.setYear(requestDto.getYear());
        userCollection.setDeleted(false);

        // set audits fields
        userCollection.setCreatedBy(user.getId());
        userCollection.setCreationDate(new Date());

        return new Message<UserCollection>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("User Collection Created Successfilly.")
                .setData(save(userCollection));

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

    @Override
    public boolean existsByMonthOrYearOrTypeOfCustomer(String month, Integer year, PaymentType paymentType, Long customerId) {
        return repo.existsByMonthAndYearAndPaymentTypeAndCustomer_Id(month, year, paymentType, customerId);
    }

}
