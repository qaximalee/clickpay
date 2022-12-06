package com.clickpay.service.transaction.user_collection;

import com.clickpay.dto.transaction.UnpaidCollectionResponse;
import com.clickpay.dto.transaction.UnpaidCollections;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.user_profile.Customer;
import com.clickpay.service.user_profile.customer.ICustomerService;
import com.clickpay.utils.Message;
import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.management.MonitorInfo;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCollectionService implements IUserCollectionService {

    private final ICustomerService customerService;

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

        for (int i = userCreatedLocalDate.getDayOfMonth()<16?0:1; i <= numMonths; i++) {

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

}
