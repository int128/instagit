package org.hidetake.gitserver

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
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
        main.server.connectors.first().port == 8080
        main.server.connectors.first().host == 'localhost'

        cleanup:
        main.server.stop()
    }

    def "server should response a HTML on /"() {
        given:
        def main = new Main(MainOptions.parse())
        main.server.start()

        when:
        def response = new RESTClient('http://localhost:8080').get(path: '/') as HttpResponseDecorator

        then:
        response.status == 200
        response.contentType == 'text/html'
        response.entity.contentLength > 100

        cleanup:
        main.server.stop()
    }

    def "server should response a JSON on /?json"() {
        given:
        def main = new Main(MainOptions.parse())
        main.server.start()

        when:
        def response = new RESTClient('http://localhost:8080').get(path: '/', queryString: 'json') as HttpResponseDecorator

        then:
        response.status == 200
        response.contentType == 'application/json'
        response.entity.contentLength > 10

        and:
        response.data.baseDir == '.'
        response.data.repositories instanceof Collection

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
