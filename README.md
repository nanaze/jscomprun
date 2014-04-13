# JsCompRun

This is a tool that uses
[JSCompiler/Closure Compiler](closure-compiler.googlecode.com) 
to take Closure-style sources and order them, optionally running them on Rhino.

Usage:

path/to/jscomprun-dev-jar-with-dependencies.jar --entry_point foo.bar.providedEntryPoint script1.js script2.js ...

## Building

To build the standalone jar:

<pre>
mvn clean compile assembly:single
</pre>
