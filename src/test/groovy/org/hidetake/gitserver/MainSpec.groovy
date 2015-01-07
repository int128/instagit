package org.hidetake.gitserver

import org.eclipse.jetty.server.ServerConnector
import org.junit.Rule
import org.junit.contrib.java.lang.system.ExpectedSystemExit
import org.junit.contrib.java.lang.system.internal.CheckExitCalled
import spock.lang.Specification

class MainSpec extends Specification {

    @Rule
    final ExpectedSystemExit exit = ExpectedSystemExit.none()

    def "server should listen on localhost:8080 at default"() {
        given:
        def options = MainOptions.parse()
        def main = new Main(options)

        when:
        main.server.start()

        then:
        main.server.started
        main.server.connectors.length == 1
        (main.server.connectors.first() as ServerConnector).port == 8080
        (main.server.connectors.first() as ServerConnector).host == 'localhost'

        cleanup:
        main.server.stop()
    }

    def "unknown option should show usage"() {
        given:
        def stderr = System.err
        def stderrBuffer = new ByteArrayOutputStream()
        System.err = new PrintStream(stderrBuffer)

        exit.expectSystemExit()

        when:
        Main.main '-h'

        then:
        CheckExitCalled e = thrown()
        e.status == 1

        and:
        stderrBuffer.toString('UTF-8').contains('usage:')

        cleanup:
        System.err = stderr
        System.err.println(stderrBuffer.toString('UTF-8'))
    }

}
