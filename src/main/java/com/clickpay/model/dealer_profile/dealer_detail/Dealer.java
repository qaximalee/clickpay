package com.clickpay.model.dealer_profile.dealer_detail;

import com.clickpay.model.area.Locality;
import com.clickpay.model.audit.Auditable;
import com.clickpay.model.company.Company;
import com.clickpay.utils.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dealer")
public class Dealer extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dealer_ID")
    private String dealerID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cell_no", nullable = false)
    private String cellNo;

    @Column(name = "phone_no")
    private String phoneNo;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "locality_id")
    private Locality locality;

    @Column(name = "cnic", nullable = false)
    private String cnic;

    @Column(name = "address")
    private String address;

    @Column(name = "joining_date", nullable = false)
    private Date joiningDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
