package com.example.frontend.entity.implemented;

import com.example.frontend.entity.Node;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class myNode implements Node {
    @Override
    public Path getPath() {
        return this.path_;
    }

    @Override
    public Type getType() { return this.type_; }

    private Type getTypePath(Path myNodePath) {
        File file = myNodePath.toFile();

        if (file.isDirectory()) {
            return Types.FOLDER;
        }
        else if (file.isFile()) {
            return Types.FILE;
        }
        else {
            // boolean exists =      file.exists();      // Check if the file exists
            // case the file doesn't exist
            return null;
        }
    }

    @Override
    public List<@NotNull Node> getChildren() {
        List<@NotNull Node> childList = new ArrayList<>();
        if (this.getType() == Types.FOLDER) {
            // this.path_ cannot be null
            for (final File fileEntry : Objects.requireNonNull(this.path_.toFile().listFiles())) {
                Path myNodePath = Path.of(fileEntry.toString());
                Type myNodeType = getTypePath(myNodePath);
                childList.add(new myNode(myNodePath, myNodeType));
            }
        }
        return childList;
    }

    public myNode(Path path, Type type) {
        this.path_ = path;
        this.type_ = type;
    }
    private final Path path_;
    private final Type type_;
}
