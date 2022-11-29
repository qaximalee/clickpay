package com.clickpay.model.transaction;

import com.clickpay.model.audit.Auditable;
import com.clickpay.model.user.User;
import com.clickpay.utils.enums.Discount;
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
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "pay_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payAt;

    @Column(name = "amount")
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "collection_status")
    private UserCollectionStatus collectionStatus;

    @Column(name = "payment_type")
    private String paymentType = "MONTHLY";
}
