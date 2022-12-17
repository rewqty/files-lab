package com.rewqty.fileslab.servlets;

import com.rewqty.fileslab.models.FileModel;
import com.rewqty.fileslab.models.UserProfileModel;
import com.rewqty.fileslab.services.AccountService;

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
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/files")
public class FileViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserProfileModel userProfile = AccountService.getInstance().getUserBySessionId(req.getSession().getId());
        if (userProfile == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        String path = req.getParameter("path");
        if (path == null || path.isEmpty()) {
            path = folderPath();
            resp.sendRedirect(req.getContextPath() + req.getServletPath() + "?path=" + path);
            return;
        }

        req.setAttribute("URL", req.getContextPath() + req.getServletPath() + "?path=");
        req.setAttribute("date", (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")).format(new Date()));

        req.setAttribute("parentFolderPath", (new File(path)).getParent());
        req.setAttribute("folderPath", path);

        req.setAttribute("files", listInfoFiles(path));

        File file = new File(path);
        if (file.isFile()) {
            try (OutputStream out = resp.getOutputStream()) {
                resp.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
                resp.addHeader("Content-Length", Long.toString(file.length()));
                resp.setContentType("application/octet-stream");
                out.write(Files.readAllBytes(file.toPath()));
            }
        } else if (!file.exists()) {
            resp.sendError(404, "File or directory not found");
            return;
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
    
    private ArrayList<FileModel> listInfoFiles(String pathDirectory) throws IOException {
        File directory = new File(pathDirectory);
        File[] listFiles = directory.listFiles();

        if (listFiles != null) {
            ArrayList<FileModel> listInfoFiles = new ArrayList<>();
            for (File file : listFiles) {
                listInfoFiles.add(new FileModel(
                        file.getCanonicalPath() + File.separator,
                        file.getName(),
                        file.isFile() ? file.length() : -1,
                        new Date(file.lastModified())));
            }
            return listInfoFiles;
        } else {
            return null;
        }
    }
}
