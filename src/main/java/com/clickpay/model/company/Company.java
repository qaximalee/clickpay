package com.clickpay.model.company;


import com.clickpay.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
