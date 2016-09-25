package org.example.filter;

import javax.servlet.*;
import java.util.*;
import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TimeTrackFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TimeTrackFilter.class);
    private FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig)
            throws ServletException {

        this.filterConfig = filterConfig;
    }

    public void destroy() {

        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Date startTime, endTime;
        double totalTime;

        startTime = new Date();

        // Forward the request to the next resource in the chain

        chain.doFilter(request, response);

        // -- Process the response -- \\

        // Calculate the difference between the start time and end time
        endTime = new Date();
        totalTime = endTime.getTime() - startTime.getTime();
        totalTime = totalTime / 1000; //Convert from milliseconds to seconds

        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);


        logger.debug("");
        logger.debug("===============");
        logger.debug("Total elapsed time is: " + totalTime + " seconds.");
        logger.debug("===============");

        // Log the resulting string
        writer.flush();
        filterConfig.getServletContext().
                log(sw.getBuffer().toString());

    }
}