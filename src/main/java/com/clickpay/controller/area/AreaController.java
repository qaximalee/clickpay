package com.clickpay.controller.area;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.area.City;
import com.clickpay.model.area.Locality;
import com.clickpay.model.area.SubLocality;
import com.clickpay.model.user.User;
import com.clickpay.service.area.IAreaService;
import com.clickpay.service.auth.IAuthService;
import com.clickpay.utils.ResponseMessage;
import com.clickpay.utils.Message;
import com.clickpay.utils.ControllerConstants;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping(ControllerConstants.AREA)
public class AreaController {

    @Value("${full.domain.name}")
    private String DOMAIN_URL;

    private static final Long COUNTRY_ID = 1L;

    private final IAreaService service;
    private final IAuthService authService;

    @Autowired
    public AreaController(final IAreaService service,
                          final IAuthService authService) {
        this.service = service;
        this.authService = authService;
    }

    /**
     * For City CRUD
     */
    @PostMapping("/city")
    @ApiOperation(
            value = "Create city with city name",
            notes = "City should be in Pakistan.",
            produces = "application/json",
            response = ResponseEntity.class)
    public ResponseEntity createCity(
            @ApiParam(
                    value = "City name should not be null or empty.",
                    required = true
            ) @NotNull @NotBlank @RequestParam("cityName") String name,
            Principal principal)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.CITY, principal);
        Message<City> m = service.createCity(name, COUNTRY_ID, user);
        return ResponseEntity
                .created(
                        URI.create(DOMAIN_URL + "/" + ControllerConstants.AREA + "/city/" + m.getData().getId())
                ).body(m);
    }

    @GetMapping("/city/{id}")
    public ResponseEntity getCity(@NotNull @PathVariable("id") Long id,
                                  Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        authService.hasPermission(ControllerConstants.CITY, principal);
        Message m = service.findCityById(id);
        return ResponseEntity.ok().body(m);
    }

    @GetMapping("/city")
    public ResponseEntity getAllCity(Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        System.out.println();
        User user = authService.hasPermission(ControllerConstants.CITY, principal);
        Message m = service.findAllCityByUserId(user.getId());
        return ResponseEntity.ok().body(m);
    }

    @PutMapping("/city")
    public ResponseEntity updateCity(@NotNull @RequestParam("cityName") String cityName,
                                     @NotNull @RequestParam("cityId") Long cityId,
                                     Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.CITY, principal);
        Message m = service.updateCity(cityId, cityName, user);
        return ResponseEntity.ok().body(m);
    }


    /**
     * For Locality CRUD
     */
    @PostMapping("/locality")
    public ResponseEntity createLocality(@NotNull @RequestParam("localityName") String name,
                                         @NotNull @RequestParam("cityId") Long cityId,
                                         Principal principal)
            throws PermissionException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.LOCALITY, principal);
        Message<Locality> m = service.createLocality(name, cityId, user);
        return ResponseEntity
                .created(
                        URI.create(DOMAIN_URL + "/locality/" + m.getData().getId())
                ).body(m);
    }

    @GetMapping("/locality/{id}")
    public ResponseEntity getLocality(@NotNull @PathVariable("id") Long id,
                                      Principal principal)
            throws PermissionException, BadRequestException, EntityNotFoundException {
        authService.hasPermission(ControllerConstants.LOCALITY, principal);
        Message m = service.findLocalityById(id);
        return ResponseEntity.ok().body(m);
    }

    @GetMapping("/locality")
    public ResponseEntity getAllLocality(Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.LOCALITY, principal);
        Message m = service.findAllLocalityByUserId(user.getId());
        return ResponseEntity.ok().body(m);
    }

    @PutMapping("/locality")
    public ResponseEntity updateLocality(@NotNull @RequestParam("localityName") String localityName,
                                         @NotNull @RequestParam("localityId") Long localityId,
                                         @NotNull @RequestParam("cityId") Long cityId,
                                         Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.LOCALITY, principal);
        Message m = service.updateLocality(localityId, localityName, cityId, user);
        return ResponseEntity.ok().body(m);
    }


    /**
     * For Sub locality CRUD
     */
    @PostMapping("/sub-locality")
    public ResponseEntity createSubLocality(@NotNull @RequestParam("subLocalityName") String name,
                                            @NotNull @RequestParam("localityId") Long localityId,
                                            Principal principal)
            throws PermissionException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.SUB_LOCALITY, principal);
        Message<SubLocality> m = service.createSubLocality(name, localityId, user);
        return ResponseEntity
                .created(
                        URI.create(DOMAIN_URL + "/sub-locality/" + m.getData().getId())
                ).body(m);
    }

    @GetMapping("/sub-locality/{id}")
    public ResponseEntity getSubLocality(@NotNull @PathVariable("id") Long id,
                                         Principal principal)
            throws PermissionException, BadRequestException, EntityNotFoundException {
        authService.hasPermission(ControllerConstants.SUB_LOCALITY, principal);
        Message m = service.findSubLocalityById(id);
        return ResponseEntity.ok().body(m);
    }

    @GetMapping("/sub-locality")
    public ResponseEntity getAllSubLocality(Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException {
        User user = authService.hasPermission(ControllerConstants.SUB_LOCALITY, principal);
        Message m = service.findAllSubLocalityByUserId(user.getId());
        return ResponseEntity.ok().body(m);
    }

    @PutMapping("/sub-locality")
    public ResponseEntity updateSubLocality(@NotNull @RequestParam("subLocalityName") String subLocalityName,
                                            @NotNull @RequestParam("subLocalityId") Long subLocalityId,
                                            @NotNull @RequestParam("localityId") Long localityId,
                                            Principal principal)
            throws BadRequestException, EntityNotFoundException, PermissionException, EntityNotSavedException {
        User user = authService.hasPermission(ControllerConstants.SUB_LOCALITY, principal);
        Message m = service.updateSubLocality(subLocalityId, subLocalityName, localityId, user);
        return ResponseEntity.ok().body(m);
    }
}
