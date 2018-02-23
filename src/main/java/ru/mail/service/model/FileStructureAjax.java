package ru.mail.service.model;

import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.service.jsonview.Views;

import java.math.BigInteger;

public class FileStructureAjax {
    @JsonView(Views.Public.class)
    private String fileName;
    @JsonView(Views.Public.class)
    private BigInteger size;
    @JsonView(Views.Public.class)
    private Boolean directory;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }

    public Boolean getDirectory() {
        return directory;
    }

    public void setDirectory(Boolean directory) {
        this.directory = directory;
    }
}
