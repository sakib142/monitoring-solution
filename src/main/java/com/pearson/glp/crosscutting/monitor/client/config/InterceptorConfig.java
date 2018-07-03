package com.pearson.glp.crosscutting.monitor.client.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.pearson.glp.crosscutting.monitor.client.interceptor.CounterInterceptor;
import com.pearson.glp.crosscutting.monitor.client.interceptor.GuageInterceptor;
import com.pearson.glp.crosscutting.monitor.client.interceptor.HistogramInterceptor;
import com.pearson.glp.crosscutting.monitor.client.interceptor.SummaryInterceptor;
import com.pearson.glp.crosscutting.monitor.client.model.Matrics;
import com.pearson.glp.crosscutting.monitor.client.model.MatricsConfig;

/**
 * InterceptorConfig class.
 * 
 * <p>
 * Maintains interceptor configuration.
 * 
 * @author Md Sakib.
 *
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	/**
	 * Object of Logger class.
	 */
	private ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory
			.getLogger(InterceptorConfig.class);

	/**
	 * The instance variable configHome.
	 * 
	 * Gets the value from properties file.
	 */
	@Value("${config.home}")
	private String configHome;

	/**
	 * The instance variable env.
	 * 
	 * Gets the value from properties file.
	 */
	@Value("${env}")
	private String env;

	/**
	 * Environment object.
	 */
	@Autowired
	private Environment environment;

	/**
	 * This method reads the yml file and maps the metrics accordingly.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		String monitorFlag = environment.getProperty("monitor");

		if (monitorFlag.equalsIgnoreCase("Y")) {
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			try {

				MatricsConfig config = mapper.readValue(
						new File("" + configHome + "/" + env + "/metrics-url-mapping.yml"), MatricsConfig.class);
				List<Matrics> matricsConfigs = config.getMatrics();

				matricsConfigs.forEach(item -> {
					if (item.getName().equalsIgnoreCase("COUNTER")) {

						Arrays.asList(item.getUrls()).forEach(url -> {
							registry.addInterceptor(new CounterInterceptor(url)).addPathPatterns(url.trim());
						});
					} else if (item.getName().equalsIgnoreCase("SUMMARY")) {

						Arrays.asList(item.getUrls()).forEach(url -> {
							registry.addInterceptor(new SummaryInterceptor(url)).addPathPatterns(url.trim());
						});

					} else if (item.getName().equalsIgnoreCase("GUAGE")) {

						Arrays.asList(item.getUrls()).forEach(url -> {
							registry.addInterceptor(new GuageInterceptor(url)).addPathPatterns(url.trim());
						});

					} else if (item.getName().equalsIgnoreCase("HISTOGRAM")) {

						Arrays.asList(item.getUrls()).forEach(url -> {
							registry.addInterceptor(new HistogramInterceptor(url)).addPathPatterns(url.trim());
						});

					}

				});

			} catch (Exception e) {
				logger.error("", e);
			}

		}

	}

}
