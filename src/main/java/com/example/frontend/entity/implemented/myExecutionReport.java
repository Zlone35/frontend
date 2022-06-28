package com.example.frontend.entity.implemented;

import com.example.frontend.entity.Feature;

public class myExecutionReport implements Feature.ExecutionReport {

    @Override
    public boolean isSuccess() {
        return this.success_;
    }

    public myExecutionReport(boolean success) {
        this.success_ = success;
    }

    private final boolean success_;
}
