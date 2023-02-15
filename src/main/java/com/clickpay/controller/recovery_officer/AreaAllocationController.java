package com.clickpay.controller.recovery_officer;

import com.clickpay.dto.recovery_officer.area_allocation.AreaAllocationRequest;
import com.clickpay.dto.recovery_officer.area_allocation.AreaAllocationResponse;
import com.clickpay.dto.recovery_officer.officer.OfficerRequest;
import com.clickpay.dto.recovery_officer.officer.OfficerResponse;
import com.clickpay.errors.general.*;
import com.clickpay.model.user.User;
import com.clickpay.service.auth.IAuthService;
import com.clickpay.service.recovery_officer.IRecoveryOfficerService;
import com.clickpay.utils.ControllerConstants;
import com.clickpay.utils.Message;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping(ControllerConstants.AREA_ALLOCATION)
@RequiredArgsConstructor
public class AreaAllocationController {

    @Value("${full.domain.name}")
    private String DOMAIN_URL;

    public final IRecoveryOfficerService recoveryOfficerService;
    public final IAuthService authService;

    /**
     * For officer CRUD
     */
    @PostMapping("/")
    @ApiOperation(
            value = "Allocate area (sub-localities) to the officer.",
            produces = "application/json",
            response = ResponseEntity.class)
    public ResponseEntity createOfficer(
            @Valid @RequestBody AreaAllocationRequest request,
            Principal principal)
            throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.AREA_ALLOCATION, principal);
        Message m = recoveryOfficerService.createOfficerArea(request, user);
        return ResponseEntity
                .created(
                        URI.create(DOMAIN_URL + "/" + ControllerConstants.AREA_ALLOCATION + "/" + request.getUserId())
                ).body(m);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOfficer(@NotNull @PathVariable("id") Long userId,
                                     Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.AREA_ALLOCATION, principal);
        Message m = recoveryOfficerService.findAreaAllocatedByUserId(userId, user);
        return ResponseEntity.ok().body(m);
    }
}
