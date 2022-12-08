package com.clickpay.controller.transaction;

import com.clickpay.dto.transaction.UnpaidCollectionResponse;
import com.clickpay.dto.transaction.UserCollectionRequest;
import com.clickpay.errors.general.*;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.model.user.User;
import com.clickpay.service.auth.IAuthService;
import com.clickpay.service.recovery_officer.IRecoveryOfficerService;
import com.clickpay.service.transaction.user_collection.IUserCollectionService;
import com.clickpay.utils.ControllerConstants;
import com.clickpay.utils.Message;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private final IUserCollectionService service;

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

    @GetMapping("/unpaid-collections")
    public ResponseEntity<Message<UnpaidCollectionResponse>> gettingUnpaidCollections(
            @Valid @RequestParam Long userId,
            Principal principal) throws PermissionException, EntityNotFoundException {
        User user = authService.hasPermission(ControllerConstants.USERS_COLLECTIONS, principal);
        Message<UnpaidCollectionResponse> m = service.getUserUnpaidCollections(userId);
        return ResponseEntity.status(m.getStatus()).body(m);
    }


    @PostMapping("/")
    public ResponseEntity<Message<UserCollection>> createUserCollection(
            @Valid @RequestBody UserCollectionRequest request,
            Principal principal)
            throws PermissionException, EntityNotFoundException, BadRequestException, EntityNotSavedException, EntityAlreadyExistException {
        User user = authService.hasPermission(ControllerConstants.USERS_COLLECTIONS, principal);
        Message<UserCollection> m = service.createUserCollection(request,user);
        return ResponseEntity.status(m.getStatus()).body(m);
    }
}
