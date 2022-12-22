package com.clickpay.controller.transaction;

import com.clickpay.dto.transaction.UserCollectionRequest;
import com.clickpay.dto.transaction.UserCollectionResponse;
import com.clickpay.errors.general.*;
import com.clickpay.model.user.User;
import com.clickpay.service.auth.IAuthService;
import com.clickpay.service.transaction.ITransactionService;
import com.clickpay.utils.ControllerConstants;
import com.clickpay.utils.Message;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping(ControllerConstants.TRANSACTION)
public class UserCollectionController {

    @Value("${full.domain.name}")
    private String DOMAIN_URL;

    private final IAuthService authService;
    private final ITransactionService transactionService;

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

    @PostMapping("/")
    public ResponseEntity<Message<UserCollectionResponse>> createUserCollection(
            @Valid @RequestBody UserCollectionRequest request,
            Principal principal)
            throws PermissionException, EntityNotFoundException, BadRequestException, EntityNotSavedException, EntityAlreadyExistException {
        User user = authService.hasPermission(ControllerConstants.USERS_COLLECTIONS, principal);
        Message<UserCollectionResponse> m = transactionService.createUserCollection(request,user);
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @GetMapping("/id")
    public ResponseEntity<Message<UserCollectionResponse>> getUserCollection(
            @Valid @NotNull @RequestParam Long collectionId,
            @Valid @NotNull @RequestParam Long customerId,
            Principal principal)
            throws PermissionException, EntityNotFoundException {
        User user = authService.hasPermission(ControllerConstants.USERS_COLLECTIONS, principal);
        Message<UserCollectionResponse> m = transactionService.getUserCollection(collectionId,customerId,user);
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @PutMapping("/delete")
    public ResponseEntity<Message<UserCollectionResponse>> deleteUserCollection(
            @Valid @NotNull @RequestParam Long collectionId,
            @Valid @NotNull @RequestParam Long customerId,
            Principal principal)
            throws PermissionException, EntityNotFoundException, EntityAlreadyExistException, BadRequestException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.USERS_COLLECTIONS, principal);
        Message<UserCollectionResponse> m = transactionService.deleteUserCollection(collectionId,customerId,user);
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @PutMapping("/")
    public ResponseEntity<Message<UserCollectionResponse>> updateUserCollectionStatus(
            @Valid @NotNull @RequestParam String status,
            @Valid @NotNull @RequestParam Long collectionId,
            @Valid @NotNull @RequestParam Long customerId,
            Principal principal)
            throws PermissionException, EntityNotFoundException, BadRequestException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.USERS_COLLECTIONS, principal);
        Message<UserCollectionResponse> m = transactionService.updateUserCollectionStatus(status,collectionId,customerId,user);
        return ResponseEntity.status(m.getStatus()).body(m);
    }

//    @GetMapping("/find-customers")
//    public ResponseEntity<Message<PaginatedUserCollectionResponse>> getCustomersByFindingFields(
//            @Valid @NotNull @RequestBody PaginatedUserCollectionRequest request,
//            Principal principal)
//            throws PermissionException {
//        User user = authService.hasPermission(ControllerConstants.USERS_COLLECTIONS, principal);
//        Message<PaginatedUserCollectionResponse> m = transactionService.getAllUserOfCollections(request,user);
//        return ResponseEntity.status(m.getStatus()).body(m);
//    }
}
