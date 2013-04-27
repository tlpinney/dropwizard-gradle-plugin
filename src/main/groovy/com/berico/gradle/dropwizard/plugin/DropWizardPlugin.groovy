package com.berico.gradle.dropwizard.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec

import eu.appsatori.gradle.fatjar.FatJarPlugin


class DropWizardPlugin implements Plugin<Project> {


	private Project project

	private DropWizardPluginExtension pluginExtension

	public void apply(Project project) {
		project.extensions.create('dropwizard', DropWizardPluginExtension)
		this.project = project
		project.apply(plugin: 'java')
		project.plugins.apply FatJarPlugin
		addJavaExecTask()
		addFatJarTask()
	}


	private void addJavaExecTask( ) {
		def run = project.tasks.add("run", JavaExec)
		run.description = "Runs this project as a JVM application"
		run.group = "dropwizard"
		run.classpath = project.sourceSets.main.runtimeClasspath
		run.conventionMapping.main = { project.dropwizard.mainClassName }
	}

	def addFatJarTask( ) {
		def fatjar = project.tasks.findByName("fatJar")
		fatjar.group = "dropwizard"
	}


}
