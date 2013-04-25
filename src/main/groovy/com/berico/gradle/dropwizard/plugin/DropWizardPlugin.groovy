package com.berico.gradle.dropwizard.plugin

import org.gradle.api.tasks.JavaExec
import org.gradle.api.Plugin
import org.gradle.api.Project

class DropWizardPlugin implements Plugin<Project> {

	
	private Project project
	
	private DropWizardPluginExtension pluginExtension
	
	public void apply(Project project) {
	
		this.project = project
		project.apply(plugin: 'java')
		addPluginConvention()
		addJavaExecTask()
		
		
	}

	private void addPluginConvention() {
		pluginExtension = new DropWizardPluginExtension(project)
		project.convention.plugins.dropwizard = pluginExtension
	}


	private void addJavaExecTask( ) {
		def run = project.tasks.add("run", JavaExec)
		run.description = "Runs this project as a JVM application"
		run.group = "application"
		run.classpath = project.sourceSets.main.runtimeClasspath
		run.conventionMapping.main = { pluginExtension.mainClassName }
	}
	
}
