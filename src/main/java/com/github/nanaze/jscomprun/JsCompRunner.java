package com.github.nanaze.jscomprun;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerInput;
import com.google.javascript.jscomp.JSModule;
import com.google.javascript.jscomp.JSModuleGraph;
import com.google.javascript.jscomp.JSModuleGraph.MissingModuleException;
import com.google.javascript.jscomp.deps.SortedDependencies.CircularDependencyException;
import com.google.javascript.jscomp.deps.SortedDependencies.MissingProvideException;

class JsCompRunner {
	public static void main(String[] args) throws CircularDependencyException,
			MissingModuleException, MissingProvideException {
		List<JSModule> moduleList = Lists.newArrayList();
		JSModuleGraph graph = new JSModuleGraph(moduleList);

		// TODO(nanaze): At present, this does nothing. Fill these from the
		// args/flags.
		List<String> entryPoints = Lists.newArrayList();
		List<CompilerInput> inputs = Lists.newArrayList();
		graph.manageDependencies(entryPoints, inputs);
	}
}
