package org.hidetake.gitserver;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class MainOptions {
    @Option(name = "-p", metaVar = "port", usage = "listening port (default: 8080)")
    private int port = 8080;

    @Option(name = "-b", metaVar = "host", usage = "listening host (default: all interfaces)")
    private String hostname = null;

    @Option(name = "-l", usage = "listening on localhost", forbids = "-b")
    private boolean bindLocal = false;

    @Argument(metaVar = "path", usage = "base path of Git repositories (default: current directory)")
    private String basePath = ".";

    private InetSocketAddress bindAddress;

    private MainOptions() {}

    public String getBasePath() {
        return basePath;
    }

    public InetSocketAddress getBindAddress() {
        return bindAddress;
    }

    private void computeBindAddress() {
        if (bindLocal) {
            bindAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), port);
        } else if (hostname == null) {
            bindAddress = new InetSocketAddress(port);
        } else {
            bindAddress = new InetSocketAddress(hostname, port);
        }
    }

    static MainOptions parse(String[] args) throws CmdLineException {
        MainOptions options = new MainOptions();
        CmdLineParser parser = new CmdLineParser(options);
        parser.parseArgument(args);
        options.computeBindAddress();
        return options;
    }
}
