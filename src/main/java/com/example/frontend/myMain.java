package com.example.frontend;

import com.example.frontend.entity.Mandatory;
import com.example.frontend.entity.Node;
import com.example.frontend.entity.Project;
import com.example.frontend.service.ProjectService;

import java.nio.file.Path;
import java.util.List;

public class myMain {
    public static void main(String[] args) {
        System.out.println("Hello World, Java app");

        ProjectService myProjectService = MyIde.init(null);
        Project myProject = myProjectService.load(Path.of("."));
        //Optional<Feature> test = myProject.getFeature(Mandatory.Features.Git.ADD);

        Node root = myProject.getRootNode();
        List<Node> list = root.getChildren();
        Node src = list.stream().filter(node -> {
            return node.getPath().getFileName().toString().equals("src");
        }).findAny().orElse(null);

         Node issou = myProjectService.getNodeService().create(myProject.getRootNode(), "issou", Node.Types.FILE);
        //Node issou = list.stream().filter(node -> {return node.getPath().getFileName().toString().equals("issou");}).findAny().orElse(null);

        issou = myProjectService.getNodeService().move(issou, src);
        issou = myProjectService.getNodeService().move(issou, root);
        myProjectService.getNodeService().delete(issou);

        Node shesh = myProjectService.getNodeService().create(myProject.getRootNode(), "folder", Node.Types.FOLDER);
        issou = myProjectService.getNodeService().create(shesh, "issou", Node.Types.FILE);
        myProjectService.getNodeService().delete(shesh);

        issou = myProjectService.getNodeService().create(myProject.getRootNode(), "issou", Node.Types.FILE);
        issou = myProjectService.getNodeService().update(issou, 0, 10, "issou".getBytes());
        myProjectService.getNodeService().delete(issou);

        // myProject.getFeature(Mandatory.Features.Git.PULL).get().execute(myProject);
        // myProject.getFeature(Mandatory.Features.Git.ADD).get().execute(myProject, "f1.txt", "f2.txt");
        // myProject.getFeature(Mandatory.Features.Git.COMMIT).get().execute(myProject, "test commited");

        Node upd = myProjectService.getNodeService().create(myProject.getRootNode(), "upd", Node.Types.FILE);
        upd = myProjectService.getNodeService().update(upd, 0, 10, "shesh".getBytes());
        upd = myProjectService.getNodeService().update(upd, 0, 10, "test2".getBytes());
        upd = myProjectService.getNodeService().update(upd, 0, 1, "333".getBytes());
        upd = myProjectService.getNodeService().update(upd, 3, 4, "Chi".getBytes());
        myProjectService.getNodeService().delete(upd);
        // test2
        // 333est2
        // 0123456
        // 333Chist2

        myProject.getFeature(Mandatory.Features.Maven.TREE).get().execute(myProject, " -Doutput=tree_output.txt");
        myProject.getFeature(Mandatory.Features.Any.CLEANUP).get().execute(myProject);

    }
}
