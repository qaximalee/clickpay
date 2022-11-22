package com.clickpay.controller.transaction;

import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.user.User;
import com.clickpay.service.auth.IAuthService;
import com.clickpay.service.recovery_officer.IRecoveryOfficerService;
import com.clickpay.utils.ControllerConstants;
import com.clickpay.utils.Message;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping(ControllerConstants.TRANSACTION)
public class UserCollectionController {

    @Value("${full.domain.name}")
    private String DOMAIN_URL;

    private final IAuthService authService;
    private final IRecoveryOfficerService recoveryOfficerService;

    // Collect fee from user
    @PostMapping("/user-collect")
    @ApiOperation(
            value = "Create box media with box number and nearby location.",
            produces = "application/json",
            response = ResponseEntity.class)
    public ResponseEntity collect(Principal principal) throws PermissionException {
        User user = authService.hasPermission(ControllerConstants.USERS_COLLECTIONS, principal);
        Message m = new Message();
        return ResponseEntity
                .created(
                        URI.create(DOMAIN_URL + "/" + ControllerConstants.TRANSACTION + "/user-collect/" + 1 /*m.getData().getId()*/)
                ).body(m);
    }
}
