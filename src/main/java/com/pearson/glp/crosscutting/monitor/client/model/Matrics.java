package com.pearson.glp.crosscutting.monitor.client.model;

/**
 * The Matrics class.
 * 
 * <p>Maintains matrics information.
 * 
 * @author Md Sakib
 *
 */
public class Matrics {

	/**
	 * The instance variable  name.
	 */
    private String name;
    /**
     * The instance String array object.
     */
    private String[] urls;

    /**
     * 
     * @return String  name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets name.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets URLs as String array.
     * 
     * @return String array of URLs.
     */
    public String[] getUrls() {
        return this.urls;
    }

    /**
     * Sets String array of URLs.
     * 
     * @param urls
     */
    public void setUrls(String[] urls) {

        this.urls = urls;
    }

}