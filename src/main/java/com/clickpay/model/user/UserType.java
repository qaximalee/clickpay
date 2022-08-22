package com.clickpay.model.user;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_type")
public class UserType {

    @Transient
    public static final String SUPER_ADMIN_TYPE = "superadmin";

    @Transient
    public static final String ADMIN_TYPE = "admin";

    @Transient
    public static final String USER_TYPE = "user";

    @Transient
    public static final String OFFICER_TYPE = "officer";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type")
    private String type;
}
