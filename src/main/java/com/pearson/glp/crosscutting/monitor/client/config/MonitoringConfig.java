package com.pearson.glp.crosscutting.monitor.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.MetricsServlet;

/**
 * MonitoringConfig class.
 * 
 * <p>Maintains monitoring configuration.
 * 
 * @author Md Sakib
 *
 */
@Configuration
@ConditionalOnClass(CollectorRegistry.class)
class MonitoringConfig {

	/**
	 * 
	 * @return CollectorRegistry object.
	 */
	@Bean
	@ConditionalOnMissingBean
	CollectorRegistry metricRegistry() {
		return CollectorRegistry.defaultRegistry;
	}

	/**
	 * 
	 * @param metricRegistry.
	 * 
	 * @return ServletRegistrationBean object.
	 */
	@Bean
	public ServletRegistrationBean registerPrometheusExporterServlet(CollectorRegistry metricRegistry) {
		return new ServletRegistrationBean(new MetricsServlet(metricRegistry), "/metrics");
	}
}
