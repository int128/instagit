package org.hidetake.gitserver

import org.kohsuke.args4j.CmdLineException
import spock.lang.Specification
import spock.lang.Unroll

class MainOptionSpec extends Specification {

    def "it should be default options"() {
        when:
        def options = MainOptions.parse()

        then:
        !options.bindAll
        options.port == 8080
        options.hostname == 'localhost'
        options.basePath == '.'
    }

    def "option -a should listen on all interfaces"() {
        when:
        def options = MainOptions.parse('-a')

        then:
        options.bindAll
        options.hostname == '0.0.0.0'
    }

    def "option -b should set listening host"() {
        when:
        def options = MainOptions.parse('-b', '1.2.3.4')

        then:
        options.hostname == '1.2.3.4'
    }

    def "option -b should set listening port"() {
        when:
        def options = MainOptions.parse('-p', '80')

        then:
        options.port == 80
    }

    def "option -r should set listening port"() {
        when:
        def options = MainOptions.parse('-r', '/')

        then:
        options.basePath == '/'
    }

    @Unroll
    def "it should be error if #arguments are given: #list"() {
        when:
        MainOptions.parse(list as String[])

        then:
        thrown(CmdLineException)

        where:
        list                    | arguments
        ['-h']                  | 'unknown option'
        ['-b']                  | 'less argument'
        ['-p']                  | 'less argument'
        ['-r']                  | 'less argument'
        ['-p', 'some']          | 'invalid type of argument'
        ['-b', '1.2.3.4', '-a'] | 'invalid combination'
        ['wrong']               | 'extra argument'
        ['wrong', 'bad']        | 'extra arguments'
    }

}
