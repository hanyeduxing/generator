package com.sf.gis.cds.common.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import com.sf.gis.cds.common.util.XssHttpServletRequestWrapper;

import java.io.IOException;

public class XssFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig = filterConfig;

	}

	public void destroy() {

		this.filterConfig = null;

	}

	// 对request进行包装

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);

	}

}
