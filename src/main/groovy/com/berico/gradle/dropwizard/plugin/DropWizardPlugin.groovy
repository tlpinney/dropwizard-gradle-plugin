package com.berico.gradle.dropwizard.plugin

import org.gradle.api.artifacts.Configuration
import org.gradle.api.internal.file.copy.CopyAction;
import org.gradle.api.internal.file.copy.CopySpecImpl;
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.bundling.Jar
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
		addFatJarTask()
	}

	private void addPluginConvention() {
		pluginExtension = new DropWizardPluginExtension(project)
		project.convention.plugins.dropwizard = pluginExtension
	}


	private void addJavaExecTask( ) {
		def run = project.tasks.add("run", JavaExec)
		run.description = "Runs this project as a JVM application"
		run.group = "dropwizard"
		run.classpath = project.sourceSets.main.runtimeClasspath
		run.conventionMapping.main = { pluginExtension.mainClassName }
	}

	private void addFatJarTask() {
		def tasks = project.getTasksByName("jar", false)
		for( i in tasks ) {
			Jar jar = (Jar)i
			CopySpecImpl lib = jar.getCopyAction().rootSpec.addFirst().into('lib')

			lib.addChild().from(project.configurations.runtime)
		}
	}
}
