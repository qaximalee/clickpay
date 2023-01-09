package com.clickpay.model.user_profile;

import com.clickpay.model.audit.Auditable;
import com.clickpay.model.company.Company;
import com.clickpay.model.connection_type.ConnectionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "package")
public class Package extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "purchase_price")
    private double purchasePrice;

    @Column(name = "sale_price")
    private double salePrice;

    @ManyToOne
    @JoinColumn(name = "connection_type_id")
    private ConnectionType connectionType;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
