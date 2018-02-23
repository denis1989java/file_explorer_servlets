package ru.mail.controller;


import com.google.gson.Gson;
import ru.mail.service.FileStructureDAO;
import ru.mail.service.impl.FileStructureDAOImpl;
import ru.mail.service.model.AjaxResponseDirectory;
import ru.mail.service.model.FileStructureAjax;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class ExplorerServlet extends HttpServlet {
    //getting access to resource file with root directory
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("javaexplorer");
    private final String defaultPath = resourceBundle.getString("rootDirectory");
    //getting access to service methods
    private final FileStructureDAO fileStructureDAO =new FileStructureDAOImpl();

    private static final String ENCODING_TYPE="UTF-8";
    private static final String CONTENT_TYPE="application/json";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("coming to ExplorerServlet doGet");
        req.getRequestDispatcher("/WEB-INF/jsp/directory.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("coming to ExplorerServlet doPost");
        //getting json file from UI with path where user want to go
        AjaxResponseDirectory data = new Gson().fromJson(req.getReader(), AjaxResponseDirectory.class);
        String path="";
        //checking the path? if it is null  - going to default directory from resource bundle file
        if (data == null) {
            path = defaultPath;
        }else{
            if(data.getParent()==null){
                path = defaultPath;
            }else {
                if(data.getPath()!=null){
                    path=data.getParent()+File.separator+data.getPath();
                }else{
                    path=data.getParent();
                }
            }
        }
        System.out.println("path from UI: "+path);
        //getting list of files from directory by path
        List<FileStructureAjax> files = fileStructureDAO.getStructure(path);
        //getting directory path to go to up folder
        String backButton=fileStructureDAO.getBackButtomPath(path);
        String validPath=fileStructureDAO.getValidPath(path);
        try {
            //checking is directory is empty and send different json files
            if (files.isEmpty()) {
                AjaxResponseDirectory result=new AjaxResponseDirectory("List is empty","200",files,backButton,validPath);
                sendResponse(resp, result);
            } else {
                AjaxResponseDirectory result=new AjaxResponseDirectory("204",files,backButton,validPath);
                sendResponse(resp, result);
            }
        }catch (IOException e) {
            System.out.println("ExceptionServlet during request");
            throw new IllegalStateException("Unknown error");
        }
    }

    private void sendResponse(HttpServletResponse resp, AjaxResponseDirectory result) throws IOException {
        //creating json file and setting settings
        String json = new Gson().toJson(result);
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(ENCODING_TYPE);
        resp.getWriter().write(json);
    }
}
