package org.hidetake.gitserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jgit.http.server.GitServlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;

/**
 * A Git server.
 *
 * @author Hidetake Iwata
 */
public class GitServer {
    private static final Logger LOG = Log.getLogger(GitServer.class);

    /**
     * Create a Git server.
     *
     * @param address listening address
     * @param basePath base path of Git repositories
     * @return a server
     */
    public static Server create(InetSocketAddress address, String basePath) {
        LOG.info("Version={}", version());
        LOG.info("BindAddress={}", address);
        LOG.info("BasePath={}", basePath);

        HandlerList handlers = new HandlerList();
        handlers.addHandler(accessLogHandler());
        handlers.addHandler(repositoryListHandler(basePath));
        handlers.addHandler(gitHandler(basePath));

        Server server = new Server(address);
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
        requestLogHandler.setRequestLog(new NCSARequestLog() {
            @Override
            public void write(String requestEntry) throws IOException {
                LOG.info(requestEntry);
            }
        });
        return requestLogHandler;
    }

    public static String version() {
        InputStream stream = null;
        try {
            stream = GitServer.class.getResourceAsStream("/version");
            if (stream != null) {
                byte[] buffer = new byte[64];
                int read = stream.read(buffer);
                return new String(buffer, 0, read);
            } else {
                LOG.debug("Could not find version resource");
                return "";
            }
        } catch (IOException e) {
            LOG.debug(e);
            return "";
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    LOG.debug(e);
                }
            }
        }
    }

    private GitServer() {}
}
