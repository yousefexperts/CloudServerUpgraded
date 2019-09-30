package co.insight.starter.firebase.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class CachingHttpHeadersFilter implements Filter {

    private final static long CACHE_PERIOD = TimeUnit.DAYS.toMillis(31L);
    private final static long LAST_MODIFIED = System.currentTimeMillis();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Cache-Control", "max-age=2678400000, public");
        httpResponse.setHeader("Pragma", "cache");
        httpResponse.setDateHeader("Expires", CACHE_PERIOD + System.currentTimeMillis());
        httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED);
        chain.doFilter(request, response);
    }
}
