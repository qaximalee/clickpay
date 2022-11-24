package com.clickpay.service.dealer_profile;

import com.clickpay.dto.dealer_profile.dealer_detail.DealerDetailRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.Locality;
import com.clickpay.model.company.Company;
import com.clickpay.model.dealer_profile.dealer_detail.Dealer;
import com.clickpay.model.user.User;
import com.clickpay.service.area.locality.ILocalityService;
import com.clickpay.service.company.ICompanyService;
import com.clickpay.service.dealer_profile.dealer_detail.IDealerDetailService;
import com.clickpay.utils.Message;
import com.clickpay.utils.ResponseMessage;
import com.clickpay.utils.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DealerProfileService implements IDealerProfileService{

    private final IDealerDetailService dealerDetailService;
    private final ILocalityService localityService;
    private final ICompanyService companyService;

    public DealerProfileService(IDealerDetailService dealerDetailService, ILocalityService localityService, ICompanyService companyService) {
        this.dealerDetailService = dealerDetailService;
        this.localityService = localityService;
        this.companyService = companyService;
    }

    /**
     * CRUD operations for dealer
     *
     * */

    @Override
    public Message createDealer(DealerDetailRequest dealerDetailRequest, User user)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException {
        log.info("Dealer creation is started.");

        dealerDetailService.isExistsByDealerID(dealerDetailRequest.getDealerID());

        Locality locality = localityService.findById(dealerDetailRequest.getLocalityId());
        Company company = companyService.findById(dealerDetailRequest.getCompanyId());

        Dealer dealer = Dealer.builder()
                .dealerID(dealerDetailRequest.getDealerID())
                .name(dealerDetailRequest.getName())
                .address(dealerDetailRequest.getAddress())
                .cellNo(dealerDetailRequest.getCellNo())
                .phoneNo(dealerDetailRequest.getPhoneNo())
                .cnic(dealerDetailRequest.getCnic())
                .locality(locality)
                .company(company)
                .joiningDate(dealerDetailRequest.getJoiningDate())
                .status(Status.ACTIVE)
                .build();

        dealer.setCreatedBy(user.getId());
        dealer.setCreationDate(new Date());

        dealer = dealerDetailService.save(dealer);

        log.debug("Dealer: " + dealer.getName() + " is successfully created for user id: " + user.getId());
        return new Message<Dealer>()
                .setStatus(HttpStatus.CREATED.value())
                .setCode(HttpStatus.CREATED.toString())
                .setMessage("Dealer: " + dealer.getName() + ResponseMessage.CREATED_MESSAGE_SUCCESS)
                .setData(dealer);
    }

    @Override
    public Message<Dealer> findDealerById(Long id) throws BadRequestException, EntityNotFoundException {
        log.info("Dealer fetching by id: " + id);
        return new Message<Dealer>()
                .setData(dealerDetailService.findById(id))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Dealer "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message<List<Dealer>> findAllDealerByUserId(Long userId) throws EntityNotFoundException {
        log.info("Dealer list is fetching.");
        return new Message<List<Dealer>>()
                .setData(dealerDetailService.findAllDealerByUserId(userId))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Dealer list "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message<Dealer> updateDealer(DealerDetailRequest dealerDetailRequest, User user)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Dealer updating with name: " + dealerDetailRequest.getName());

        Locality locality = localityService.findById(dealerDetailRequest.getLocalityId());
        Company company = companyService.findById(dealerDetailRequest.getCompanyId());

        Dealer dealer = dealerDetailService.findById(dealerDetailRequest.getId());
        dealer.setDealerID(dealerDetailRequest.getDealerID());
        dealer.setName(dealerDetailRequest.getName());
        dealer.setCnic(dealerDetailRequest.getCnic());
        dealer.setPhoneNo(dealerDetailRequest.getPhoneNo());
        dealer.setCellNo(dealerDetailRequest.getCellNo());
        dealer.setAddress(dealerDetailRequest.getAddress());
        dealer.setCompany(company);
        dealer.setLocality(locality);
        dealer.setStatus(Status.valueOf(dealerDetailRequest.getStatus()));
        dealer.setModifiedBy(user.getId());
        dealer.setLastModifiedDate(new Date());

        dealer = dealerDetailService.save(dealer);

        log.debug("Dealer: " + dealer.getName() + " is successfully updated for user id: "+user.getId());
        return new Message<Dealer>()
                .setData(dealer)
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Dealer "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
    }

    @Override
    public Message<Dealer> updateDealerStatus(Long dealerId, String status, User user)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Dealer status updating.");

        Dealer dealer = dealerDetailService.findById(dealerId);

        dealer.setStatus(Status.INACTIVE);
        dealer.setModifiedBy(user.getId());
        dealer.setLastModifiedDate(new Date());

        dealer = dealerDetailService.save(dealer);

        log.debug("Dealer: " + dealer.getName() + " is successfully status updated by user id: "+user.getId());
        return new Message<Dealer>()
                .setData(dealer)
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Dealer Status "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
    }

    @Override
    public Message<Dealer> deleteDealer(Long dealerId , User user)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Dealer status updating.");

        Dealer dealer = dealerDetailService.findById(dealerId);

        dealer.setDeleted(true);
        dealer.setModifiedBy(user.getId());
        dealer.setLastModifiedDate(new Date());

        dealer = dealerDetailService.save(dealer);

        log.debug("Dealer: " + dealer.getName() + " is successfully deleted by user id: "+user.getId());
        return new Message<Dealer>()
                .setData(dealer)
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Dealer Status "+ ResponseMessage.DELETED_MESSAGE_SUCCESS);
    }
}
