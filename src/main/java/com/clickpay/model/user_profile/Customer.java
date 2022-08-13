package com.clickpay.model.user_profile;

import com.clickpay.model.area.SubLocality;
import com.clickpay.model.audit.Auditable;
import com.clickpay.model.company.Company;
import com.clickpay.model.connection_type.ConnectionType;
import com.clickpay.model.user.User;
import com.clickpay.utils.enums.Discount;
import com.clickpay.utils.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "internet_id")
    private String internetId;

    @ManyToOne
    @JoinColumn(name = "sub_locality_id")
    private SubLocality subLocality;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "installation_amount")
    private double installationAmount;

    @Column(name = "other_amount")
    private double otherAmount;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "connection_type_id")
    private ConnectionType connectionType;

    @ManyToOne
    @JoinColumn(name = "box_media_id")
    private BoxMedia boxMedia;

    @ManyToOne
    @JoinColumn(name = "packages_id")
    private Package packages;

    @Temporal(TemporalType.DATE)
    @Column(name = "installation_date")
    private Date installationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "recharge_date")
    private Date rechargeDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount")
    private Discount discount;

    @Column(name = "amount")
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_card_charge")
    private boolean isCardCharge = false;

    @Column(name = "active")
    private boolean active = true;

}
