package com.clickpay.controller.recovery_officer;

import com.clickpay.dto.recovery_officer.area_allocation.AreaAllocationRequest;
import com.clickpay.dto.recovery_officer.officer.*;
import com.clickpay.errors.general.*;
import com.clickpay.model.area.City;
import com.clickpay.model.user.User;
import com.clickpay.service.auth.IAuthService;
import com.clickpay.service.recovery_officer.IRecoveryOfficerService;
import com.clickpay.utils.ControllerConstants;
import com.clickpay.utils.Message;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping(ControllerConstants.RECOVERY_OFFICER)
public class RecoveryOfficerController {

    @Value("${full.domain.name}")
    private String DOMAIN_URL;

    private final IAuthService authService;
    private final IRecoveryOfficerService recoveryOfficerService;

    public RecoveryOfficerController(final IAuthService authService, IRecoveryOfficerService recoveryOfficerService) {
        this.authService = authService;
        this.recoveryOfficerService = recoveryOfficerService;
    }

    /**
     * For officer CRUD
     */
    @PostMapping("/officer")
    @ApiOperation(
            value = "Create officer with officer values",
            notes = "Officers should have unique username and email.",
            produces = "application/json",
            response = ResponseEntity.class)
    public ResponseEntity createOfficer(
            @Valid @RequestBody OfficerRequest request,
            Principal principal)
            throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.RECOVERY_OFFICER_SUB, principal);
        Message<OfficerResponse> m = recoveryOfficerService.createOfficer(request, user);
        return ResponseEntity
                .created(
                        URI.create(DOMAIN_URL + "/" + ControllerConstants.RECOVERY_OFFICER + "/officer/" + m.getData().getId())
                ).body(m);
    }

    @GetMapping("/officer/{id}")
    public ResponseEntity getOfficer(@NotNull @PathVariable("id") Long id,
                                  Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        authService.hasPermission(ControllerConstants.RECOVERY_OFFICER_SUB, principal);
        Message m = recoveryOfficerService.findOfficerById(id);
        return ResponseEntity.ok().body(m);
    }

    @PostMapping("/officer/getAll")
    public ResponseEntity<Message<PaginatedOfficerResponse>> getAllOfficer(
            @Valid @RequestParam(required = false) String status,
            @Valid @RequestParam Integer pageNo,
            @Valid @RequestParam Integer pageSize,
            Principal principal)
            throws EntityNotFoundException, PermissionException, BadRequestException {
        System.out.println();
        User user = authService.hasPermission(ControllerConstants.RECOVERY_OFFICER_SUB, principal);
        Message<PaginatedOfficerResponse> m = recoveryOfficerService.findAllOfficerByUserId(status, pageNo, pageSize,user.getId());
        return ResponseEntity.ok().body(m);
    }

    @PutMapping("/officer")
    public ResponseEntity updateOfficer(@Valid @RequestBody OfficerUpdateRequest request,
                                     Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.RECOVERY_OFFICER_SUB, principal);
        Message m = recoveryOfficerService.updateOfficer(request, user);
        return ResponseEntity.ok().body(m);
    }

    @PostMapping("/officer-area")
    @ApiOperation(
            value = "Allocate area (sub-localities) to the officer.",
            produces = "application/json",
            response = ResponseEntity.class)
    public ResponseEntity createOfficerArea(
            @Valid @RequestBody AreaAllocationRequest request,
            Principal principal)
            throws EntityNotFoundException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.AREA_ALLOCATION, principal);
        Message m = recoveryOfficerService.createOfficerArea(request, user);
        return ResponseEntity
                .created(
                        URI.create(DOMAIN_URL + "/" + ControllerConstants.RECOVERY_OFFICER + "/officer-area/" + request.getUserId())
                ).body(m);
    }

    @GetMapping("/officer-area/{id}")
    public ResponseEntity getOfficerArea(@NotNull @PathVariable("id") Long userId,
                                     Principal principal)
            throws EntityNotFoundException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.AREA_ALLOCATION, principal);
        Message m = recoveryOfficerService.findAreaAllocatedByUserId(userId, user);
        return ResponseEntity.ok().body(m);
    }
}
