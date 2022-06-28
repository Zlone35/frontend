package com.example.frontend.service.implemented;

import com.example.frontend.myide.MyIde;
import com.example.frontend.entity.*;
import com.example.frontend.entity.implemented.*;
import com.example.frontend.service.NodeService;
import com.example.frontend.service.ProjectService;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.example.frontend.entity.Mandatory.Aspects.*;
import static com.example.frontend.entity.Node.Types.*;

public class myProjectService implements ProjectService {
    @Override
    public Project load(Path root) {
        Node rootNode = new myNode(root, FOLDER);
        Set<Aspect> aspectSet = new HashSet<Aspect>();
        aspectSet.add(new myAspect(ANY));

        // root : "."
        // or
        // root : "./"
        File file = root.toFile();
        if (file.isDirectory()) {
            if (Path.of(root.toString() + "/pom.xml").toFile().exists()) {
                aspectSet.add(new myAspect(Mandatory.Aspects.MAVEN));
            }
            if (Path.of(root.toString() + "/.git").toFile().exists()) {
                aspectSet.add(new myAspect(Mandatory.Aspects.GIT));
            }
        }

        return new myProject(rootNode, aspectSet);
    }

    @Override
    public Feature.ExecutionReport execute(Project project, Feature.Type featureType, Object... params) {
        Optional<Feature> feature = project.getFeature(featureType);
        if (feature.isPresent()) {
            return feature.get().execute(project, params);
        }
        else {
            return new myExecutionReport(false);
        }
    }

    @Override
    public NodeService getNodeService() {
        return this.nodeService_;
    }

    public MyIde.Configuration getConfiguration_() {
        return this.configuration_;
    }

    public myProjectService(MyIde.Configuration configuration) {
        this.nodeService_ = new myNodeService();
        this.configuration_ = configuration;
    }

    private final NodeService nodeService_;
    private final MyIde.Configuration configuration_;
}
