package com.clickpay.controller.user_profile;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.connection_type.ConnectionType;
import com.clickpay.model.user.User;
import com.clickpay.service.auth.IAuthService;
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

@RestController("CONNECTION_TYPE")
@RequestMapping("ct")
public class ConnectionTypeController {

    private final IAuthService authService;
    private final IConnectionTypeService connectionTypeService;
    private final IUserProfileService userProfileService;

    @Autowired
    public ConnectionTypeController(final IAuthService authService,
                             final IConnectionTypeService connectionTypeService,
                             final IUserProfileService userProfileService) {
        this.authService = authService;
        this.connectionTypeService = connectionTypeService;
        this.userProfileService = userProfileService;
    }

    @PostMapping("/connection-type")
    @ApiOperation(
            value = "Create connection type with type.",
            produces = "application/json",
            response = ResponseEntity.class)
    public ResponseEntity createConnectionType(
            @NotBlank @RequestParam("type") String type,
            Principal principal)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.USER_PROFILE, principal);
        Message<ConnectionType> m = userProfileService.createConnectionType(type,  user);
        return ResponseEntity
                .created(
                        URI.create(EnvironmentVariables.SERVER_DOMAIN + "/" + ControllerConstants.USER_PROFILE + "/connection-type/" + m.getData().getId())
                ).body(m);
    }

    @GetMapping("/connection-type/{id}")
    public ResponseEntity getConnectionType(@NotNull @PathVariable("id") Long id,
                                     Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message m = userProfileService.findConnectionTypeById(id);
        return ResponseEntity.ok().body(m);
    }

    @GetMapping("/connection-type")
    public ResponseEntity getAllConnectionType(Principal principal)
            throws EntityNotFoundException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message m = userProfileService.findAllConnectionTypeByUserId(user.getId());
        return ResponseEntity.ok().body(m);
    }

    @PutMapping("/connection-type")
    public ResponseEntity updateConnectionType(@NotBlank @RequestParam("type") String type,
                                        @NotNull @RequestParam("id") Long id,
                                        Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.PACKAGE, principal);
        Message m = userProfileService.updateConnectionType(id, type, user);
        return ResponseEntity.ok().body(m);
    }
}
