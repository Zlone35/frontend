package com.example.frontend.entity.implemented;

import com.example.frontend.entity.Feature;
import com.example.frontend.entity.Project;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.example.frontend.entity.Mandatory.Features.Any.*;

public class myFeatureAny extends myFeature {
    public myFeatureAny(Type featureType) {
        super(featureType);
    }

    public Feature.ExecutionReport clean(Project project) {
        try {
            var arr = Files.readAllLines(Path.of(project.getRootNode().getPath().toAbsolutePath().toString() + "/.myideignore"));

            arr.forEach(e -> {
                File tmp = new File(e);
                if (tmp.exists()) {
                    if (tmp.isDirectory()) {
                        try {
                            FileUtils.deleteDirectory(tmp);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        if (!tmp.delete())
                            throw new RuntimeException("fichier " + e + "pas del");
                    }
                }
            });
            return new myExecutionReport(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new myExecutionReport(false);
        }
    }



    @Override
    public Feature.ExecutionReport execute(Project project, Object... params) {

        if (CLEANUP.equals(this.featureType_)) {
            return clean(project);
        }
        else if (DIST.equals(this.featureType_)) {
            try {
                if (! clean(project).isSuccess())
                    return new myExecutionReport(false);

                var sourceFolderPath = project.getRootNode().getPath().toAbsolutePath();
                var ZipPath =  sourceFolderPath.getParent().toString() + "/" + sourceFolderPath.getFileName() + ".zip";

                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(ZipPath));

                Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<>() {

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        Path fn = dir.getFileName();
                        if (fn != null && fn.toString().startsWith(".")) {
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                        return super.preVisitDirectory(dir, attrs);
                    }
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                        zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                        Files.copy(file, zos);
                        zos.closeEntry();
                        return FileVisitResult.CONTINUE;

                    }
                });
                zos.close();
                return new myExecutionReport(true);
            } catch (Exception e) {
                return new myExecutionReport(false);
            }
        }
        return new myExecutionReport(false);
    }
}

