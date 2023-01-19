package com.clickpay.controller.transaction;

import com.clickpay.dto.transaction.bills_creator.BillsCreatorRequest;
import com.clickpay.errors.general.*;
import com.clickpay.model.bills_creator.BillsCreator;
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
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(ControllerConstants.BILLS_CREATOR)
public class BillsCreatorController {
    private final IAuthService authService;
    private final ITransactionService transactionService;

    @PostMapping("/")
    public ResponseEntity<Message<BillsCreator>> createBillsCreator(
            @Valid @RequestBody BillsCreatorRequest request,
            Principal principal)
            throws PermissionException, EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.BILL_CREATOR, principal);
        Message<BillsCreator> m = transactionService.createBillsCreator(request,user);
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @GetMapping("/")
    public ResponseEntity<Message<List<BillsCreator>>> getAllBillsCreator(Principal principal)
            throws PermissionException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        System.out.println();
        User user = authService.hasPermission(ControllerConstants.BILL_CREATOR, principal);
        Message<List<BillsCreator>> m = transactionService.getAllBillCreatorsByUserId(user.getId());
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @DeleteMapping("/")
    public ResponseEntity<Message<BillsCreator>> deleteBillsCreator(
            @Valid @RequestParam Long billsCreatorId,
            Principal principal)
            throws PermissionException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        System.out.println();
        User user = authService.hasPermission(ControllerConstants.BILL_CREATOR, principal);
        Message<BillsCreator> m = transactionService.deleteBillCreator(billsCreatorId,user);
        return ResponseEntity.status(m.getStatus()).body(m);
    }
}
