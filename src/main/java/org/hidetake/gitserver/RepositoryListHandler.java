package org.hidetake.gitserver;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.RepositoryNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RepositoryListHandler extends HandlerWrapper {
    private final String basePath;

    public RepositoryListHandler(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if ("/".equals(request.getPathInfo())) {
            if ("json".equals(request.getQueryString())) {
                response.setStatus(200);
                response.setContentType("application/json");
                responseJson(response.getWriter());
            } else {
                response.setStatus(200);
                response.setContentType("text/html");
                responseHtml(response.getOutputStream());
            }
            baseRequest.setHandled(true);
        } else {
            super.handle(target, baseRequest, request, response);
        }
    }

    public void responseHtml(OutputStream outputStream) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = RepositoryListHandler.class.getResourceAsStream("/index.html");
            if (inputStream != null) {
                byte[] buffer = new byte[4096];
                for (;;) {
                    int read = inputStream.read(buffer);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(buffer, 0, read);
                }
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public void responseJson(PrintWriter writer) throws IOException{
        File baseDir = new File(basePath);
        File[] baseDirFiles = baseDir.listFiles();
        if (baseDirFiles != null) {
            List<String> repositories = new ArrayList<String>();
            for (File file : baseDirFiles) {
                if (file.isDirectory()) {
                    Git git = null;
                    try {
                        git = Git.open(file);
                        repositories.add(file.getName());
                    } catch (RepositoryNotFoundException e) {
                        System.err.println(e.getLocalizedMessage());
                    } finally {
                        if (git != null) {
                            git.close();
                        }
                    }
                }
            }

            writer.println("{");
            writer.println("  \"baseDir\": \"" + basePath + "\",");
            writer.println("  \"repositories\": [");
            for (Iterator iterator = repositories.iterator();;) {
                if (iterator.hasNext()) {
                    writer.print("    \"" + iterator.next() + "\"");
                    if (iterator.hasNext()) {
                        writer.println(",");
                    } else {
                        writer.println();
                    }
                } else {
                    break;
                }
            }
            writer.println("  ]");
            writer.println("}");
        } else {
            writer.println("{");
            writer.println("  \"baseDir\": \"" + basePath + "\",");
            writer.println("  \"error\": \"Base directory not found\"");
            writer.println("}");
        }
    }
}
