package com.example.frontend.entity.implemented;

import com.example.frontend.entity.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static com.example.frontend.entity.Mandatory.Features.Haskell.*;

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
                var out = new StringBuilder();
                var err = new StringBuilder();
                try (BufferedReader input =
                             new BufferedReader(new
                                     InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = input.readLine()) != null) {
                        out.append(line).append("\n");
                    }
                }
                try (BufferedReader inputerr =
                             new BufferedReader(new
                                     InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = inputerr.readLine()) != null) {
                        err.append(line).append("\n");
                    }
                }
                if (process.exitValue() != 0)
                {
                    var res = new myExecutionReport(false);
                    res.setOut_(err.toString());
                    return res;

                }
                var report = new myExecutionReport(true);
                report.setOut_(out.toString());
                return report;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return new myExecutionReport(false);
            }



        } else if (RUN.equals(this.featureType_)) {

                ProcessBuilder p = new ProcessBuilder("sh", "-c" , "./a.out");

            try {
                var process = p.start();
                process.waitFor();
                var out = new StringBuilder();
                try (BufferedReader input =
                             new BufferedReader(new
                                     InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = input.readLine()) != null) {
                        out.append(line).append("\n");
                    }
                }
                if (process.exitValue() != 0)
                {
                    var res = new myExecutionReport(false);
                    res.setOut_(out.toString());
                    return res;

                }
                var report = new myExecutionReport(true);
                report.setOut_(out.toString());
                return report;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return new myExecutionReport(false);
            }
        }
        return new myExecutionReport(false);
    }
}