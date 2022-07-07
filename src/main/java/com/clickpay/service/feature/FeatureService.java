package com.clickpay.service.feature;

import com.clickpay.model.feature.Feature;
import com.clickpay.repository.feature.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureService implements IFeatureService {

    public final FeatureRepository repo;

    @Autowired
    public FeatureService(final FeatureRepository repo) {
        this.repo = repo;
    }

    @Override
    public Feature findByValue(String value) {
        Feature feature = repo.findByValueAndActive(value, Boolean.TRUE);
        return feature;
    }
}
