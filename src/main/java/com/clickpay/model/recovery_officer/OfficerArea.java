package com.clickpay.model.recovery_officer;

import com.clickpay.model.area.SubLocality;
import com.clickpay.model.audit.Auditable;
import com.clickpay.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "officer_area")
public class OfficerArea extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "sub_locality_id")
    private SubLocality subLocality;

    public static List<OfficerArea> fromSubLocalitiesAndUserAndLoggedInUserId(List<SubLocality> subLocalities, User user, long id) {
        return subLocalities.stream().map(e -> {
            OfficerArea officerArea = new OfficerArea();
            officerArea.setSubLocality(e);
            officerArea.setUser(user);
            officerArea.setCreatedBy(id);
            officerArea.setCreationDate(new Date());
            return officerArea;
        }).collect(Collectors.toList());
    }
}
