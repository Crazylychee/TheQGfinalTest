
package tmall.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.User;
import org.apache.commons.lang.StringUtils;

public class ForeAuthFilter implements Filter{
	
	@Override
	public void destroy() {
		
		
	}

	/**
	 * 这段代码是一个 Java Servlet 的 filter，它
	 * 	用于验证请求是否需要进行身份验证。如果请求的 URL 中
	 * 	包含前缀 /fore,并且该请求不是指向 /foreServlet
	 * 	的，则该 filter 将检查请求的方法是否在列表 noNeedAuthPage
	 * 	 中。如果不在列表中，则将重定向到登录页面。如果请求需要身份验证，
	 * 	 则该 filter 将递归调用 chain.doFilter() 方法，以便将请求传递
	 * 	 给下一个 filter 或 Servlet。否则，该 filter 将结束处理请求，
	 * 	 并将响应重定向到登录页面
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String contextPath=request.getServletContext().getContextPath();

		String[] noNeedAuthPage = new String[]{
				"homepage",
				"checkLogin",
				"register",
				"loginAjax",
				"login",
				"product",
				"category",
				"search"};
		
		
		String uri = request.getRequestURI();
		uri =StringUtils.remove(uri, contextPath);
		if(uri.startsWith("/fore")&&!uri.startsWith("/foreServlet")){
			String method = StringUtils.substringAfterLast(uri,"/fore" );
			if(!Arrays.asList(noNeedAuthPage).contains(method)){
				User user =(User) request.getSession().getAttribute("user");
				if(null==user){
					response.sendRedirect("login.jsp");
					return;
				}
			}
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	
}

