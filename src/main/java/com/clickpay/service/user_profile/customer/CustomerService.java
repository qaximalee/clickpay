package com.clickpay.service.user_profile.customer;

import com.clickpay.dto.recovery_officer.recovery_officer_collection.RecoveryOfficerCustomerDropdownDto;
import com.clickpay.dto.user_profile.customer.PaginatedCustomersInUserCollectionRequest;
import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationRequest;
import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationResponse;
import com.clickpay.dto.user_profile.customer.CustomerRequest;
import com.clickpay.dto.user_profile.customer.CustomerResponse;
import com.clickpay.errors.general.*;
import com.clickpay.model.area.SubLocality;
import com.clickpay.model.company.Company;
import com.clickpay.model.connection_type.ConnectionType;
import com.clickpay.model.user.User;
import com.clickpay.model.user.UserType;
import com.clickpay.model.user_profile.BoxMedia;
import com.clickpay.model.user_profile.Customer;
import com.clickpay.model.user_profile.Package;
import com.clickpay.repository.user_profile.CustomerRepository;
import com.clickpay.service.area.sub_locality.ISubLocalityService;
import com.clickpay.service.company.ICompanyService;
import com.clickpay.service.connection_type.IConnectionTypeService;
import com.clickpay.service.user.IUserService;
import com.clickpay.service.user.user_type.IUserTypeService;
import com.clickpay.service.user_profile.box_media.IBoxMediaService;
import com.clickpay.service.user_profile.packages.IPackageService;
import com.clickpay.utils.StringUtil;
import com.clickpay.utils.enums.Discount;
import com.clickpay.utils.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Slf4j
@Service
public class CustomerService implements ICustomerService {

    public static final String INTERNET_ID_PREFIX = "speedex-";

    private final CustomerRepository repo;
    private final IUserService userService;
    private final ISubLocalityService subLocalityService;
    private final ICompanyService companyService;
    private final IBoxMediaService boxMediaService;
    private final IConnectionTypeService connectionTypeService;
    private final IPackageService packageService;
    private final IUserTypeService userTypeService;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(final CustomerRepository repo,
                           final IUserService userService,
                           final ISubLocalityService subLocalityService,
                           final ICompanyService companyService,
                           final IBoxMediaService boxMediaService,
                           final IConnectionTypeService connectionTypeService,
                           final IPackageService packageService,
                           final IUserTypeService userTypeService,
                           final PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.userService = userService;
        this.boxMediaService = boxMediaService;
        this.companyService = companyService;
        this.packageService = packageService;
        this.connectionTypeService = connectionTypeService;
        this.subLocalityService = subLocalityService;
        this.userTypeService = userTypeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public Customer createCustomer(CustomerRequest dto, User user)
            throws BadRequestException,
            EntityNotFoundException,
            EntityNotSavedException,
            EntityAlreadyExistException {

        // Validate all IDs of existing entities
        Company company = companyService.findById(dto.getCompanyId());
        SubLocality subLocality = subLocalityService.findById(dto.getSubLocalityId());
        BoxMedia boxMedia = boxMediaService.findById(dto.getBoxMediaId());
        ConnectionType connectionType = connectionTypeService.findById(dto.getConnectionTypeId());
        Package packageData = packageService.findById(dto.getPackagesId());

        Discount discount = Discount.of(dto.getDiscount());


        String internetId = createInternetId(user.getId());

        // Register user first
        User createdUser = createUser(internetId, dto, user);

        log.info("Populate customer data.");
        Customer customer = new Customer();
        customer.setInternetId(internetId);
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        customer.setMobile(dto.getMobile());
        customer.setInstallationAmount(dto.getInstallationAmount());
        customer.setOtherAmount(dto.getOtherAmount());
        customer.setInstallationDate(dto.getInstallationDate());
        customer.setRechargeDate(dto.getRechargeDate());
        customer.setDiscount(discount);
        customer.setAmount(dto.getAmount());

        // Relational entities
        customer.setSubLocality(subLocality);
        customer.setCompany(company);
        customer.setConnectionType(connectionType);
        customer.setBoxMedia(boxMedia);
        customer.setPackages(packageData);
        customer.setUser(createdUser);

        // Audits and checks
        customer.setCardCharge(dto.isCardCharge());
        customer.setStatus(Status.ACTIVE);
        customer.setCreatedBy(user.getId());
        customer.setCreationDate(new Date());

        return save(customer);
    }

    private String createInternetId(Long id) {
        Random random = new Random();
        int rand = 100 + random.nextInt(99899);
        String internetId = INTERNET_ID_PREFIX + rand;

        // while we have same internet id for a user then it should not be equals
        while (repo.existsByInternetIdAndCreatedBy(internetId, id)) {
            internetId = INTERNET_ID_PREFIX + (100 + random.nextInt(99899));
        }
        return internetId;
    }

    private User createUser(String internetId, CustomerRequest dto, User createdByUser)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException, EntityAlreadyExistException {
        log.info("Creating user with customer data.");

        // Is already user with username || email
        if (userService.existsByUsernameOREmail(internetId, dto.getEmail())) {
            log.error("User already exists with username or email.");
            throw new EntityAlreadyExistException("User already exists with username or email.");
        }

        UserType userType = userTypeService.findByUserTypeName(UserType.USER_TYPE);
        String firstAndLastName[] = StringUtil.extractFirstNameAndLastNameFromNameField(dto.getName());

        User user = new User();
        user.setFirstName(firstAndLastName[0]);
        user.setLastName(firstAndLastName[1]);
        user.setEmail(dto.getEmail());
        user.setUserType(userType);
        user.setIsCustomPermission(false);
        user.setUsername(internetId);
        user.setPassword(passwordEncoder.encode(internetId));
        user.setCreatedBy(user.getId());
        user.setCreationDate(new Date());
        user.setVerified(true);

        return userService.save(user);
    }


    @Transactional
    @Override
    public Customer save(Customer customer) throws BadRequestException, EntityNotSavedException {
        log.info("Creating customer.");

        if (customer == null) {
            log.error("Customer should not be null.");
            throw new BadRequestException("Customer should not be null.");
        }
        try {
            customer = repo.save(customer);
            log.debug("Customer with customer id: " + customer.getId() + " created successfully.");
            return customer;
        } catch (Exception e) {
            log.error("Customer can not be saved.");
            throw new EntityNotSavedException("Customer can not be saved.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Customer findById(Long id) throws BadRequestException, EntityNotFoundException {
        log.info("Finding customer by id: " + id);
        if (id == null || id < 1) {
            log.error("Customer id " + id + " is invalid.");
            throw new BadRequestException("Provided customer id should be a valid and non null value.");
        }
        Optional<Customer> data = repo.findById(id);
        if (!data.isPresent()) {
            log.error("No customer found with id: " + id);
            throw new EntityNotFoundException("No customer found with provided city id.");
        }
        return data.get();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Customer> findAllCustomerById(Long userId) throws EntityNotFoundException {
        log.info("Fetching all customer for user id: " + userId);
        List<Customer> data = repo.findAllByCreatedBy(userId);
        if (data == null || data.isEmpty()) {
            log.error("No customer data found.");
            throw new EntityNotFoundException("Customer not found.");
        }

        return data;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Customer> findAllCustomerByIdAndConnectionTypeId(Long userId, Long connectionTypeId) throws EntityNotFoundException {
        log.info("Fetching all customer for user id: " + userId + " and connection Type Id : " + connectionTypeId);
        List<Customer> data = repo.findAllByCreatedByAndConnectionType_Id(userId, connectionTypeId);
        if (data == null || data.isEmpty()) {
            log.error("No customer data found.");
            throw new EntityNotFoundException("Customer not found.");
        }

        return data;
    }


    @Transactional(readOnly = true)
    @Override
    public List<Customer> findAllCustomerByIdAndConnectionTypeIdAndSubLocalityId(Long userId, Long connectionTypeId, Long subLocalityId) throws EntityNotFoundException {
        log.info("Fetching all customer for user id : " + userId + " and connection Type Id : " + connectionTypeId + " and subLocality id : " + subLocalityId);
        List<Customer> data = repo.findAllByCreatedByAndConnectionType_IdAndSubLocality_Id(userId, connectionTypeId, subLocalityId);
        if (data == null || data.isEmpty()) {
            log.error("No customer data found.");
            throw new EntityNotFoundException("Customer not found.");
        }
        return data;
    }

    // for unpaid collections
    @Transactional(readOnly = true)
    @Override
    public Customer findCustomerByUserId(Long userId) throws EntityNotFoundException {
        log.info("Fetching by customer by user id: " + userId);
        Customer data = repo.findByUser_Id(userId);
        if (data == null) {
            log.error("No customer data found.");
            throw new EntityNotFoundException("Customer not found.");
        }
        return data;
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerFilterAndPaginationResponse findCustomerByFilter(CustomerFilterAndPaginationRequest customerFilterDTO, User user) throws EntityNotFoundException {
        log.info("Fetching all customer by filtration data");

        Pageable pageable = PageRequest.of(customerFilterDTO.getPageNo(), customerFilterDTO.getPageSize());

        // TODO fetch filtration data from the customerRepo's filtration method and mapped them to customerResponse dto
        log.info("Fetching data with filtration values.");

        Page<Object[]> customers = repo.getCustomerByFiltration(
                customerFilterDTO.getStatus(),
                customerFilterDTO.getConnectionTypeId(),
                customerFilterDTO.getBoxMediaId(),
                customerFilterDTO.getPackageId(),
                customerFilterDTO.getDiscount(),
                user.getId(),
                pageable
        );

        if (customers == null || customers.isEmpty()) {
            log.info("No customers found with and without filtration.");
            throw new EntityNotFoundException("No customers found with and without filtration data.");
        }

        return CustomerFilterAndPaginationResponse.builder()
                .customers(CustomerResponse.mapListOfCustomerDetail(customers.getContent()))
                .pageNo(customerFilterDTO.getPageNo())
                .pageSize(customerFilterDTO.getPageSize())
                .noOfPages(customers.getTotalPages())
                .totalRows(customers.getTotalElements())
                .build();
    }

    @Override
    public CustomerFilterAndPaginationResponse getCustomersByFilter(PaginatedCustomersInUserCollectionRequest request, User user) throws EntityNotFoundException {

        log.info("Fetching User collection by collection Id ");

        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize());

        if (request.getSearchInput() != null) {
            request.setSearchInput("%" + request.getSearchInput() + "%");
        }

        Page<Object[]> customersFiltered = repo.findCustomersByUserCollectionsWithFilter(request.getSubLocalityId(),
                request.getCustomerStatus(),
                request.getUserCollectionStatus(),
                request.getConnectionTypeId(),
                request.getSearchInput(),
                user.getId(),
                pageable);

        if (customersFiltered == null || customersFiltered.isEmpty()) {
            log.info("No customers found in user collection with and without filtration.");
            throw new EntityNotFoundException("No customers found in user collection with and without filtration data.");
        }

        return CustomerFilterAndPaginationResponse.builder()
                .customers(CustomerResponse.mapListOfCustomerDetail(customersFiltered.getContent()))
                .pageNo(request.getPageNo())
                .pageSize(request.getPageSize())
                .noOfPages(customersFiltered.getTotalPages())
                .totalRows(customersFiltered.getTotalElements())
                .build();
    }

    /**
     * Fetch all customers for a recovery officer assigned into the area allocation.
     *
     * */
    @Transactional(readOnly = true)
    @Override
    public List<RecoveryOfficerCustomerDropdownDto> getAllUsersWithRespectToTheRecoveryOfficer(User user) throws EntityNotFoundException {
        log.info("Fetching all customer data by logged in recovery officer.");
        return RecoveryOfficerCustomerDropdownDto.fromCustomerOfRecoveryOfficer(repo.findAllUsersWithRespectToTheRecoveryOfficer(user.getId()));
    }


}
