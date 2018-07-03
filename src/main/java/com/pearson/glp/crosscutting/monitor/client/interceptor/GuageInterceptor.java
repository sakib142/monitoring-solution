package com.pearson.glp.crosscutting.monitor.client.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.prometheus.client.Gauge;

/**
 * The GuageInterceptor class for Gauge matrics.
 * 
 * @author Md Sakib
 *
 */
public class GuageInterceptor extends HandlerInterceptorAdapter {

	/**
	 * The instance variable endpointURL.
	 */
    private String endpointURL;

    /**
     * GuageInterceptor Constructor.
     * 
     * @param endPoint
     */
    public GuageInterceptor(String endPoint) {
        super();
        this.endpointURL = endPoint;
    }

    /**
     * Gauge object stating in progress requests.
     */
    static final Gauge guageInprogressRequests = Gauge.build()
            .name("inprogress_requests").help("Guage Inprogress requests.")
            .labelNames("method", "endpointURL", "status").register();

    /**
     * This method sets the prerequisites before calculating gauge metrics.
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) {
        guageInprogressRequests.labels(request.getMethod(), endpointURL,
                Integer.toString(response.getStatus())).inc();
        return true;
    }

    /**
     * This method is executed after calculating gauge metrics for status and description.
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception e) {

        guageInprogressRequests.labels(request.getMethod(), endpointURL,
                Integer.toString(response.getStatus())).dec();
    }

}
