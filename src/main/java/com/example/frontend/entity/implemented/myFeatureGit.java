package com.example.frontend.entity.implemented;

import com.example.frontend.entity.Project;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.*;

import java.io.File;

import static com.example.frontend.entity.Mandatory.Features.Git.*;

public class myFeatureGit extends myFeature {
    public myFeatureGit(Type featureType) {
        super(featureType);
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            String path = (project.getRootNode().getPath().toString() + "/" + ".git/");
            Git myGit = Git.open(new File(path));
            if (ADD.equals(this.featureType_)) {
                AddCommand cmd = myGit.add();
                for (Object elt : params) {
                    cmd.addFilepattern(elt.toString());
                }
                cmd.call();
            } else if (PULL.equals(this.featureType_)) {
                PullCommand cmd = myGit.pull();
                cmd.setRemote("origin");
                cmd.setRemoteBranchName("master");
                cmd.call();
            } else if (COMMIT.equals(this.featureType_)) {
                CommitCommand cmd = myGit.commit();
                cmd.setMessage(StringUtils.join(params, " "));
                cmd.call();
            } else if (PUSH.equals(this.featureType_)) {
                PushCommand cmd = myGit.push();
                cmd.call();
            }
            return new myExecutionReport(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new myExecutionReport(false);
        }
    }
}
