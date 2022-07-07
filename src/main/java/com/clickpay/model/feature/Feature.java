package com.clickpay.model.feature;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "feature")
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "main_menu_name")
    private String mainMenuName;

    @Column(name = "main_url")
    private String mainUrl;

    @Column(name = "value")
    private String value;

    @Column(name = "active")
    private Boolean active = Boolean.TRUE;
}
