package com.example.frontend.entity.implemented;

import com.example.frontend.entity.Aspect;
import com.example.frontend.entity.Feature;

import java.util.ArrayList;
import java.util.List;

import com.example.frontend.entity.Mandatory;

public class myAspect implements Aspect {
    @Override
    public Type getType() {
        return aspectType_;
    }

    @Override
    public List<Feature> getFeatureList() {
        // maybe we could improve to genericity
        List<Feature> featureList = new ArrayList<>();
        switch (this.aspectType_) {
            case ANY:
                for (Feature.Type featureType : Mandatory.Features.Any.values()) {
                    featureList.add(new myFeatureAny(featureType));
                }
                break;
            case MAVEN:
                for (Feature.Type featureType : Mandatory.Features.Maven.values()) {
                    featureList.add(new myFeatureMaven(featureType));
                }
                break;
            case GIT:
                for (Feature.Type featureType : Mandatory.Features.Git.values()) {
                    featureList.add(new myFeatureGit(featureType));
                }
                break;
        }
        return featureList;
        // get a list of enum values from an enum
        // List<Enum> enumValues = new ArrayList<Enum>(EnumSet.allOf(Enum.class));
    }

    public myAspect(Mandatory.Aspects aspectType) {
        this.aspectType_ = aspectType;
    }

    private final Mandatory.Aspects aspectType_;
}
