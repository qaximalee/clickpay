package com.clickpay.dto.recovery_officer.recovery_officer_collection;

import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.user_profile.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RecoveryOfficerCustomerDropdownDto {

    private Long id;
    private String name;
    private Long subLocalityId;

    public static List<RecoveryOfficerCustomerDropdownDto> fromCustomerOfRecoveryOfficer(List<Object[]> data) throws EntityNotFoundException {
        if(CollectionUtils.isEmpty(data))
            throw new EntityNotFoundException("No customer found for this recovery officer.");
        return data.stream().map(e -> {
            RecoveryOfficerCustomerDropdownDto customer = new RecoveryOfficerCustomerDropdownDto();
            customer.setId(Long.parseLong(""+e[0]));
            customer.setName(""+e[1]);
            customer.setSubLocalityId(Long.parseLong(""+e[2]));
            return customer;
        }).collect(Collectors.toList());
    }
}
