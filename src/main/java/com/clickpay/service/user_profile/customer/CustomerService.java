package com.clickpay.service.user_profile.customer;

import com.clickpay.dto.user_profile.customer.CustomerFilterDTO;
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
import com.clickpay.utils.Constant;
import com.clickpay.utils.StringUtil;
import com.clickpay.utils.enums.Discount;
import com.clickpay.utils.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Slf4j
@Service
public class CustomerService implements ICustomerService{

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
        int rand = 100+random.nextInt(99899);
        String internetId = INTERNET_ID_PREFIX+rand;

        // while we have same internet id for a user then it should not be equals
        while(repo.existsByInternetIdAndCreatedBy(internetId, id)) {
            internetId = INTERNET_ID_PREFIX + (100+random.nextInt(99899));
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

        UserType userType = userTypeService.findByUserTypeName(Constant.USER);
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
            log.debug("Customer with customer id: "+customer.getId()+ " created successfully.");
            return customer;
        } catch (Exception e) {
            log.error("Customer can not be saved.");
            throw new EntityNotSavedException("Customer can not be saved.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Customer findById(Long id) throws BadRequestException, EntityNotFoundException {
        log.info("Finding customer by id: "+id);
        if (id == null || id < 1) {
            log.error("Customer id " + id + " is invalid.");
            throw new BadRequestException("Provided customer id should be a valid and non null value.");
        }
        Optional<Customer> data = repo.findById(id);
        if (!data.isPresent()) {
            log.error("No customer found with id: "+id);
            throw new EntityNotFoundException("No customer found with provided city id.");
        }
        return data.get();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponse> findAllCustomerById(Long userId) throws EntityNotFoundException {
        log.info("Fetching all customer for user id: "+userId);
        List<Customer> data = repo.findAllByCreatedBy(userId);
        if (data == null || data.isEmpty()) {
            log.error("No customer data found.");
            throw new EntityNotFoundException("Customer not found.");
        }

        return CustomerResponse.mapListOfCustomer(data);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponse> findCustomerByFilter(CustomerFilterDTO customerFilterDTO, User user) throws EntityNotFoundException {
        log.info("Fetching all customer by filtration data");
        if(customerFilterDTO.getBoxMediaId() == null
                && customerFilterDTO.getConnectionTypeId() == null
                && customerFilterDTO.getPackageId() == null
                && customerFilterDTO.getStatus() == null
                && customerFilterDTO.getDiscount() == null
        ) {
            log.info("No filtration data selected.");
            return findAllCustomerById(user.getId());
        }

        // TODO fetch filtration data from the customerRepo's filtration method and mapped them to customerResponse dto
        log.info("Fetching data with filtration values.");
        List<CustomerResponse> response = CustomerResponse.mapListOfCustomerDetail(
                repo.getCustomerByFiltration(
                    customerFilterDTO.getStatus(),
                    customerFilterDTO.getConnectionTypeId(),
                    customerFilterDTO.getBoxMediaId(),
                    customerFilterDTO.getPackageId(),
                    customerFilterDTO.getDiscount(),
                    user.getId()
                )
        );
        if (response == null || response.isEmpty()) {
            log.info("No customers found with filtration.");
            throw new EntityNotFoundException("No customers found with filtration data.");
        }
        return response;
    }

}
