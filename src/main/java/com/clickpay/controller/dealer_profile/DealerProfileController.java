package com.clickpay.controller.dealer_profile;

import com.clickpay.dto.dealer_profile.dealer_detail.DealerDetailRequest;
import com.clickpay.dto.dealer_profile.dealer_detail.DealerDetailResponse;
import com.clickpay.dto.recovery_officer.officer.OfficerUpdateRequest;
import com.clickpay.errors.general.*;
import com.clickpay.model.area.City;
import com.clickpay.model.dealer_profile.dealer_detail.Dealer;
import com.clickpay.model.user.User;
import com.clickpay.service.auth.IAuthService;
import com.clickpay.service.dealer_profile.IDealerProfileService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping(ControllerConstants.DEALERS)
public class DealerProfileController {

    @Value("${full.domain.name}")
    private String DOMAIN_URL;

    private final IAuthService authService;
    private final IDealerProfileService service;

    @PostMapping("/create")
    @ApiOperation(
            value = "Create dealer with requested parameters",
            produces = "application/json",
            response = ResponseEntity.class)
    public ResponseEntity createDealer(
            @Valid @RequestBody DealerDetailRequest dto,
            Principal principal)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException,
            PermissionException, EntityAlreadyExistException {
        User user = authService.hasPermission(ControllerConstants.DEALER_DETAILS, principal);
        Message<Dealer> m = service.createDealer(dto, user);
        return ResponseEntity
                .created(
                        URI.create(DOMAIN_URL + "/" + ControllerConstants.DEALERS + "/detail/" + m.getData().getId())
                ).body(m);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity getDealerDetail(@NotNull @PathVariable("id") Long id,
                                     Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        authService.hasPermission(ControllerConstants.DEALER_DETAILS, principal);
        Message m = service.findDealerById(id);
        return ResponseEntity.ok().body(m);
    }

    @GetMapping("/")
    public ResponseEntity getAllDealers(Principal principal)
            throws EntityNotFoundException, PermissionException {
        System.out.println();
        User user = authService.hasPermission(ControllerConstants.DEALER_DETAILS, principal);
        Message m = service.findAllDealerByUserId(user.getId());
        return ResponseEntity.ok().body(m);
    }

    @PutMapping("/")
    public ResponseEntity updateDealer(@Valid @RequestBody DealerDetailRequest request,
                                        Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.DEALER_DETAILS, principal);
        Message m = service.updateDealer(request, user);
        return ResponseEntity.ok().body(m);
    }

    @PutMapping("/update-status")
    public ResponseEntity<Message<Dealer>> updateDealerStatus(@Valid @RequestParam("dealerId") Long dealerId,
                                             @Valid @RequestParam("status") String status,
                                       Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.DEALER_DETAILS, principal);
        Message<Dealer> m = service.updateDealerStatus(dealerId, status, user);
        return ResponseEntity.ok().body(m);
    }

    @PutMapping("/delete")
    public ResponseEntity<Message<Dealer>> deleteDealer(@Valid @RequestParam("dealerId") Long dealerId,
                                                              Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.DEALER_DETAILS, principal);
        Message<Dealer> m = service.deleteDealer(dealerId, user);
        return ResponseEntity.ok().body(m);
    }

}
