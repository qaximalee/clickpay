package com.clickpay.service.user_profile;

import com.clickpay.dto.user_profile.customer.PaginatedCustomersInUserCollectionRequest;
import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationRequest;
import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationResponse;
import com.clickpay.dto.user_profile.customer.CustomerRequest;
import com.clickpay.dto.user_profile.packages.PackageRequest;
import com.clickpay.errors.general.*;
import com.clickpay.model.company.Company;
import com.clickpay.model.connection_type.ConnectionType;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.BoxMedia;
import com.clickpay.model.user_profile.Customer;
import com.clickpay.service.company.ICompanyService;
import com.clickpay.service.connection_type.IConnectionTypeService;
import com.clickpay.service.user.IUserService;
import com.clickpay.service.user_profile.box_media.IBoxMediaService;
import com.clickpay.service.user_profile.customer.ICustomerService;
import com.clickpay.service.user_profile.packages.IPackageService;
import com.clickpay.utils.ResponseMessage;
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
    private final ICustomerService customerService;
    private final IUserService userService;

    public UserProfileService(final ICompanyService companyService,
                              final IBoxMediaService boxMediaService,
                              final IConnectionTypeService connectionTypeService,
                              final IPackageService packageService,
                              final ICustomerService customerService,
                              final IUserService userService) {
        this.boxMediaService = boxMediaService;
        this.companyService = companyService;
        this.connectionTypeService = connectionTypeService;
        this.packageService = packageService;
        this.customerService = customerService;
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
                .setMessage("Company " + ResponseMessage.FETCHED_MESSAGE_SUCCESS);
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
                .setMessage("Company: " + name + ResponseMessage.CREATED_MESSAGE_SUCCESS)
                .setData(company);
    }

    @Override
    public Message findAllCompaniesByUserId(Long userId) throws EntityNotFoundException {
        return new Message()
                .setData(companyService.findAllCompanyByUserId(userId))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Company list "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message updateCompany(Long id, String name, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException {
        log.info("Company updating with company name: " + name);

        Company company = companyService.findById(id);
        company.setName(name);

        company.setModifiedBy(user.getId());
        company.setLastModifiedDate(new Date());

        company = companyService.save(company);

        log.debug("Company: " + name + " is successfully updated for user id: "+user.getId());
        return new Message()
                .setData(company)
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Company "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
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
                .setMessage("Box media " + ResponseMessage.FETCHED_MESSAGE_SUCCESS);
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
                .setMessage("Box media: " + boxNumber + ResponseMessage.CREATED_MESSAGE_SUCCESS)
                .setData(boxMedia);
    }

    @Override
    public Message findAllBoxMediaByUserId(Long userId) throws EntityNotFoundException {
        return new Message()
                .setData(boxMediaService.findAllBoxMediaByUserId(userId))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Box media list "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message updateBoxMedia(Long id, String boxNumber, String nearbyLocation, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException {
        log.info("Box media updating with box number: " + boxNumber);

        BoxMedia boxMedia = boxMediaService.findById(id);
        boxMedia.setBoxNumber(boxNumber);
        boxMedia.setNearbyLocation(nearbyLocation);

        boxMedia.setModifiedBy(user.getId());
        boxMedia.setLastModifiedDate(new Date());

        boxMedia = boxMediaService.save(boxMedia);

        log.debug("Box media: " + boxNumber + " is successfully updated for user id: "+user.getId());
        return new Message()
                .setData(boxMedia)
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Box media "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
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
                .setMessage("Connection type " + ResponseMessage.FETCHED_MESSAGE_SUCCESS);
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

    @Override
    public Message findAllConnectionTypeByUserId(Long userId) throws EntityNotFoundException {
        return new Message()
                .setData(connectionTypeService.findAllConnectionTypeByUserId(userId))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Connection type list "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message updateConnectionType(Long id, String type, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException {
        log.info("Connection type updating with connection type: " + type);

        ConnectionType connectionType = connectionTypeService.findById(id);
        connectionType.setType(type);

        connectionType.setModifiedBy(user.getId());
        connectionType.setLastModifiedDate(new Date());

        connectionType = connectionTypeService.save(connectionType);

        log.debug("Connection type: " + type + " is successfully updated for user id: "+user.getId());
        return new Message()
                .setData(connectionType)
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Connection type "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
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
                .setMessage("Package " + ResponseMessage.FETCHED_MESSAGE_SUCCESS);
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
                .setMessage("Package: " + packageRequest.getPackageName() + ResponseMessage.CREATED_MESSAGE_SUCCESS)
                .setData(pack);
    }

    @Override
    public Message findAllPackageByUserId(Long userId) throws EntityNotFoundException {
        return new Message()
                .setData(packageService.findAllPackageByUserId(userId))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Package list "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message updatePackage(PackageRequest packageRequest, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException {
        log.info("Connection type updating with package name: " + packageRequest.getPackageName());

        Package packageData = packageService.findById(packageRequest.getId());

        Company company = companyService.findById(packageRequest.getCompanyId());
        ConnectionType connectionType = connectionTypeService.findById(packageRequest.getConnectionTypeId());

        packageData.setPackageName(packageRequest.getPackageName());
        packageData.setSalePrice(packageRequest.getSalePrice());
        packageData.setPurchasePrice(packageRequest.getPurchasePrice());
        packageData.setCompany(company);
        packageData.setConnectionType(connectionType);

        packageData.setModifiedBy(user.getId());
        packageData.setLastModifiedDate(new Date());

        packageData = packageService.save(packageData);

        log.debug("Package : " + packageRequest.getPackageName() + " is successfully updated for user id: "+user.getId());
        return new Message()
                .setData(packageData)
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Package "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
    }


    /**
     * CRUD for customer
     */

    @Override
    public Message createCustomer(CustomerRequest dto, User user)
            throws PermissionException, BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException {
        log.debug("Customer: " + dto.getName() + " is successfully created");
        return new Message<Customer>()
                .setStatus(HttpStatus.CREATED.value())
                .setCode(HttpStatus.CREATED.toString())
                .setMessage("Customer: " + dto.getName() +" "+ ResponseMessage.CREATED_MESSAGE_SUCCESS)
                .setData(customerService.createCustomer(dto, user));
    }

    @Override
    public Message findCustomerById(Long id) throws BadRequestException, EntityNotFoundException {
        return new Message<Customer>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setData(customerService.findById(id))
                .setMessage("Customer " + ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message findAllCustomerByUserId(Long userId) throws EntityNotFoundException {
        return new Message()
                .setData(customerService.findAllCustomerById(userId))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Customer list " + ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message findCustomerByFilter(CustomerFilterAndPaginationRequest customerFilterDTO, User user) throws EntityNotFoundException {
        return new Message()
                .setData(customerService.findCustomerByFilter(customerFilterDTO, user))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Customer list by filtration " + ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message<CustomerFilterAndPaginationResponse> getAllCustomersByUserCollections(PaginatedCustomersInUserCollectionRequest request, User user) throws EntityNotFoundException {
        log.info("Fetching customers by finding fields.");
        return new Message<CustomerFilterAndPaginationResponse>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Customers by User Collections Fetched Successfully.")
                .setData(customerService.getCustomersByFilter(request,user));
    }

}
