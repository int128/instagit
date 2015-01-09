package org.hidetake.gitserver

import spock.lang.Specification

class GitServerClassSpec extends Specification {

    def "version() should return version"() {
        when:
        def version = GitServer.version()

        then:
        version.matches(/instagit-(@version@|SNAPSHOT|[0-9\.]+)/)
    }

}
