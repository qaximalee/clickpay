package com.clickpay.dto.recovery_officer.officer;

import com.clickpay.model.recovery_officer.Officer;
import com.clickpay.utils.enums.Status;
import lombok.*;

@Getter
@Setter
public class OfficerResponse extends OfficerRequest{
    private Status status;

    public static OfficerResponse fromOfficer(Officer officer) {
        OfficerResponse officerResponse = new OfficerResponse();
        officerResponse.setName(officer.getName());
        officerResponse.setEmail(officer.getEmail());
        officerResponse.setAddress(officer.getEmail());
        officerResponse.setPassword(officer.getPassword());
        officerResponse.setUserName(officer.getUserName());
        officerResponse.setStatus(officer.getStatus());
        officerResponse.setCellNo(officer.getCellNo());
        officerResponse.setJoiningDate(officer.getJoiningDate());
        officerResponse.setCnic(officer.getCnic());
        return officerResponse;
    }
}
