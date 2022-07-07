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

    public String getOut_(){return this.out_;}
    void setOut_(String out){out_ = out;}
    private final boolean success_;
    private String out_;
}
