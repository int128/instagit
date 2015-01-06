package org.hidetake.gitserver;

import org.eclipse.jetty.server.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = GitServer.create(8080, "..");
        server.start();
        server.join();
    }
}
