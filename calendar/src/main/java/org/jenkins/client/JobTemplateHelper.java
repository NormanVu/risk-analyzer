package org.jenkins.client;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class JobTemplateHelper {

	Configuration configuration;
	TemplateLoader classLoader;

	public JobTemplateHelper() {
		configuration = new Configuration();
		classLoader = new ClassTemplateLoader(getClass(), "/");
		configuration.setTemplateLoader(classLoader);
		BeansWrapper objectWrapper = new DefaultObjectWrapper();
		configuration.setObjectWrapper(objectWrapper);

	}

	public String renderTemplate(String templateName,
			Map<String, Object> parameters) throws IOException {

		Template template = getTemplate(templateName);
		return renderTemplate(template, parameters);
	}

	public Template getTemplate(String templateName) throws IOException {
		Template template = configuration.getTemplate(templateName);

		if (template == null) {
			throw new IOException("Could not find template " + templateName);
		}
		return template;
	}

	public String renderTemplate(Template template,
			Map<String, Object> parameters) throws IOException {

		StringWriter out = new StringWriter();
		try {
			template.process(parameters, out);
		} catch (TemplateException e) {
			throw new IOException("Could not render template "
					+ template.getName() + " for reason " + e.getMessage());
		}
		return out.toString();

	}

}
