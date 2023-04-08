package com.clickpay.model.recovery_officer;

import com.clickpay.model.audit.Auditable;
import com.clickpay.model.user.User;
import com.clickpay.utils.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "officer")
public class Officer extends Auditable<Long> {

    @Transient
    public static final String OFFICER = "OFFICER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "cnic")
    private String cnic;

    @Column(name = "cell_no")
    private String cellNo;

    @Column(name = "joining_date")
    @Temporal(TemporalType.DATE)
    private Date joiningDate;

    @Column(name = "leaving_date")
    @Temporal(TemporalType.DATE)
    private Date leavingDate;

    @Column(name = "salary")
    private double salary = 0;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.ACTIVE;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "active")
    private boolean active = true;
}
