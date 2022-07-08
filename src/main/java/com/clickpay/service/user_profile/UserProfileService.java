package com.clickpay.service.user_profile;

import com.clickpay.dto.packages.PackageRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.company.Company;
import com.clickpay.model.connection_type.ConnectionType;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.BoxMedia;
import com.clickpay.service.company.ICompanyService;
import com.clickpay.service.connection_type.IConnectionTypeService;
import com.clickpay.service.user.IUserService;
import com.clickpay.service.user_profile.box_media.IBoxMediaService;
import com.clickpay.service.user_profile.packages.IPackageService;
import com.clickpay.utils.Constant;
import com.clickpay.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.clickpay.model.user_profile.Package;

import java.util.Date;

@Slf4j
@Service
public class UserProfileService implements IUserProfileService{

    private final ICompanyService companyService;
    private final IBoxMediaService boxMediaService;
    private final IConnectionTypeService connectionTypeService;
    private final IPackageService packageService;
    private final IUserService userService;

    public UserProfileService(final ICompanyService companyService,
                              final IBoxMediaService boxMediaService,
                              final IConnectionTypeService connectionTypeService,
                              final IPackageService packageService,
                              final IUserService userService) {
        this.boxMediaService = boxMediaService;
        this.companyService = companyService;
        this.connectionTypeService = connectionTypeService;
        this.packageService = packageService;
        this.userService = userService;
    }


    /**
     * CRUB for company
     */

    @Override
    public Message findCompanyById(Long id) throws BadRequestException, EntityNotFoundException {
        return new Message<Company>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setData(companyService.findById(id))
                .setMessage("Company " + Constant.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message createCompany(String name, User user)
            throws EntityNotSavedException, BadRequestException {
        log.info("Company creation is started.");

        Company company = new Company();
        company.setName(name);
        company.setCreationDate(new Date());
        company.setCreatedBy(user.getId());

        company = companyService.save(company);

        log.debug("Company: " + name + " is successfully created for user id: " + user.getId());
        return new Message<Company>()
                .setStatus(HttpStatus.CREATED.value())
                .setCode(HttpStatus.CREATED.toString())
                .setMessage("Company: " + name + Constant.CREATED_MESSAGE_SUCCESS)
                .setData(company);
    }


    /**
     * CRUB for box media
     * */

    @Override
    public Message findBoxMediaById(Long id) throws BadRequestException, EntityNotFoundException {
        return new Message<BoxMedia>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setData(boxMediaService.findById(id))
                .setMessage("Box media " + Constant.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message createBoxMedia(String boxNumber, String nearbyLocation, User user)
            throws EntityNotSavedException, BadRequestException {
        log.info("Box media creation is started.");

        BoxMedia boxMedia = new BoxMedia();
        boxMedia.setBoxNumber(boxNumber);
        boxMedia.setNearbyLocation(nearbyLocation);
        boxMedia.setCreationDate(new Date());
        boxMedia.setCreatedBy(user.getId());

        boxMedia = boxMediaService.save(boxMedia);

        log.debug("Box media: " + boxNumber + " is successfully created for user id: " + user.getId());
        return new Message<BoxMedia>()
                .setStatus(HttpStatus.CREATED.value())
                .setCode(HttpStatus.CREATED.toString())
                .setMessage("Box media: " + boxNumber + Constant.CREATED_MESSAGE_SUCCESS)
                .setData(boxMedia);
    }


    /**
     * CRUB for connection type
     * */

    @Override
    public Message findConnectionTypeById(Long id) throws BadRequestException, EntityNotFoundException {
        return new Message<ConnectionType>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setData(connectionTypeService.findById(id))
                .setMessage("Connection type " + Constant.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message createConnectionType(String type, User user)
            throws EntityNotSavedException, BadRequestException {
        log.info("Connection type creation is started.");

        ConnectionType connectionType = new ConnectionType();
        connectionType.setType(type);
        connectionType.setCreationDate(new Date());
        connectionType.setCreatedBy(user.getId());

        connectionType = connectionTypeService.save(connectionType);

        log.debug("Connection type: " + type + " is successfully created for user id: " + user.getId());
        return new Message<ConnectionType>()
                .setStatus(HttpStatus.CREATED.value())
                .setCode(HttpStatus.CREATED.toString())
                .setMessage("Connection type: " + type + " is successfully created.")
                .setData(connectionType);
    }


    /**
     * CRUB for package
     * */

    @Override
    public Message findPackageById(Long id) throws BadRequestException, EntityNotFoundException {
        return new Message<Package>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setData(packageService.findById(id))
                .setMessage("Package " + Constant.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message createPackage(PackageRequest packageRequest, User user)
            throws EntityNotSavedException, BadRequestException, EntityNotFoundException {
        log.info("Package creation is started.");

        Company company = companyService.findById(packageRequest.getCompanyId());
        ConnectionType connectionType = connectionTypeService.findById(packageRequest.getConnectionTypeId());

        Package pack = new Package();
        pack.setPackageName(packageRequest.getPackageName());
        pack.setPurchasePrice(packageRequest.getPurchasePrice());
        pack.setSalePrice(packageRequest.getSalePrice());
        pack.setCompany(company);
        pack.setConnectionType(connectionType);

        pack.setCreationDate(new Date());
        pack.setCreatedBy(user.getId());

        pack = packageService.save(pack);

        log.debug("Package: " + packageRequest.getPackageName() + " is successfully created for user id: " + user.getId());
        return new Message<Package>()
                .setStatus(HttpStatus.CREATED.value())
                .setCode(HttpStatus.CREATED.toString())
                .setMessage("Package: " + packageRequest.getPackageName() + Constant.CREATED_MESSAGE_SUCCESS)
                .setData(pack);
    }
}
