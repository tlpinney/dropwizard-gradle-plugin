package com.berico.gradle.dropwizard.plugin

import org.gradle.api.Project

class DropWizardPluginExtension {

	String projectName
	String mainClassName
	
	public DropWizardPluginExtension( Project project ) {
		projectName = project.name
	}

}
