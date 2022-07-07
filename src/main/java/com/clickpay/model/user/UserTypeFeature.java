package com.clickpay.model.user;

import com.clickpay.model.feature.Feature;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * This entity will have the default features permission
 *
 * */

@Getter
@Setter
@Entity
@Table(name = "user_type_feature")
public class UserTypeFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;
}
