package ru.mail.service;

import ru.mail.service.model.FileStructureAjax;

import java.util.List;

public interface FileStructureDAO {
    List<FileStructureAjax> getStructure(String path);
    String getBackButtomPath(String path);
    String getValidPath(String path);
}
