package com.pearson.glp.crosscutting.monitor.client.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.prometheus.client.Counter;

/**
 * The CounterInterceptor class for Counter metrics.
 * 
 * @author Md Sakib
 *
 */
public class CounterInterceptor extends HandlerInterceptorAdapter {

	/**
	 * The instance variable endpointURL.
	 */
    private String endpointURL;

    /**
     * The CounterInterceptor Constructor.
     * 
     * @param endPoint
     */
    public CounterInterceptor(String endPoint) {
        super();
        this.endpointURL = endPoint;
    }

    /**
     * Counter object stating the total number of requests.
     */
    private static final Counter requestTotal = Counter.build()
            .name("http_requests_total")
            .labelNames("method", "endpointURL", "status")
            .help("Http Request Total").register();

    /**
     * This method is executed after calculation of counter metrics.
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception e) {

        requestTotal.labels(request.getMethod(), endpointURL,
                Integer.toString(response.getStatus())).inc();
    }
}
