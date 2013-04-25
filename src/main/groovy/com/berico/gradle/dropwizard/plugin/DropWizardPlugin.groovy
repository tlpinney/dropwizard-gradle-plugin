package com.berico.gradle.dropwizard.plugin

import org.gradle.api.Plugin;
import org.gradle.api.Project

class DropWizardPlugin implements Plugin<Project> {

	
	private Project project
	
	public void apply(Project project) {
	
		this.project = project
		project.apply(plugin: 'java')
		
		
	}

	private void addPluginConvention() {
		
	}


}
