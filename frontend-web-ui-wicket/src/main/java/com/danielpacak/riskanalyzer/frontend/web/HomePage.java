package com.danielpacak.riskanalyzer.frontend.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.agilecoders.wicket.markup.html.bootstrap.heading.Heading;

public class HomePage extends WebPage {

	public HomePage() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println("IN HELLOW ORLD CONSTURCTON:" + context.getBean("mojnapis"));
		Heading heading = new Heading("wicket-markup-id", Model.of("Heading Title: " + context.getBean("mojnapis")));
		add(heading);

	}

}
