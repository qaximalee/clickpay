package com.clickpay.service.recovery_officer.area_allocation;

import com.clickpay.dto.recovery_officer.area_allocation.AreaAllocationRequest;
import com.clickpay.dto.recovery_officer.area_allocation.AreaAllocationResponse;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.user.User;

public interface IAreaAllocationService {
    void createOfficerArea(AreaAllocationRequest request, User loggedInUser) throws EntityNotFoundException;

    AreaAllocationResponse getAllAreaAllocationsByUserId(Long userId, User loggedInUser) throws EntityNotFoundException;
}
