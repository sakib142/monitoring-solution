package com.pearson.glp.crosscutting.monitor.client.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.prometheus.client.Summary;

/**
 * Interceptor class for Summary matrics.
 * 
 * <p>Summary metric, to track the size of events.
 * 
 * @author Md Sakib
 *
 */
public class SummaryInterceptor extends HandlerInterceptorAdapter {

	/**
	 * The instance variable endpointURL.
	 */
    private String endpointURL;

    /**
     * SummaryInterceptor Constructor.
     * 
     * @param endPoint
     */
    public SummaryInterceptor(String endPoint) {
        super();
        this.endpointURL = endPoint;
    }

    /**
     * The static final variable REQ_PARAM_TIMIMG.
     */
    private static final String REQ_PARAM_TIMING = "timing";

    /**
     * Summary object stating response time in ms.
     */
    private static final Summary responseTimeInMs = Summary.build()
            .name("http_response_time_milliseconds")
            .labelNames("method", "endpointURL", "status")
            .help("Request completed time in milliseconds").register();

    /**
     * The  method sets the timing of the request before handling any request.
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) {
        request.setAttribute(REQ_PARAM_TIMING, System.currentTimeMillis());
        return true;
    }

    /**
     * The method sets the response time of the request after request handling.
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
        Long timingAttr = (Long) request.getAttribute(REQ_PARAM_TIMING);
        long completedTime = System.currentTimeMillis() - timingAttr;
        responseTimeInMs
                .labels(request.getMethod(), endpointURL,
                        Integer.toString(response.getStatus()))
                .observe(completedTime);
    }
}
