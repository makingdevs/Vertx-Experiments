package com.makingdevs

import io.vertx.groovy.ext.unit.TestSuite
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.http.HttpClient

def options = [ reporters: [[ to:"console" ]]]

def suite = TestSuite.create("the_test_suite")

suite.before({ context ->
  println "Pruebas aquí"
}).after({ context ->
  println "Terminando las pruebas"
})

def completion = suite.run(options)

