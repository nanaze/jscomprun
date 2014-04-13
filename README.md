# JsCompRun

This is a tool that uses
[JSCompiler/Closure Compiler](closure-compiler.googlecode.com) 
to take Closure-style sources and order them, optionally running them on Rhino.

Usage:

java -jar path/to/jscomprun-dev-jar-with-dependencies.jar --entry_point foo.bar.providedEntryPoint script1.js script2.js ...

You can also just specify the directories where your sources lives and
JsCompRun will just pull all .js files. You should include the Closure
sources.

java -jar path/to/jscomprun-dev-jar-with-dependencies.jar \
  -R path/to/closure-library \
  -R path/to/my-codebase \
  -e mycodebase.providedEntryPoint

An *entry point* is a symbol that has been "provided" with
<code>goog.provide()</code>.

## Building

To build the standalone jar:

<pre>
mvn clean compile assembly:single
</pre>
