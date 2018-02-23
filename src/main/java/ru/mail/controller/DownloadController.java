package ru.mail.controller;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class DownloadController extends HttpServlet {

    private static final String ENCODING_TYPE = "UTF-8";
    private static final String DECODING_TYPE = "ISO8859_1";
    private static final String MIME_TYPE = "application/octet-stream";
    private static final String HEADER = "Content-Disposition";
    private static final String EXPLORER_URL = "/explorer?noFile=true";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("coming to DownloadController doGet");
        //getting file by full path
        try {
            String fullPath=req.getParameter("fullPath")+File.separator+req.getParameter("fileName");
            System.out.println("download fullpath: "+ fullPath);
            File file = new File(fullPath);
                String fileName = null;
                //encoding and decoding file name (for russian and other simbols)
                try {
                    fileName = URLEncoder.encode(file.getName(), ENCODING_TYPE);
                    fileName = URLDecoder.decode(fileName, DECODING_TYPE);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //reading thread of bytes - and writenning new file
                ServletContext ctx = getServletContext();
                InputStream fis = null;
                fis = new FileInputStream(file);
                String mimeType = ctx.getMimeType(file.getAbsolutePath());
                resp.setContentType(mimeType != null ? mimeType : MIME_TYPE);
                resp.setContentLength((int) file.length());
                resp.setHeader(HEADER, "attachment; filename=\"" + fileName + "\"");
                ServletOutputStream os = resp.getOutputStream();
                byte[] bufferData = new byte[1024];
                int read = 0;
                while ((read = fis.read(bufferData)) != -1) {
                    os.write(bufferData, 0, read);
                }
                os.flush();
                os.close();
                fis.close();
        } catch (IOException e) {
            System.out.println("file doesn't exist");
            resp.sendRedirect(req.getContextPath()+EXPLORER_URL);
        }
    }
}
