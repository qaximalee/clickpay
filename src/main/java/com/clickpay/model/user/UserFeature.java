package com.clickpay.model.user;

import com.clickpay.model.feature.Feature;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * This entity will contains the custom permission for specific users some specific features
 *
 * */

@Getter
@Setter
@Entity
@Table(name = "user_feature")
public class UserFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;

}
