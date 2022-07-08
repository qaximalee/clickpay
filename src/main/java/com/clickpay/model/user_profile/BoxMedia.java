package com.clickpay.model.user_profile;


import com.clickpay.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "box_media")
public class BoxMedia extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "box_number")
    private String boxNumber;

    @Column(name = "nearby_location")
    private String nearbyLocation;

    @Column(name = "active")
    private boolean active = true;
}
