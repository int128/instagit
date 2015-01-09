package org.hidetake.gitserver

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.junit.Rule
import org.junit.contrib.java.lang.system.ExpectedSystemExit
import org.junit.contrib.java.lang.system.internal.CheckExitCalled
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

class MainSpec extends Specification {

    @Rule
    final ExpectedSystemExit exit = ExpectedSystemExit.none()

    def "main should start a server on given port"() {
        given:
        int port = Utility.pickUpFreePort()
        def conditions = new PollingConditions()

        when:
        Thread.start { Main.main '-p', "$port" }

        then:
        conditions.within(1) {
            def response = new RESTClient("http://localhost:$port").get(path: '/') as HttpResponseDecorator
            assert response.success
        }
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
