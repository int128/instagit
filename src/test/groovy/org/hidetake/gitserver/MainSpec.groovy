package org.hidetake.gitserver

import org.eclipse.jetty.server.ServerConnector
import spock.lang.Specification

class MainSpec extends Specification {

    def "server should listen on localhost:8080 at default"() {
        given:
        def options = MainOptions.parse()
        def main = new Main(options);

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

}
