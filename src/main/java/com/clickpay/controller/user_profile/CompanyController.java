package com.clickpay.controller.user_profile;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.company.Company;
import com.clickpay.model.user.User;
import com.clickpay.service.auth.IAuthService;
import com.clickpay.service.company.ICompanyService;
import com.clickpay.service.connection_type.IConnectionTypeService;
import com.clickpay.service.user_profile.IUserProfileService;
import com.clickpay.utils.ControllerConstants;
import com.clickpay.utils.EnvironmentVariables;
import com.clickpay.utils.Message;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.security.Principal;

@RestController("COMPANY")
@RequestMapping("c")
public class CompanyController extends ConnectionTypeController{

    private final IAuthService authService;
    private final ICompanyService companyService;
    private final IUserProfileService userProfileService;

    @Autowired
    public CompanyController(final IAuthService authService,
                             final ICompanyService companyService,
                             IConnectionTypeService connectionTypeService,
                             final IUserProfileService userProfileService) {
        super(authService, connectionTypeService, userProfileService);
        this.authService = authService;
        this.companyService = companyService;
        this.userProfileService = userProfileService;
    }

    @PostMapping("/company")
    @ApiOperation(
            value = "Create company with company name.",
            produces = "application/json",
            response = ResponseEntity.class)
    public ResponseEntity createCompany(
            @NotBlank @RequestParam("name") String name,
            Principal principal)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message<Company> m = userProfileService.createCompany(name,  user);
        return ResponseEntity
                .created(
                        URI.create(EnvironmentVariables.SERVER_DOMAIN + "/" + ControllerConstants.PACKAGE + "/company/" + m.getData().getId())
                ).body(m);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity getCompany(@NotNull @PathVariable("id") Long id,
                                     Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message m = userProfileService.findCompanyById(id);
        return ResponseEntity.ok().body(m);
    }

    @GetMapping("/company")
    public ResponseEntity getAllCompany(Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message m = userProfileService.findAllCompaniesByUserId(user.getId());
        return ResponseEntity.ok().body(m);
    }

    @PutMapping("/company")
    public ResponseEntity updateCompany(@NotBlank @RequestParam("name") String name,
                                        @NotNull @RequestParam("id") Long id,
                                        Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message m = userProfileService.updateCompany(id, name, user);
        return ResponseEntity.ok().body(m);
    }
}
