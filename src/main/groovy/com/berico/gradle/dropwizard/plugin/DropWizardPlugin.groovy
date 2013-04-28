package com.berico.gradle.dropwizard.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.bundling.Tar
import eu.appsatori.gradle.fatjar.FatJarPlugin


class DropWizardPlugin implements Plugin<Project> {


	private Project project

	private DropWizardPluginExtension pluginExtension
	
	static final String TASK_DIST_TAR_NAME = "distTar"
	
	static final String DROPWIZ_GROUP = "dropwizard"

	public void apply(Project project) {
		project.extensions.create('dropwizard', DropWizardPluginExtension)
		this.project = project
		project.apply(plugin: 'java')
		project.plugins.apply FatJarPlugin
		addJavaExecTask()
		addFatJarTask()
		addArchiveTask()
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
	
	def addArchiveTask( ) {
		def archiveTask = project.tasks.add(TASK_DIST_TAR_NAME, Tar )
		archiveTask.description = "Bundles the project as a JVM application with libs and OS specific scripts."
        archiveTask.group = DROPWIZ_GROUP
        archiveTask.conventionMapping.baseName = { project.name }
        def baseDir = { archiveTask.archiveName - ".${archiveTask.extension}" }
        archiveTask.into(baseDir) {
            with(configureDistSpec())
        }
	}
	
	private CopySpec configureDistSpec() {
		def distSpec = project.copySpec {}
		def jar = project.tasks["fatJar"]
		//def startScripts = project.tasks[TASK_START_SCRIPTS_NAME]

		distSpec.with {
			from(project.file("src/dist"))

			into("lib") {
				from(jar)
			}
			//into("bin") {
			//	from(startScripts)
			//	fileMode = 0755
			//}
		}

		distSpec
	}


}
