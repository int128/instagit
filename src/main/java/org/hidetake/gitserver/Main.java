package org.hidetake.gitserver;

import org.eclipse.jetty.server.Server;
import org.kohsuke.args4j.CmdLineException;

import java.net.InetSocketAddress;

public class Main {
    protected Server server;

    public Main(MainOptions options) {
        server = GitServer.create(new InetSocketAddress(options.hostname, options.port), options.basePath);
    }

    public static void main(String[] args) throws Exception {
        try {
            MainOptions options = MainOptions.parse(args);
            Main main = new Main(options);
            main.server.start();
            main.server.join();
        } catch (CmdLineException e) {
            System.err.println("usage: [-a] [-b host] [-p port] [path]");
            e.getParser().printUsage(System.err);
            System.exit(1);
        }
    }
}
