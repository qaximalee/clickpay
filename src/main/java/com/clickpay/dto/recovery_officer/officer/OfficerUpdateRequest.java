package com.clickpay.dto.recovery_officer.officer;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class OfficerUpdateRequest {
    @NotNull
    @Range(min = 1, message = "ID should not be negative.")
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    private String address;
    @NotBlank
    private String cnic;
    @NotBlank
    private String cellNo;
    @NotNull
    @Range(min = 0, message = "Salary should not be minimum.")
    private double salary = 0;
    private Date leavingDate;
    private String updatingStatus;
}
