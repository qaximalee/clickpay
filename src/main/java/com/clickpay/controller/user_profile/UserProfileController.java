package com.clickpay.controller.user_profile;

import com.clickpay.dto.user_profile.customer.PaginatedCustomersInUserCollectionRequest;
import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationRequest;
import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationResponse;
import com.clickpay.dto.user_profile.customer.CustomerRequest;
import com.clickpay.dto.user_profile.packages.PackageRequest;
import com.clickpay.errors.general.*;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.BoxMedia;
import com.clickpay.model.user_profile.Customer;
import com.clickpay.model.user_profile.Package;
import com.clickpay.service.auth.IAuthService;
import com.clickpay.service.company.ICompanyService;
import com.clickpay.service.connection_type.IConnectionTypeService;
import com.clickpay.service.user_profile.IUserProfileService;
import com.clickpay.service.user_profile.customer.ICustomerService;
import com.clickpay.utils.ControllerConstants;
import com.clickpay.utils.Message;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.security.Principal;


@RestController("USER_PROFILE")
@RequestMapping(ControllerConstants.USER_PROFILE)
public class UserProfileController extends CompanyController {

    private final IAuthService authService;
    private final IUserProfileService userProfileService;
    private final ICustomerService customerService;
    private ICompanyService companyService;
    private IConnectionTypeService connectionTypeService;

    @Autowired
    public UserProfileController(final IAuthService authService,
                                 final ICustomerService customerService,
                                 final IUserProfileService userProfileService,
                                 ICompanyService companyService,
                                 IConnectionTypeService connectionTypeService) {
        super(authService, companyService, connectionTypeService, userProfileService);
        this.authService = authService;
        this.customerService = customerService;
        this.userProfileService = userProfileService;
    }

    /**
     * CRUD for box media
     */

    @PostMapping("/box-media")
    @ApiOperation(
            value = "Create box media with box number and nearby location.",
            produces = "application/json",
            response = ResponseEntity.class)
    public ResponseEntity createBoxMedia(
            @ApiParam(
                    value = "Box number should not be null or empty.",
                    required = true
            ) @NotNull @NotBlank @RequestParam("boxNumber") String boxNumber,
            @ApiParam(
                    value = "You can give a nearby location.",
                    required = false
            ) @NotNull @NotBlank @RequestParam("nearbyLocation") String nearbyLocation,
            Principal principal)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.BOX_MEDIA, principal);
        Message<BoxMedia> m = userProfileService.createBoxMedia(boxNumber, nearbyLocation, user);
        return ResponseEntity
                .created(
                        URI.create(DOMAIN_URL + "/" + ControllerConstants.USER_PROFILE + "/box-media/" + m.getData().getId())
                ).body(m);
    }

    @GetMapping("/box-media/{id}")  
    public ResponseEntity getBoxMedia(@NotNull @PathVariable("id") Long id,
                                      Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        authService.hasPermission(ControllerConstants.BOX_MEDIA, principal);
        Message m = userProfileService.findBoxMediaById(id);
        return ResponseEntity.ok().body(m);
    }

    @GetMapping("/box-media")
    public ResponseEntity getAllBoxMedia(Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.BOX_MEDIA, principal);
        Message m = userProfileService.findAllBoxMediaByUserId(user.getId());
        return ResponseEntity.ok().body(m);
    }

    @PutMapping("/box-media")
    public ResponseEntity updateBoxMedia(@NotNull @RequestParam("boxNumber") String boxNumber,
                                         @RequestParam("nearbyLocation") String nearbyLocation,
                                         @NotNull @RequestParam("boxMediaId") Long boxMediaId,
                                         Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.BOX_MEDIA, principal);
        Message m = userProfileService.updateBoxMedia(boxMediaId, boxNumber, nearbyLocation, user);
        return ResponseEntity.ok().body(m);
    }

    @DeleteMapping("/box-media/delete")
    public ResponseEntity<Message<BoxMedia>> deleteBoxMedia(@NotNull @RequestParam("boxMediaId") Long boxMediaId,
                                         Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.BOX_MEDIA, principal);
        Message<BoxMedia> m = userProfileService.deleteBoxMedia(boxMediaId, user);
        return ResponseEntity.ok().body(m);
    }


    /**
     * CRUD for packages
     */

    @PostMapping("/package")
    @ApiOperation(
            value = "Create package with package request body.",
            produces = "application/json",
            response = ResponseEntity.class)
    public ResponseEntity createPackage(
            @Valid @RequestBody PackageRequest dto,
            Principal principal)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message<Package> m = userProfileService.createPackage(dto, user);
        return ResponseEntity
                .created(
                        URI.create(DOMAIN_URL + "/" + ControllerConstants.USER_PROFILE + "/package/" + m.getData().getId())
                ).body(m);
    }

    @GetMapping("/package/{id}")
    public ResponseEntity getPackage(@NotNull @PathVariable("id") Long id,
                                     Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message m = userProfileService.findPackageById(id);
        return ResponseEntity.ok().body(m);
    }

    @GetMapping("/package")
    public ResponseEntity getAllPackage(Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message m = userProfileService.findAllPackageByUserId(user.getId());
        return ResponseEntity.ok().body(m);
    }

    @PutMapping("/package")
    public ResponseEntity updatePackage(@Valid @RequestBody PackageRequest dto,
                                        Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message m = userProfileService.updatePackage(dto, user);
        return ResponseEntity.ok().body(m);
    }

    @DeleteMapping("/package/delete")
    public ResponseEntity<Message<Package>> deletePackage(@NotNull @RequestParam("id") Long id,
                                        Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message<Package> m = userProfileService.deletePackage(id, user);
        return ResponseEntity.ok().body(m);
    }


    /**
     * CRUD for customer
     */

    @PostMapping("/user-details")
    public ResponseEntity createCustomer(@Valid @RequestBody CustomerRequest dto, Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException, EntityAlreadyExistException {
        User user = authService.hasPermission(ControllerConstants.USER_DETAILS, principal);
        Message<Customer> m = userProfileService.createCustomer(dto, user);
        return ResponseEntity.created(
                URI.create(DOMAIN_URL + "/" + ControllerConstants.USER_PROFILE + "/user-details/" + m.getData().getId())
        ).body(m);
    }

    @GetMapping("/user-details/{id}")
    public ResponseEntity getCustomer(@NotNull @PathVariable("id") Long id,
                                      Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        authService.hasPermission(ControllerConstants.USER_DETAILS, principal);
        Message m = userProfileService.findCustomerById(id);
        return ResponseEntity.ok().body(m);
    }

    @GetMapping("/user-details")
    public ResponseEntity getAllCustomer(Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.USER_DETAILS, principal);
        Message m = userProfileService.findAllCustomerByUserId(user.getId());
        return ResponseEntity.ok().body(m);
    }

    @PostMapping("/user-details/filter")
    public ResponseEntity getAllCustomerByFiltration(@RequestBody CustomerFilterAndPaginationRequest customerFilterDTO, Principal principal)
            throws PermissionException, EntityNotFoundException {
        User user = authService.hasPermission(ControllerConstants.USER_DETAILS, principal);
        Message m = userProfileService.findCustomerByFilter(customerFilterDTO, user);
        return ResponseEntity.ok().body(m);
    }


    @PostMapping("/customer-details/filter")
    public ResponseEntity<Message<CustomerFilterAndPaginationResponse>> getAllCustomerByFiltrationWithUserCollection(
            @RequestBody PaginatedCustomersInUserCollectionRequest request,
            Principal principal)
            throws PermissionException, EntityNotFoundException {
        User user = authService.hasPermission(ControllerConstants.USER_DETAILS, principal);
        Message<CustomerFilterAndPaginationResponse> m = userProfileService.getAllCustomersByUserCollections(request, user);
        return ResponseEntity.ok().body(m);
    }
}
