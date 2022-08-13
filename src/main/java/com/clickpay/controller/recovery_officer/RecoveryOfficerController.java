package com.clickpay.controller.recovery_officer;

import com.clickpay.service.auth.IAuthService;
import com.clickpay.utils.ControllerConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerConstants.RECOVERY_OFFICER)
public class RecoveryOfficerController {

    private final IAuthService authService;

    public RecoveryOfficerController(final IAuthService authService) {
        this.authService = authService;
    }


}
