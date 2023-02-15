package com.clickpay.dto.area;

import com.clickpay.model.area.SubLocality;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SubLocalityResponse {
    private Long id;
    private String countryName;
    private String cityName;
    private String localityName;
    private String subLocalityName;

    public static List<SubLocalityResponse> mapFromSubLocality(List<SubLocality> subLocalities) {
        return subLocalities.stream().map(e -> {
            SubLocalityResponse data = new SubLocalityResponse();
            data.setId(e.getId());
            data.setCountryName(e.getLocality().getCity().getCountry().getName());
            data.setCityName(e.getLocality().getCity().getName());
            data.setLocalityName(e.getLocality().getName());
            data.setSubLocalityName(e.getName());
            return data;
        }).collect(Collectors.toList());
    }
}
