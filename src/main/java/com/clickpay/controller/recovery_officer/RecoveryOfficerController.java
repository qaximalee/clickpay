package com.clickpay.controller.recovery_officer;

import com.clickpay.dto.recovery_officer.officer.OfficerRequest;
import com.clickpay.dto.recovery_officer.officer.OfficerResponse;
import com.clickpay.dto.recovery_officer.officer.OfficerUpdateRequest;
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

    @GetMapping("/officer")
    public ResponseEntity getAllOfficer(Principal principal)
            throws EntityNotFoundException, PermissionException {
        System.out.println();
        User user = authService.hasPermission(ControllerConstants.RECOVERY_OFFICER_SUB, principal);
        Message m = recoveryOfficerService.findAllOfficerByUserId(user.getId());
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
}
