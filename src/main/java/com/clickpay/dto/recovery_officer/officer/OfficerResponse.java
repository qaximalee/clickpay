package com.clickpay.dto.recovery_officer.officer;

import com.clickpay.model.recovery_officer.Officer;
import com.clickpay.utils.enums.Status;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OfficerResponse extends OfficerRequest{
    private Status status;

    public static OfficerResponse fromOfficer(Officer officer) {
        OfficerResponse officerResponse = new OfficerResponse();
        officerResponse.setId(officer.getId());
        officerResponse.setName(officer.getName());
        officerResponse.setEmail(officer.getEmail());
        officerResponse.setAddress(officer.getAddress());
        officerResponse.setPassword(officer.getPassword());
        officerResponse.setUserName(officer.getUserName());
        officerResponse.setStatus(officer.getStatus());
        officerResponse.setCellNo(officer.getCellNo());
        officerResponse.setSalary(officer.getSalary());
        officerResponse.setJoiningDate(officer.getJoiningDate());
        officerResponse.setLeavingDate(officer.getLeavingDate());
        officerResponse.setCnic(officer.getCnic());
        return officerResponse;
    }

    public static List<OfficerResponse> fromListOfOfficer(List<Officer> officers) {
        List<OfficerResponse> officerResponseList = new ArrayList<>();
        officers.stream().forEach(officer -> {
            OfficerResponse officerResponse = new OfficerResponse();
            officerResponse.setId(officer.getId());
            officerResponse.setName(officer.getName());
            officerResponse.setEmail(officer.getEmail());
            officerResponse.setAddress(officer.getAddress());
            officerResponse.setPassword(officer.getPassword());
            officerResponse.setUserName(officer.getUserName());
            officerResponse.setStatus(officer.getStatus());
            officerResponse.setCellNo(officer.getCellNo());
            officerResponse.setSalary(officer.getSalary());
            officerResponse.setJoiningDate(officer.getJoiningDate());
            officerResponse.setLeavingDate(officer.getLeavingDate());
            officerResponse.setCnic(officer.getCnic());
            officerResponseList.add(officerResponse);
        });

        return officerResponseList;
    }
}
