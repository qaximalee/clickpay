package com.clickpay.service.feature;

import com.clickpay.model.feature.Feature;

public interface IFeatureService {
    Feature findByValue(String value);
}
