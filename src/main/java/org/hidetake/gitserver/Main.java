package org.hidetake.gitserver;

import org.eclipse.jetty.server.Server;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Main {
    @Option(name = "-p", usage = "listening port (default: 8080)")
    private int port = 8080;

    @Option(name = "-b", usage = "listening host (default: localhost)")
    private String hostname = "localhost";

    @Option(name = "-r", usage = "base path of Git repositories (default: current directory)")
    private String basePath = ".";

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public void run(String[] args) throws Exception {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if(arguments.size() > 0) {
                usage(parser);
                System.exit(1);
            }
        } catch (CmdLineException e) {
            usage(parser);
            System.exit(1);
        }

        Server server = GitServer.create(new InetSocketAddress(hostname, port), basePath);
        server.start();
        server.join();
    }

    private static void usage(CmdLineParser parser) {
        parser.printUsage(System.err);
    }

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }
}
