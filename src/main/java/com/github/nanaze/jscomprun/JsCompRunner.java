package com.github.nanaze.jscomprun;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.Argument;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.javascript.jscomp.ErrorManager;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.javascript.jscomp.JSModule;
import com.google.javascript.jscomp.JSModuleGraph;
import com.google.javascript.jscomp.JSModuleGraph.MissingModuleException;
import com.google.javascript.jscomp.LoggerErrorManager;
import com.google.javascript.jscomp.deps.DependencyInfo;
import com.google.javascript.jscomp.deps.JsFileParser;
import com.google.javascript.jscomp.deps.SortedDependencies;
import com.google.javascript.jscomp.deps.SortedDependencies.CircularDependencyException;
import com.google.javascript.jscomp.deps.SortedDependencies.MissingProvideException;

class JsCompRunner {

	private static final Logger logger = Logger.getLogger(JsCompRunner.class
			.getName());

	private static class Flags {
		@Option(name = "-e", usage = "Entry point(s) to the source", aliases = "--entry_point")
		private List<String> entryPoints = null;

		@Argument
		private List<String> sourcePaths = new ArrayList<String>();
	}

	public static void main(String[] args) throws CircularDependencyException,
			MissingModuleException, MissingProvideException, CmdLineException,
			IOException {
		Flags flags = new Flags();
		CmdLineParser parser = new CmdLineParser(flags);
		parser.parseArgument(args);

		// TODO(nanaze): At present, this does nothing. Fill these from the
		// args/flags.

		List<DependencyInfo> dependencyInfoList = Lists.newArrayList();
		for (String path : flags.sourcePaths) {
			ErrorManager errorManager = new LoggerErrorManager(logger);
			JsFileParser jsFileParser = new JsFileParser(errorManager);
			jsFileParser.setIncludeGoogBase(true);

			DependencyInfo dependencyInfo = jsFileParser.parseFile(path, null);

			dependencyInfoList.add(dependencyInfo);
			Preconditions.checkArgument(jsFileParser.didParseSucceed(),
					"Parse failed");

			if (errorManager.getErrorCount() > 0) {
				errorManager.generateReport();
				throw new IllegalStateException("Failed with JsCompiler Errors");
			}
		}

		SortedDependencies<DependencyInfo> sortedDependencies = new SortedDependencies<DependencyInfo>(
				dependencyInfoList);

		Preconditions.checkState(
				flags.entryPoints != null && flags.entryPoints.size() > 0,
				"One or more entry points must be specified.");

		List<DependencyInfo> entryPointInputs = Lists.newArrayList();
		for (String entryPoint : flags.entryPoints) {
			DependencyInfo input = sortedDependencies
					.getInputProviding(entryPoint);
			entryPointInputs.add(input);
		}

		logger.info("Printing source file paths to stderr.");
		List<String> scriptPaths = Lists.newArrayList();
		for (DependencyInfo dependency : sortedDependencies
				.getSortedDependenciesOf(entryPointInputs)) {
			String path = dependency.getName();
			System.out.println(path);
			scriptPaths.add(path);
		}

		runClosureScripts(scriptPaths);
	}

	private static void runClosureScripts(List<String> scriptPaths)
			throws IOException {
		logger.info("Running scripts in Rhino...");

		Context context = Context.enter();
		Scriptable scope = context.initStandardObjects();

		for (String path : scriptPaths) {
			FileReader reader = new FileReader(path);
			context.evaluateReader(scope, reader, path, 1, null);
			reader.close();
		}
	}
}
