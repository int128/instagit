package org.hidetake.gitserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.http.server.GitServlet;

public class GitServer {
    public static Server create(int port, String basePath) {
        Server server = new Server(port);

        HandlerList handlers = new HandlerList();
        handlers.addHandler(accessLogHandler());
        handlers.addHandler(gitHandler(basePath));

        server.setHandler(handlers);
        return server;
    }

    public static Handler gitHandler(String basePath) {
        ServletHandler handler = new ServletHandler();
        ServletHolder holder = handler.addServletWithMapping(GitServlet.class, "/*");
        holder.setInitParameter("base-path", basePath);
        holder.setInitParameter("export-all", "1");
        return handler;
    }

    public static Handler accessLogHandler() {
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        NCSARequestLog requestLog = new NCSARequestLog();
        requestLogHandler.setRequestLog(requestLog);
        return requestLogHandler;
    }
}
