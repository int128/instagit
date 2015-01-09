package org.hidetake.gitserver

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.eclipse.jetty.server.Server
import spock.lang.Shared
import spock.lang.Specification

class GitServerSpec extends Specification {

    @Shared
    Server server

    @Shared
    String endpoint

    def setupSpec() {
        int port = Utility.pickUpFreePort()
        endpoint = "http://localhost:$port"
        server = GitServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), port), '.')
        server.start()
    }

    def cleanupSpec() {
        server.stop()
    }

    def "server should response a HTML on /"() {
        when:
        def response = new RESTClient(endpoint).get(path: '/') as HttpResponseDecorator

        then:
        response.status == 200
        response.contentType == 'text/html'
        response.entity.contentLength > 100
    }

    def "server should response a JSON on /?json"() {
        when:
        def response = new RESTClient(endpoint).get(path: '/', queryString: 'json') as HttpResponseDecorator

        then:
        response.status == 200
        response.contentType == 'application/json'
        response.entity.contentLength > 10

        and:
        response.data.baseDir == '.'
        response.data.repositories instanceof Collection
    }

}
