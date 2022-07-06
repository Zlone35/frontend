package com.example.frontend;

public class HandlerSaver {
    public TextFile textFile;
    public EditorModel editorModel;
    public Integer index;

    public HandlerSaver(EditorModel editorModel)
    {
        this.editorModel = editorModel;
    }

    public void setTextFile(TextFile textFile)
    {
        this.textFile = textFile;

    }
    public void setIndex(Integer index) {
        this.index = index;
    }
}
