package com.example.frontend.entity.implemented;

import com.example.frontend.entity.Feature;

public abstract class myFeature implements Feature {

    @Override
    public Type type() {
        return this.featureType_;
    }

    public myFeature(Feature.Type featureType) {
        this.featureType_ = featureType;
    }

    protected final Feature.Type featureType_;
}
