package com.example.frontend;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class EditorModel {

    public void save(TextFile textFile) throws IOException {
        PrintWriter write = new PrintWriter(textFile.getFile().toFile());
        write.print("");
        write.close();
        Files.write(textFile.getFile(), textFile.getContent(), StandardOpenOption.CREATE);
    }

    public IOResult<TextFile> load(Path file){
        try {
            List<String> lines = Files.readAllLines(file);
            return new IOResult<TextFile>(true, new TextFile(file, lines));
        } catch (IOException e) {
            e.printStackTrace();
            return new IOResult<TextFile>(false, null);
        }
    }

    public void close()
    {
        System.exit(0);
    }

}
