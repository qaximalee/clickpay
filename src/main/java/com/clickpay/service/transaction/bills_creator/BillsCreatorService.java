package com.clickpay.service.transaction.bills_creator;

import com.clickpay.dto.transaction.bills_creator.BillsCreatorRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.area.SubLocality;
import com.clickpay.model.bills_creator.BillsCreator;
import com.clickpay.model.connection_type.ConnectionType;
import com.clickpay.model.user.User;
import com.clickpay.repository.bills_creator.BillsCreatorRepository;
import com.clickpay.service.area.sub_locality.ISubLocalityService;
import com.clickpay.service.connection_type.IConnectionTypeService;
import com.clickpay.utils.Message;
import com.clickpay.utils.enums.Months;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillsCreatorService implements IBillsCreatorService{
    private final BillsCreatorRepository repo;
    private final IConnectionTypeService connectionTypeService;
    private final ISubLocalityService subLocalityService;

    @Transactional
    @Override
    public BillsCreator createBillsCreator(BillsCreatorRequest request, User user) throws BadRequestException, EntityNotFoundException {
        ConnectionType connectionType = connectionTypeService.findById(request.getConnectionType());

        BillsCreator billsCreator = new BillsCreator();

        billsCreator.setAmount(request.getAmount());
        billsCreator.setConnectionType(connectionType);
        if(request.getSubLocality()!=null){
            SubLocality subLocality = subLocalityService.findById(request.getSubLocality());
            billsCreator.setSubLocality(subLocality);
        }
        billsCreator.setMonth(Months.of(request.getMonth()));
        billsCreator.setYear(request.getYear());
        billsCreator.setNoOfUsers(request.getNoOfUsers());
        billsCreator.setDeleted(false);
        // set audits
        billsCreator.setCreatedBy(user.getId());
        billsCreator.setCreationDate(new Date());

        repo.save(billsCreator);

        return billsCreator;

    }

    private void checkBillCreatorValid(BillsCreatorRequest request, User user){
        BillsCreator billsCreator = repo.existsBillCreator(user.getId(),request.getSubLocality(),request.getConnectionType(),request.getMonth(),request.getYear());
    }
}
