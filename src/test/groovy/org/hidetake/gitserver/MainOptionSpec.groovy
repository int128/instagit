package org.hidetake.gitserver

import org.kohsuke.args4j.CmdLineException
import spock.lang.Specification
import spock.lang.Unroll

class MainOptionSpec extends Specification {

    def "it should be default options"() {
        when:
        def options = MainOptions.parse()

        then:
        !options.bindLocal
        options.port == 8080
        options.hostname == '0.0.0.0'
        options.basePath == '.'
    }

    def "option -l should listen on localhost"() {
        when:
        def options = MainOptions.parse('-l')

        then:
        options.bindLocal
        options.hostname == 'localhost'
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

    def "extra argument should set base path of repositories"() {
        when:
        def options = MainOptions.parse('/')

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
        ['-p', 'some']          | 'invalid type of argument'
        ['-b', '1.2.3.4', '-l'] | 'invalid combination'
        ['wrong', 'bad']        | 'extra arguments'
    }

}
