package com.clickpay.model.transaction;

import com.clickpay.model.audit.Auditable;
import com.clickpay.model.user_profile.Customer;
import com.clickpay.utils.enums.Months;
import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user_collection")
public class UserCollection extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bill_number")
    private Bill bill;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private Months month;

    @Column(name = "year")
    private int year;

    @Column(name = "amount")
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "collection_status")
    private UserCollectionStatus collectionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "bill_creator_id")
    private BillsCreator billsCreator;

    public static UserCollection mapInUserCollection(Customer customer, UserCollectionStatus userCollectionStatus,
                                                     double amount, PaymentType paymentType, Months month, int year,
                                                     String remarks, Long userId, Date createdDate, BillsCreator billCreator){
        System.out.println("Map user collection data.");
        UserCollection userCollection = new UserCollection();
        userCollection.setCustomer(customer);
        userCollection.setCollectionStatus(userCollectionStatus);
        userCollection.setAmount(amount);
        userCollection.setPaymentType(paymentType);
        userCollection.setMonth(month);
        userCollection.setYear(year);
        userCollection.setRemarks(remarks);
        userCollection.setDeleted(false);
        if (billCreator!=null){
            userCollection.setBillsCreator(billCreator);
        }
        if (userCollection.getId()!=null){
            userCollection.setModifiedBy(userId);
            userCollection.setLastModifiedDate(new Date());
        }else {
            userCollection.setCreatedBy(userId);
            userCollection.setCreationDate(createdDate);
        }

        return userCollection;
    }
}
