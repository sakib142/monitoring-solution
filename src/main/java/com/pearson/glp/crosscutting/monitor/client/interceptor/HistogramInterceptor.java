package com.pearson.glp.crosscutting.monitor.client.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.prometheus.client.Histogram;

/**
 * Interceptor class for Histogram matrics.
 * 
 * @author Md Sakib
 *
 */
public class HistogramInterceptor extends HandlerInterceptorAdapter {

	/**
	 * The instance variable endpointURL.
	 */
    private String endpointURL;

    /**
     * HistogramInterceptor Constructor.
     * 
     * @param endPoint
     */
    public HistogramInterceptor(String endPoint) {
        super();
        this.endpointURL = endPoint;
    }

    /**
     * The Histogram object showing request latency.
     */
    static final Histogram histogramRequestLatency = Histogram.build()
            .buckets(.01, .02, .03, .04, 1, 5, 10, 48, 50, 60)
            .name("requests_latency_seconds")
            .help("Request latency in seconds.")
            .labelNames("method", "endpointURL", "status").register();

    /**
     * The method sets the data before calculating histogram latency.
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) {
        histogramRequestLatency.labels(request.getMethod(), endpointURL,
                Integer.toString(response.getStatus())).startTimer();
        return true;
    }

    /**
     * This method observes request duration.
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {

        histogramRequestLatency
                .labels(request.getMethod(), endpointURL,
                        Integer.toString(response.getStatus()))
                .startTimer().observeDuration();
    }

}
