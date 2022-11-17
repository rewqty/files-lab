package com.rewqty.fileslab;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter("path");
        if (path == null) {
            path = folderPath();
            resp.sendRedirect(req.getContextPath() + "/?path=" + path);
        }
        req.setAttribute("URL", req.getContextPath() + "/?path=");
        req.setAttribute("date", (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")).format(new Date()));
        String test = (new File(path)).getParent();
        req.setAttribute("parentFolderPath", (new File(path)).getParent());
        req.setAttribute("folderPath", path);

        String[][] listInfoFiles = listInfoFiles(path);
        req.setAttribute("files", listInfoFiles);

        File file = new File(path);
        if (file.isFile()) {
            try (OutputStream out = resp.getOutputStream()) {
                resp.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
                resp.addHeader("Content-Length", Long.toString(file.length()));
                resp.setContentType("application/octet-stream");
                out.write( Files.readAllBytes(file.toPath()));
            }
        } else if (!file.exists()) {
            resp.sendError(404, "File or directory not found");
        }

        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private String folderPath() throws IOException {
        File file = new File(".");
        return file.getCanonicalPath();
    }
    
    private String[][] listInfoFiles(String pathDirectory) throws IOException {
        File directory = new File(pathDirectory);
        File[] listFiles = directory.listFiles();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if (listFiles != null) {
            String[][] listInfoFiles = new String[listFiles.length][4];
            for (int i = 0; i < listFiles.length; i++) {
                listInfoFiles[i][0] = listFiles[i].getCanonicalPath() + "\\";
                listInfoFiles[i][1] = listFiles[i].getName();
                if(listFiles[i].isFile()) {
                    listInfoFiles[i][2] = listFiles[i].length() + " B";
                }
                listInfoFiles[i][3] = dateFormat.format(listFiles[i].lastModified());
            }
            return listInfoFiles;
        } else {
            return null;
        }
    }
}
