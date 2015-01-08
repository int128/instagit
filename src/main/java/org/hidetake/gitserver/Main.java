package org.hidetake.gitserver;

import org.eclipse.jetty.server.Server;
import org.kohsuke.args4j.CmdLineException;

public class Main {
    protected Server server;

    public Main(MainOptions options) {
        server = GitServer.create(options.getBindAddress(), options.getBasePath());
    }

    public static void main(String[] args) throws Exception {
        try {
            MainOptions options = MainOptions.parse(args);
            Main main = new Main(options);
            main.server.start();
            main.server.join();
        } catch (CmdLineException e) {
            System.err.print("usage:");
            e.getParser().printSingleLineUsage(System.err);
            System.err.println();
            e.getParser().printUsage(System.err);
            System.exit(1);
        }
    }
}
