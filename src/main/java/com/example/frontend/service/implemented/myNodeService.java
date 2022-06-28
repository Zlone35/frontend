package com.example.frontend.service.implemented;

import com.example.frontend.entity.Node;
import com.example.frontend.entity.implemented.myNode;
import com.example.frontend.service.NodeService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.example.frontend.entity.Node.Types.*;

public class myNodeService implements NodeService {
    @Override
    public Node update(Node node, int from, int to, byte[] insertedContent) {
        assert from >= 0;
        assert from <= to;
        assert node.isFile();
        try {
            Path path = node.getPath();
            File file = path.toFile();

            byte[] fileContent = Files.readAllBytes(path);
            FileOutputStream fos = new FileOutputStream(file);
            int len = fileContent.length;
            if (from != 0)
                // [0, from]
                fos.write(fileContent, 0, from);

            fos.write(insertedContent);

            int len2 = len - to;
            if (len > 0 && len2 > 0)
                fos.write(fileContent, to, len2);

            fos.close();
            return node;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Node node) {
        if (node.isFolder()) {
            for (Node subNode : node.getChildren()) {
                delete(subNode);
            }
        }
        File file = node.getPath().toFile();
        return file.delete();
    }

    @Override
    public Node create(Node folder, String name, Node.Type type) {
        Path newNodePath = Path.of(folder.getPath() + "/" + name);
        File newNodeFile = newNodePath.toFile();

        try {
            if (type == FOLDER) {
                if (!newNodeFile.mkdir())
                    throw new RuntimeException("Error on creating new dir with name " + name + " with path " + newNodePath.toString());
            }
            else if (type == FILE) {
                if (!newNodeFile.createNewFile())
                    throw new RuntimeException("Error on creating new file with name " + name + " with path " + newNodePath.toString());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new myNode(newNodePath, type);
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        Path curNodePath = nodeToMove.getPath();
        Path nodeName = nodeToMove.getPath().getFileName();
        Path newNodePath = Path.of(destinationFolder.getPath().toString() + "/" + nodeName.toString());

        if (! curNodePath.toFile().renameTo(newNodePath.toFile()))
            throw new RuntimeException("Error: move node");

        return new myNode(newNodePath, nodeToMove.getType());
    }
}
