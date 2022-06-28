package com.example.frontend.entity.implemented;

import com.example.frontend.entity.Aspect;
import com.example.frontend.entity.Feature;
import com.example.frontend.entity.Node;
import com.example.frontend.entity.Project;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

public class myProject implements Project {
    @Override
    public Node getRootNode() {
        return this.root_;
    }

    @Override
    public Set<Aspect> getAspects() {
        return this.aspects_;
    }

    @Override
    public Optional<Feature> getFeature(Feature.Type featureType) {
        for (Aspect aspect : this.getAspects()) {
            for (Feature feature : aspect.getFeatureList()) {
                if (feature.type() == featureType)
                    return Optional.of(feature);
            }
        }
        return Optional.empty();
    }

    public myProject(Node root, Set<Aspect> aspects) {
        this.root_ = root;
        this.aspects_ = aspects;
    }
    private final Node root_;
    private final Set<Aspect> aspects_;
}
