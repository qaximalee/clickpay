package com.clickpay.controller.transaction;

import com.clickpay.dto.transaction.bills_creator.BillsCreatorCreateRequest;
import com.clickpay.dto.transaction.bills_creator.BillsCreatorDeleteRequest;
import com.clickpay.dto.transaction.bills_creator.PaginatedBillsCreatorResponse;
import com.clickpay.errors.general.*;
import com.clickpay.model.transaction.BillsCreator;
import com.clickpay.model.user.User;
import com.clickpay.service.auth.IAuthService;
import com.clickpay.service.transaction.ITransactionService;
import com.clickpay.utils.ControllerConstants;
import com.clickpay.utils.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping(ControllerConstants.BILLS_CREATOR)
public class BillsCreatorController {
    private final IAuthService authService;
    private final ITransactionService transactionService;

    @PostMapping("/")
    public ResponseEntity<Message<BillsCreator>> createBillsCreator(
            @Valid @RequestBody BillsCreatorCreateRequest request,
            Principal principal)
            throws PermissionException, EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.BILL_CREATOR, principal);
        Message<BillsCreator> m = transactionService.createBillsCreator(request,user);
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @GetMapping("/")
    public ResponseEntity<Message<PaginatedBillsCreatorResponse>> getAllBillsCreator(
            @Valid @RequestParam int pageNo,
            @Valid @RequestParam int pageSize,
            Principal principal
    )
            throws PermissionException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        System.out.println();
        User user = authService.hasPermission(ControllerConstants.BILL_CREATOR, principal);
        Message<PaginatedBillsCreatorResponse> m = transactionService.getAllBillCreatorsByUserId(user.getId(),pageNo,pageSize);
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @PostMapping("/delete")
    public ResponseEntity<Message<BillsCreator>> deleteBillsCreator(
            @Valid @RequestBody BillsCreatorDeleteRequest request,
            Principal principal)
            throws PermissionException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        System.out.println();
        User user = authService.hasPermission(ControllerConstants.BILL_CREATOR, principal);
        Message<BillsCreator> m = transactionService.deleteBillCreator(request,user);
        return ResponseEntity.status(m.getStatus()).body(m);
    }
}
