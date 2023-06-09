package com.clickpay.dto.recovery_officer.officer;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
public class OfficerRequest {
    @NotNull
    @Range(min = 1, message = "ID should not be negative.")
    private Long id;
    @NotBlank
    @Size(min = 3, message = "Name should not be less than 3 characters and less than 80 characters long.")
    private String name;

    @NotBlank
    @Email(message = "Please enter valid email address.")
    private String email;
    private String address;
    @NotBlank
    @Size(min = 13, max = 13, message = "The CNIC no should only be 13 characters long.")
    private String cnic;
    @NotBlank
    @Pattern(regexp = "^(\\+92|92|0)?(3\\d{9})$", message = "Please enter mobile no with i.e +923001234567, 923001234567 or 03001234567.")
    private String cellNo;
    @NotNull
    @Range(min = 5000, message = "Salary should not be less than 5000.")
    private double salary = 0;
    @NotNull
    private Date joiningDate;
    private Date leavingDate;
    @NotBlank
    @Size(min = 5, max=20, message = "The username should not be less than 5 characters and greater than 20 characters long.")
    private String userName;

    @NotBlank
    @Size(min = 8, max = 40, message = "The password should not be less than 8 characters and greater than 40 characters long.")
    private String password;
}
