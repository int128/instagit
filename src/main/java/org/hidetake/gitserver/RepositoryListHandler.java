package org.hidetake.gitserver;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class RepositoryListHandler extends HandlerWrapper {
    private final String basePath;

    public RepositoryListHandler(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getPathInfo().equals("/")) {
            response.setStatus(200);
            response.setContentType("text/plain");
            PrintWriter writer = response.getWriter();
            response(writer);

            baseRequest.setHandled(true);
        } else {
            super.handle(target, baseRequest, request, response);
        }
    }

    public void response(PrintWriter writer) throws IOException{
        File baseDir = new File(basePath);
        File[] baseDirFiles = baseDir.listFiles();
        if (baseDirFiles != null) {
            writer.append("Directories in path ").append(basePath);
            writer.println();
            writer.println();

            for(File file : baseDirFiles) {
                if (file.isDirectory()) {
                    writer.println(file.getName());
                }
            }
        } else {
            writer.append("Path ").append(basePath).append(" not found");
        }
    }
}
