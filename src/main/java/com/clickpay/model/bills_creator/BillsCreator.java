package com.clickpay.model.bills_creator;

import com.clickpay.model.area.SubLocality;
import com.clickpay.model.audit.Auditable;
import com.clickpay.model.connection_type.ConnectionType;
import com.clickpay.utils.enums.Months;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "bill")
public class BillsCreator extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private Months month;

    @Column(name = "year")
    private int year;

    @Column(name = "amount")
    private double amount;

    @Column(name = "no_of_users")
    private int noOfUsers;

    @ManyToOne
    @JoinColumn(name = "connection_type_id")
    private ConnectionType connectionType;

    @ManyToOne
    @JoinColumn(name = "sub_locality_id")
    private SubLocality subLocality;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
