package org.hidetake.gitserver;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

public class MainOptions {
    @Option(name = "-p", metaVar = "port", usage = "listening port (default: 8080)")
    public int port = 8080;

    @Option(name = "-b", metaVar = "host", usage = "listening host (default: all interfaces)")
    public String hostname = "0.0.0.0";

    @Option(name = "-l", usage = "listening on localhost", forbids = "-b")
    public boolean bindLocal = false;

    @Argument(metaVar = "path", usage = "base path of Git repositories (default: current directory)")
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

        if (options.bindLocal) {
            options.hostname = "localhost";
        }

        return options;
    }
}
