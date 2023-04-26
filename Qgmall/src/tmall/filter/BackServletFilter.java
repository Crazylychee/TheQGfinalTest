package tmall.filter;


import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * @author yc
 * @date 2023/4/24 20:31
 */
public class BackServletFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        //首先强制转换请求和响应对象为 HttpServletRequest 和
        // HttpServletResponse，以便能够访问 Web 应用程序的上下文路径和响应状态码。
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        //获取请求的url
        String contextPath = request.getServletContext().getContextPath();
        String url = request.getRequestURI();
        //StringUtils 类去除 URL 中的上下文路径
        url=StringUtils.remove(url,contextPath);
        //如果 URL 以“/admin_”开头，则该函数将提取 servlet 路径和请求方法，
        // 并将请求转发到一个名为“Servlet”的 Servlet 上，最后返回。
        if (url.startsWith("/admin_")){
            //从请求 URL 中提取 servlet 路径，将请求 URL 从“/”开始提取，直到最后一个“_”为止
            String servletPath =StringUtils.substringBetween(url,"_",
                    "_"+"servlet");
            //从请求 URL 中提取请求方法，将请求 URL 从“/”开始提取，直到最后一个“_”为止
            String method = StringUtils.substringAfterLast(url,"_");
            //将转化的结果存到请求中
            request.setAttribute("method",method);
            //向 Servlet 请求对应的 URL 路径并转到对应servlet
            req.getRequestDispatcher("/"+servletPath).forward(request,response);
            return;
        }
        //否则，该函数将直接调用 FilterChain 的 doFilter() 方法来处理请求和响应。
        chain.doFilter(request, response);
    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}