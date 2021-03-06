package com.lambdalab.gradle

import com.lambdalab.gradle.tasks.*

import groovy.io.FileType
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ThriftPluginTest extends Specification {

    Project project = ProjectBuilder.builder().build()
    File thriftDirectory = new File("src/test/thrift/")
    File destinationDirectory = new File("src/gen/scala")

    def setup() {
        project.apply plugin: 'thrift-plugin'
        // TODO set language
    }

    def cleanup(){
        if (destinationDirectory.exists()) {
            destinationDirectory.eachFile{it.deleteDir()}
        }
    }

    def "compileScrooge is available    "() {
        expect:
        project.tasks.compileScrooge instanceof ThriftCompile
    }

    def "compile builds 2 scala files"() {
        given:
        ThriftCompile compileThrift = project.tasks.compileScrooge
        compileThrift.output = destinationDirectory
        compileThrift.source = thriftDirectory.listFiles().toList()

        when:
        compileThrift.compile()

        then: "There should be two scala files generated"
        def count = 0
        destinationDirectory.traverse(
                type: FileType.FILES,
                nameFilter: ~/.*\.scala/){ File file ->
            count++
        }
        count == 2
    }

    def "compile builds 2 scala files with finagle options set"() {

        given:
        ThriftCompile compileThrift = project.tasks.compileScrooge
        compileThrift.output = destinationDirectory
        compileThrift.source = thriftDirectory.listFiles().toList()

        when:
        compileThrift.compile()

        then: "There should be two scala files generated"
        def count = 0
        destinationDirectory.traverse(
                type: FileType.FILES,
                nameFilter: ~/.*\.scala/){ File file ->
            count++
        }
        count == 2
    }
}
