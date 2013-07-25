package org.jenkins.client;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class JobTemplateLocator {
	Configuration freemarkerConfiguration;
	TemplateLoader classLoader;

	public JobTemplateLocator() {
		freemarkerConfiguration = new Configuration();
		classLoader = new ClassTemplateLoader(getClass(), "/");
		freemarkerConfiguration.setTemplateLoader(classLoader);
		BeansWrapper objectWrapper = new DefaultObjectWrapper();
		freemarkerConfiguration.setObjectWrapper(objectWrapper);

	}

	@Test
	public void tstMe() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cloneUrl", "someurl");
		params.put("description", "Hi Moussa!");
		params.put("username", "John");
		params.put("password", "secret");
		String jobXml = renderTemplate("org/jenkins/client/next-release-job.ftl", params);
		System.out.println(jobXml);
	}

	public Template getTemplate(String templateName) throws IOException {

		File file = new File(templateName);
		if (file.exists()) {

			// this is a file
			freemarkerConfiguration.setTemplateLoader(new FileTemplateLoader(
					file.getParentFile()));
			templateName = file.getName();
		} else {

			// just use the classloader
			freemarkerConfiguration.setTemplateLoader(classLoader);
		}
		Template template = freemarkerConfiguration.getTemplate(templateName);

		if (template == null) {
			throw new IOException("Could not find template " + templateName);
		}
		return template;
	}

	public String renderTemplate(String templateName,
			Map<String, Object> parameters) throws IOException {

		Template template = getTemplate(templateName);
		return renderTemplate(template, parameters);
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
