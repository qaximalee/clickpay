package com.clickpay.model.transaction;

import com.clickpay.model.audit.Auditable;
import com.clickpay.model.bill.Bill;
import com.clickpay.model.user_profile.Customer;
import com.clickpay.utils.enums.Months;
import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
}
