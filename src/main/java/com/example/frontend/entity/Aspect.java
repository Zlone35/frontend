package com.example.frontend.entity;

import com.example.frontend.utils.Given;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Given()
public interface Aspect {

    /**
     * @return The type of the Aspect.
     */
    Aspect.Type getType();

    /**
     * @return The list of features associated with the Aspect.
     */
    default @NotNull List<Feature> getFeatureList() {
        return Collections.emptyList();
    }

    interface Type {

    }
}
