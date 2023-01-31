package com.clickpay.model.transaction;

import com.clickpay.model.audit.Auditable;
import com.clickpay.utils.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "bill")
public class Bill extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "amount")
    private Double Amount;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
