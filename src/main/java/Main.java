import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.http.server.GitServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        HandlerList handlers = new HandlerList();
        handlers.addHandler(accessLogHandler());
        handlers.addHandler(gitHandler());

        server.setHandler(handlers);
        server.start();
        server.join();
    }

    public static Handler gitHandler() {
        ServletHandler handler = new ServletHandler();
        ServletHolder holder = handler.addServletWithMapping(GitServlet.class, "/*");
        holder.setInitParameter("base-path", "..");
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
