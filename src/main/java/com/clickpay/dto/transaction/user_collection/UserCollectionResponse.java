package com.clickpay.dto.transaction.user_collection;

import com.clickpay.model.transaction.UserCollection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserCollectionResponse extends UserCollectionRequest {

    @NotBlank
    private String customerName;

    @NotBlank
    private String internetId;

    private String customerAddress;
    private Boolean isDeleted;

    public static UserCollectionResponse fromUserCollection(UserCollection userCollection) {
        UserCollectionResponse userCollectionResponse = new UserCollectionResponse();
        userCollectionResponse.setId(userCollection.getId());
        userCollectionResponse.setCustomerId(userCollection.getCustomer().getId());
        userCollectionResponse.setCustomerName(userCollection.getCustomer().getName());
        userCollectionResponse.setInternetId(userCollectionResponse.getInternetId());
        userCollectionResponse.setCustomerAddress(userCollectionResponse.getCustomerAddress());
        userCollectionResponse.setMonth(userCollection.getMonth().name());
        userCollectionResponse.setYear(userCollection.getYear());
        userCollectionResponse.setAmount(userCollection.getAmount());
        userCollectionResponse.setCollectionStatus(userCollection.getCollectionStatus().name());
        userCollectionResponse.setPaymentType(userCollection.getPaymentType().name());
        userCollectionResponse.setRemarks(userCollection.getRemarks());
        userCollectionResponse.setIsDeleted(userCollection.isDeleted());
        return userCollectionResponse;
    }
}
