package ru.mail.service.model;

import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.service.jsonview.Views;

import java.util.List;

public class AjaxResponseDirectory {

    @JsonView(Views.Public.class)
    private
    String msg;
    @JsonView(Views.Public.class)
    private
    String code;
    @JsonView(Views.Public.class)
    private List <FileStructureAjax> files;
    @JsonView(Views.Public.class)
    private String backButton;
    @JsonView(Views.Public.class)
    private String parent;
    @JsonView(Views.Public.class)
    private String path;

    public AjaxResponseDirectory(String msg, String code, List<FileStructureAjax> files, String backButton, String parent) {
        this.msg = msg;
        this.code = code;
        this.files = files;
        this.backButton = backButton;
        this.parent = parent;
    }
    public AjaxResponseDirectory (String code, List<FileStructureAjax> files, String backButton, String parent) {
        this.code = code;
        this.files = files;
        this.backButton = backButton;
        this.parent = parent;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<FileStructureAjax> getFiles() {
        return files;
    }

    public void setFiles(List<FileStructureAjax> files) {
        this.files = files;
    }

    public String getBackButton() {
        return backButton;
    }

    public void setBackButton(String backButton) {
        this.backButton = backButton;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "AjaxResponseDirectory{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", files=" + files +
                ", backButton='" + backButton + '\'' +
                ", parent='" + parent + '\'' +
                '}';
    }
}
