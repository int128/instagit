package org.hidetake.gitserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.http.server.GitServlet;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * A Git server.
 *
 * @author Hidetake Iwata
 */
public class GitServer {
    /**
     * Create a Git server listening on loopback port.
     *
     * @param port listening port
     * @param basePath base path of Git repositories
     * @return a server
     */
    public static Server create(int port, String basePath) {
        return create(new InetSocketAddress(InetAddress.getLoopbackAddress(), port), basePath);
    }

    /**
     * Create a Git server.
     *
     * @param address listening address
     * @param basePath base path of Git repositories
     * @return a server
     */
    public static Server create(InetSocketAddress address, String basePath) {
        Server server = new Server(address);

        HandlerList handlers = new HandlerList();
        handlers.addHandler(accessLogHandler());
        handlers.addHandler(repositoryListHandler(basePath));
        handlers.addHandler(gitHandler(basePath));

        server.setHandler(handlers);
        return server;
    }

    public static Handler repositoryListHandler(String basePath) {
        return new RepositoryListHandler(basePath);
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
