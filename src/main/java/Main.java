import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.http.server.GitServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletHandler handler = new ServletHandler();
        ServletHolder holder = handler.addServletWithMapping(GitServlet.class, "/*");
        holder.setInitParameter("base-path", "..");
        holder.setInitParameter("export-all", "1");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
