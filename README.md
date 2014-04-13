# JsCompRun

This is a tool that uses
[JSCompiler/Closure Compiler](closure-compiler.googlecode.com)'s 
dependency resolution logic to take Closure-style sources, give them
in dependency order (per specified on entry points), and run them
on Rhino.

## Why?

Because then you can run unit tests without the browser extremely quickly.
Particularly useful to be able to run a big suite while you're developing
as a smoke test before you run on heavyweight browser images.

## Status

The scripts load, order, and run on Rhino.

Some changes to Closure's test runner are necessary to run correctly in Rhino. 
Those changes are in progress.
Also, any code that makes assumptions about the environment that are not present
in Rhino (the DOM is present, in the global scope, for example) will fail.

## Usage:

<pre>
java -jar path/to/jscomprun-dev-jar-with-dependencies.jar --entry_point foo.bar.providedEntryPoint script1.js script2.js ...
</pre>

You can also just specify the directories where your sources lives and
JsCompRun will just pull all .js files. You should include the Closure
sources.

<pre>
java -jar path/to/jscomprun-dev-jar-with-dependencies.jar \
  -R path/to/closure-library \
  -R path/to/my-codebase \
  -e mycodebase.providedEntryPoint
</pre>

An *entry point* is a symbol that has been "provided" with
<code>goog.provide()</code>.

## Building

To build the standalone jar, run this from the root directory.

<pre>
mvn clean compile assembly:single
</pre>

You'll need Maven, obviously.
