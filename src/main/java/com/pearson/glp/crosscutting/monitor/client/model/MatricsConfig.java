package com.pearson.glp.crosscutting.monitor.client.model;

import java.util.List;

/**
 * The MatricsConfig class.
 * 
 * <p>Maintains the configuration of metrics.
 * 
 * @author Md Sakib
 *
 */
public class MatricsConfig {

	/**
	 * List Object.
	 */
	private List<Matrics> matrics;

	/**
	 * @return List Object.
	 */
	public List<Matrics> getMatrics() {
		return this.matrics;
	}

	/**
	 * Sets List of matrics.
	 * 
	 * @param matrics
	 */
	public void setMatrics(List<Matrics> matrics) {
		this.matrics = matrics;
	}

}
