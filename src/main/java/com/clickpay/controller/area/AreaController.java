package com.clickpay.controller.area;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.City;
import com.clickpay.model.area.Locality;
import com.clickpay.model.area.SubLocality;
import com.clickpay.service.area.IAreaService;
import com.clickpay.utils.Constant;
import com.clickpay.utils.EnvironmentVariables;
import com.clickpay.utils.Message;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/area")
public class AreaController {

    private final IAreaService service;

    @Autowired
    public AreaController(final IAreaService service) {
        this.service = service;
    }

    /**
     * For City CRUD
     * */
    @PostMapping("/city")
    public ResponseEntity createCity(@NotNull @RequestParam("cityName") String name,
                                     Principal principal) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        Message<City> m = service.createCity(name, Constant.COUNTRY_ID,  principal);
        return ResponseEntity
                .created(
                        URI.create(EnvironmentVariables.SERVER_DOMAIN+"/api/v1/area/city/"+m.getData().getId())
                ).body(m);
    }

    @GetMapping("/city/{id}")
    public ResponseEntity getCity(@NotNull @PathVariable("id") Long id,
                                     Principal principal) throws BadRequestException, EntityNotFoundException {
        Message m = service.findCityById(id, principal);
        return ResponseEntity.ok().body(m);
    }


    /**
     * For Locality CRUD
     * */
    @PostMapping("/locality")
    public ResponseEntity createLocality(@NotNull @RequestParam("localityName") String name,
                                     @NotNull @RequestParam("cityId") String cityId,
                                     Principal principal) {
        Message<Locality> m = service.createLocality(name, cityId,  principal);
        return ResponseEntity
                .created(
                        URI.create(EnvironmentVariables.SERVER_DOMAIN+"/locality/"+m.getData().getId())
                ).body(m);
    }

    @GetMapping("/locality/{id}")
    public ResponseEntity getLocality(@NotNull @PathVariable("id") Long id,
                                     Principal principal) {
        Message m = service.findLocalityById(id);
        return ResponseEntity.ok().body(m);
    }


    /**
     * For Sub locality CRUD
     * */
    @PostMapping("/sub-locality")
    public ResponseEntity createSubLocality(@NotNull @RequestParam("subLocalityName") String name,
                                     @NotNull @RequestParam("localityId") String localityId,
                                     Principal principal) {
        Message<SubLocality> m = service.createSubLocality(name, localityId,  principal);
        return ResponseEntity
                .created(
                        URI.create(EnvironmentVariables.SERVER_DOMAIN+"/sub-locality/"+m.getData().getId())
                ).body(m);
    }

    @GetMapping("/sub-locality/{id}")
    public ResponseEntity getSubLocality(@NotNull @PathVariable("id") Long id,
                                     Principal principal) {
        Message m = service.findSubLocalityById(id);
        return ResponseEntity.ok().body(m);
    }
}
