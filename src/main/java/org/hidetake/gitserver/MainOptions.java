package org.hidetake.gitserver;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

public class MainOptions {
    @Option(name = "-p", usage = "listening port (default: 8080)")
    public int port = 8080;

    @Option(name = "-b", usage = "listening host (default: localhost)")
    public String hostname = "localhost";

    @Option(name = "-a", usage = "listening on all interfaces", forbids = "-b")
    public boolean bindAll = false;

    @Argument(usage = "base path of Git repositories (default: current directory)")
    protected List<String> arguments = new ArrayList<String>();

    public String basePath = ".";

    static MainOptions parse(String[] args) throws CmdLineException {
        MainOptions options = new MainOptions();
        CmdLineParser parser = new CmdLineParser(options);
        parser.parseArgument(args);
        if (options.arguments.size() == 1) {
            options.basePath = options.arguments.get(0);
        } else if (options.arguments.size() > 1) {
            throw new CmdLineException(parser, "wrong arguments");
        }

        if (options.bindAll) {
            options.hostname = "0.0.0.0";
        }

        return options;
    }
}
