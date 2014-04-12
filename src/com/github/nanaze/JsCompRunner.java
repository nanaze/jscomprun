package com.github.nanaze;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.JSModule;
import com.google.javascript.jscomp.JSModuleGraph;

class JsCompRunner {
	static void main(String[] args){
		List<JSModule> moduleList = Lists.newArrayList();
		JSModuleGraph graph = new JSModuleGraph(moduleList);
		
		List<String> entryPoints = Lists.newArrayList();
		List <CompilerInputs> inputs = Lists.newArrayList();
		graph.manageDependencies(entryPoints, inputs);
	}
}
