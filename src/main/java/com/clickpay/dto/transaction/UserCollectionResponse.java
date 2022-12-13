package com.clickpay.dto.transaction;

import com.clickpay.model.transaction.UserCollection;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCollectionResponse extends UserCollectionRequest{

    @NotNull
    private String customerName;

    private Boolean isDeleted;

    public static UserCollectionResponse fromUserCollection(UserCollection userCollection) {
        UserCollectionResponse userCollectionResponse = new UserCollectionResponse();
        userCollectionResponse.setId(userCollection.getId());
        userCollectionResponse.setCustomerId(userCollection.getCustomer().getId());
        userCollectionResponse.setCustomerName(userCollection.getCustomer().getName());
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
