package com.danielpacak.riskanalyzer.frontend.web;

import org.apache.wicket.protocol.http.WebApplication;

import de.agilecoders.wicket.Bootstrap;
import de.agilecoders.wicket.settings.BootstrapSettings;

public class FrontendApplication extends WebApplication {

	public FrontendApplication() {

	}

	protected void init() {
		super.init();
		Bootstrap.install(this, new BootstrapSettings());
	}

	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

}