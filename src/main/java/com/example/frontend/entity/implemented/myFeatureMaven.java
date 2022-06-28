package com.example.frontend.entity.implemented;

import com.example.frontend.entity.Project;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

import static com.example.frontend.entity.Mandatory.Features.Maven.*;


public class myFeatureMaven extends myFeature {

    public myFeatureMaven(Type featureType) {
        super(featureType);
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {

        String shell = "cd " + project.getRootNode().getPath().toString() + " && ";
        String command = "";
        try {

            if (COMPILE.equals(this.featureType_)) {
                command = (shell + "mvn compile");
            }
            else if (CLEAN.equals(this.featureType_)) {
                command = (shell + "mvn clean");
            }
            else if (TEST.equals(this.featureType_)) {
                command = (shell + "mvn test");
            }
            else if (PACKAGE.equals(this.featureType_)) {
                command = (shell + "mvn package");
            }
            else if (INSTALL.equals(this.featureType_)) {
                command = (shell + "mvn install");
            }
            else if (EXEC.equals(this.featureType_)) {
                command = (shell + "mvn exec");
            }
            else if (TREE.equals(this.featureType_)) {
                command = (shell + "mvn dependency:tree");
                // command = (shell + "mvn dependency:tree >> tree_output.txt");
            }
            if (command == "")
                throw new Exception();

            command += " " + (StringUtils.join(params, " "));

            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
            Process p = processBuilder.start();
            p.waitFor();
            var out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println(out);
            // p.input ... pour avoir le retour du terminal
            if (p.exitValue() == 0)
                return new myExecutionReport(true);
            else
                return new myExecutionReport(false);
        } catch (Exception e) {
            e.printStackTrace();
            return new myExecutionReport(false);
        }

    }
}
