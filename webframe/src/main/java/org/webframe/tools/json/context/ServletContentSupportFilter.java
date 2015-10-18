package org.webframe.tools.json.context;   
import java.io.IOException;   

import javax.servlet.Filter;   
import javax.servlet.FilterChain;   
import javax.servlet.FilterConfig;   
import javax.servlet.ServletException;   
import javax.servlet.ServletRequest;   
import javax.servlet.ServletResponse;   
import javax.servlet.http.HttpServletRequest;   
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤器得到response和request
 * @author 张永葑
 *
 */
public class ServletContentSupportFilter implements Filter {   
	
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    		throws IOException, ServletException {
        HttpServiceContent.setRequest((HttpServletRequest) request);   
        HttpServiceContent.setResponse((HttpServletResponse) response);   
        chain.doFilter(request, response);
    }
    
    public void init(FilterConfig arg0) throws ServletException {}
    
	public void destroy() {}   
} 