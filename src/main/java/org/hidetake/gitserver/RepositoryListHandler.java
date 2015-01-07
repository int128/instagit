package org.hidetake.gitserver;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.RepositoryNotFoundException;

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
            response(response.getWriter());

            baseRequest.setHandled(true);
        } else {
            super.handle(target, baseRequest, request, response);
        }
    }

    public void response(PrintWriter writer) throws IOException{
        File baseDir = new File(basePath);
        File[] baseDirFiles = baseDir.listFiles();
        if (baseDirFiles != null) {
            writer.append("Repositories in path ").append(basePath);
            writer.println();
            writer.println();

            for(File file : baseDirFiles) {
                if (file.isDirectory()) {
                    try {
                        Git.open(file);
                        writer.append("[Git]  ").append(file.getName());
                        writer.println();
                    } catch (RepositoryNotFoundException e) {
                        writer.append("[None] ").append(file.getName());
                        writer.println();
                    }
                }
            }
        } else {
            writer.append("Path ").append(basePath).append(" not found");
        }
    }
}
