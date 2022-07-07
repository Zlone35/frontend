package com.example.frontend.entity.implemented;

import com.example.frontend.entity.Project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.frontend.entity.Mandatory.Haskell.BUILD;
import static com.example.frontend.entity.Mandatory.Haskell.RUN;


public class myFeatureHaskell extends myFeature {

    public myFeatureHaskell(Type featureType) {
        super(featureType);
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {

        //TODO Later build and run : check if already built
        if (BUILD.equals(this.featureType_)) {
            StringBuilder command = new StringBuilder("ghc");
            for (Object elt : params) {
                command.append(" ").append(elt.toString());
            }
            command.append(" -o a.out");
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("sh", "-c", command.toString());
            try {
                var process = processBuilder.start();
                process.waitFor();
                var out = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                var report = new myExecutionReport(true);
                report.setOut_(out);
                return report;
            } catch (Exception e) {
                return new myExecutionReport(false);

            }
        } else if (RUN.equals(this.featureType_)) {
            try {
                Process p = new ProcessBuilder("sh", "-c" , "./a.out").start();
                p.waitFor();
                var out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                var report = new myExecutionReport(true);
                report.setOut_(out);
                return report;
            } catch (Exception e) {
                return new myExecutionReport(false);
            }
        }
        return new myExecutionReport(false);
    }
}