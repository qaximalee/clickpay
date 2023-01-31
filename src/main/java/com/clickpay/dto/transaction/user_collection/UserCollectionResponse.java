package com.clickpay.dto.transaction.user_collection;

import com.clickpay.dto.recovery_officer.officer.OfficerResponse;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.utils.enums.UserCollectionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserCollectionResponse extends UserCollectionRequest {

    @NotBlank
    private String customerName;

    private String customerNumber;
    private Long billNumber;

    @NotBlank
    private String internetId;

    @NotBlank
    private String connectionType;

    @NotBlank
    private String collectionStatus;

    private String customerAddress;
    private Boolean isDeleted;

    public static UserCollectionResponse fromUserCollection(UserCollection userCollection) {
        UserCollectionResponse userCollectionResponse = new UserCollectionResponse();
        userCollectionResponse.setId(userCollection.getId());
        userCollectionResponse.setCustomerId(userCollection.getCustomer().getId());
        userCollectionResponse.setCustomerName(userCollection.getCustomer().getName());
        userCollectionResponse.setInternetId(userCollection.getCustomer().getInternetId());
        userCollectionResponse.setCustomerAddress(userCollectionResponse.getCustomerAddress());
        userCollectionResponse.setMonth(userCollection.getMonth().name());
        userCollectionResponse.setYear(userCollection.getYear());
        userCollectionResponse.setAmount(userCollection.getAmount());
        userCollectionResponse.setCollectionStatus(userCollection.getCollectionStatus().name());
        userCollectionResponse.setCustomerNumber(userCollection.getCustomer().getMobile());
        userCollectionResponse.setPaymentType(userCollection.getPaymentType().name());
        userCollectionResponse.setConnectionType(userCollection.getCustomer().getConnectionType().getType());
        if (userCollection.getCollectionStatus().name().equals(UserCollectionStatus.PAID.name())){
            userCollectionResponse.setBillNumber(userCollection.getBill().getBillNumber());
        }else {
            userCollectionResponse.setBillNumber(null);
        }
        userCollectionResponse.setRemarks(userCollection.getRemarks());
        userCollectionResponse.setIsDeleted(userCollection.isDeleted());
        return userCollectionResponse;
    }

    public static List<UserCollectionResponse> fromUserCollectionList(List<UserCollection> userCollections) {
        List<UserCollectionResponse> userCollectionResponseList = new ArrayList<>();
        userCollections.stream().forEach(userCollection -> {
            UserCollectionResponse userCollectionResponse = new UserCollectionResponse();
            userCollectionResponse.setId(userCollection.getId());
            userCollectionResponse.setCustomerId(userCollection.getCustomer().getId());
            userCollectionResponse.setCustomerName(userCollection.getCustomer().getName());
            userCollectionResponse.setInternetId(userCollection.getCustomer().getInternetId());
            userCollectionResponse.setCustomerAddress(userCollectionResponse.getCustomerAddress());
            userCollectionResponse.setMonth(userCollection.getMonth().name());
            userCollectionResponse.setYear(userCollection.getYear());
            userCollectionResponse.setAmount(userCollection.getAmount());
            userCollectionResponse.setCollectionStatus(userCollection.getCollectionStatus().name());
            userCollectionResponse.setCustomerNumber(userCollection.getCustomer().getMobile());
            userCollectionResponse.setPaymentType(userCollection.getPaymentType().name());
            userCollectionResponse.setConnectionType(userCollection.getCustomer().getConnectionType().getType());
            if (userCollection.getCollectionStatus().name().equals(UserCollectionStatus.PAID.name())){
                userCollectionResponse.setBillNumber(userCollection.getBill().getBillNumber());
            }else {
                userCollectionResponse.setBillNumber(null);
            }
            userCollectionResponse.setRemarks(userCollection.getRemarks());
            userCollectionResponse.setIsDeleted(userCollection.isDeleted());
            userCollectionResponseList.add(userCollectionResponse);
        });
        return userCollectionResponseList;
    }
}
