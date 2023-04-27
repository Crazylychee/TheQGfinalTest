package tmall.servlet;


import tmall.dao.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import tmall.util.Page;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BaseBackServlet extends HttpServlet {

	public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page) ;
	public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page) ;
	public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page) ;
	public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page) ;
	public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page) ;
	
	
	protected CategoryDAO categoryDAO = new CategoryDAO();
	protected OrderDAO orderDAO = new OrderDAO();
	protected OrderItemDAO orderItemDAO = new OrderItemDAO();
	protected ProductDAO productDAO = new ProductDAO();
	protected ProductImageDAO productImageDAO = new ProductImageDAO();
	protected PropertyDAO propertyDAO = new PropertyDAO();
	protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
	protected ReviewDAO reviewDAO = new ReviewDAO();
	protected UserDAO userDAO = new UserDAO();

	/**
	 *
	 * @param request
	 * @param response
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			/*获取分页信息*/
			int start= 0;
			int count = 5;
			try {
				start = Integer.parseInt(request.getParameter("page.start"));
			} catch (Exception e) {
				
			}
			try {
				count = Integer.parseInt(request.getParameter("page.count"));
			} catch (Exception e) {
			}
			Page page = new Page(start,count);
			
			/*借助反射，调用对应的方法*/
			String method = (String) request.getAttribute("method");
			Method m = this.getClass().getMethod(method, HttpServletRequest.class,
					HttpServletResponse.class,Page.class);
			String redirect = m.invoke(this,request, response,page).toString();
			
			/*根据方法的返回值，进行相应的客户端跳转，服务端跳转，或者仅仅是输出字符串*/
			
			if(redirect.startsWith("@"))
			{response.sendRedirect(redirect.substring(1));}
			else if(redirect.startsWith("%"))
			{response.getWriter().print(redirect.substring(1));}
			else
			{request.getRequestDispatcher(redirect).forward(request, response);}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 	用于上传文件的方法
	 */
	public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
			InputStream is =null;
			try {
				//该对象用于创建和管理文件上传过程中使用的磁盘文件对象
	            DiskFileItemFactory factory = new DiskFileItemFactory();
	            //使用该对象的 parseRequest 方法来解析 HTTP 请求中的文件上传请求
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            // 在解析请求时，代码首先设置了上传文件的大小限制为 10M
	            factory.setSizeThreshold(1024 * 10240);
	            //这个集合是干嘛用的
	            List items = upload.parseRequest(request);
	            //然后获取了请求中所有的文件上传请求，并使用迭代器iter来遍历它们
	            Iterator iter = items.iterator();
	            while (iter.hasNext()) {
					//对于每个文件上传请求，使用 FileItem 对象来获取上传文件的输入流
	                FileItem item = (FileItem) iter.next();
	                if (!item.isFormField()) {
	                    // item.getInputStream() 获取上传文件的输入流，如果上传文件不是表单字段，则使用输入流读取上传文件的内容
	                    is = item.getInputStream();
	                } else {
	                	//将获取表单字段的值并将其编码为 UTF-8 格式，并将其存储在 Map 中以供后续使用
	                	String paramName = item.getFieldName();
	                	String paramValue = item.getString();
	                	paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
	                	params.put(paramName, paramValue);
	                }
	            }
	             
	
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return is;
		}
	
	 
	

}
