
package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xpath.internal.operations.Equals;
import org.apache.commons.lang.StringUtils;
import sun.security.timestamp.HttpTimestamper;
import tmall.bean.*;
import tmall.dao.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.util.HtmlUtils;

import tmall.servlet.BaseBackServlet;
import tmall.comparator.ProductAllComparator;
import tmall.comparator.ProductDateComparator;
import tmall.comparator.ProductPriceComparator;
import tmall.comparator.ProductReviewComparator;
import tmall.comparator.ProductSaleCountComparator;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class ForeServlet extends BaseForeServlet {
	public String home(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Category> cs = new CategoryDAO().list();
		new ProductDAO().fill(cs);
		new ProductDAO().fillByRow(cs);
		request.setAttribute("cs", cs);
		return "home.jsp";
	}

	public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		name = HtmlUtils.htmlEscape(name);
		System.out.println(name);
		boolean exist = userDAO.isExist(name);

		if (exist) {
			request.setAttribute("msg", "用户名已经被使用,不能使用");
			return "register.jsp";
		}

		User user = new User();
		user.setName(name);
		user.setPassword(password);
		System.out.println(user.getName());
		System.out.println(user.getPassword());
		userDAO.add(user);

		return "@registerSuccess.jsp";
	}

	public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user =null;
		String name = request.getParameter("name");
		name = HtmlUtils.htmlEscape(name);
		String password = request.getParameter("password");

		try {
			user = userDAO.get(name, password);
			Store store = storeDAO.get(user.getId());
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("store", store);

		}catch (NullPointerException e){
			if (null == user) {
				request.setAttribute("msg", "账号密码错误");
				return "login.jsp";
			}
			e.printStackTrace();
		}
		return "@forehome";
	}

	public String product(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		Store s = storeDAO.get(p.getSid());
		List<ProductImage> productSingleImages = productImageDAO.list(p, ProductImageDAO.type_single);
		List<ProductImage> productDetailImages = productImageDAO.list(p, ProductImageDAO.type_detail);
		p.setProductSingleImages(productSingleImages);
		p.setProductDetailImages(productDetailImages);

		List<PropertyValue> pvs = propertyValueDAO.list(p.getId());

		List<Review> reviews = reviewDAO.list(p.getId());

		productDAO.setSaleAndReviewNumber(p);

		request.setAttribute("reviews", reviews);

		request.getSession().setAttribute("s",s);
		request.getSession().setAttribute("p",p);
		request.setAttribute("pvs", pvs);
		return "product.jsp";
	}

	public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
		request.getSession().removeAttribute("user");
		return "@forehome";
	}

	public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if (null != user) {
			return "%success";
		}
		return "%fail";
	}

	public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		System.out.println(name);
		System.out.println(password);
		User user = userDAO.get(name, password);

		if (null == user) {
			return "%fail";
		}
		request.getSession().setAttribute("user", user);
		return "%success";
	}

	public String category(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));

		Category c = new CategoryDAO().get(cid);
		new ProductDAO().fill(c);
		new ProductDAO().setSaleAndReviewNumber(c.getProducts());

		String sort = request.getParameter("sort");
		if (null != sort) {
			switch (sort) {
				case "review":
					Collections.sort(c.getProducts(), new ProductReviewComparator());
					break;
				case "date":
					Collections.sort(c.getProducts(), new ProductDateComparator());
					break;

				case "saleCount":
					Collections.sort(c.getProducts(), new ProductSaleCountComparator());
					break;

				case "price":
					Collections.sort(c.getProducts(), new ProductPriceComparator());
					break;

				case "all":
					Collections.sort(c.getProducts(), new ProductAllComparator());
					break;
			}
		}

		request.setAttribute("c", c);
		return "category.jsp";
	}

	public String search(HttpServletRequest request, HttpServletResponse response, Page page) {
		String keyword = request.getParameter("keyword");
		List<Product> ps = new ProductDAO().search(keyword, 0, 20);
		productDAO.setSaleAndReviewNumber(ps);
		request.setAttribute("ps", ps);
		return "searchResult.jsp";
	}

	public String buyone(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		Product p = productDAO.get(pid);
		int oiid = 0;

		User user = (User) request.getSession().getAttribute("user");
		boolean found = false;
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for (OrderItem oi : ois) {
			if (oi.getProduct().getId() == p.getId()) {
				oi.setNumber(oi.getNumber() + num);
				orderItemDAO.update(oi);
				found = true;
				oiid = oi.getId();
				break;
			}
		}

		if (!found) {
			OrderItem oi = new OrderItem();
			oi.setUser(user);
			oi.setNumber(num);
			oi.setProduct(p);
			orderItemDAO.add(oi);
			oiid = oi.getId();
		}
		return "@forebuy?oiid=" + oiid;
	}


	public String buy(HttpServletRequest request, HttpServletResponse response, Page page) {
		String[] oiids = request.getParameterValues("oiid");
		List<OrderItem> ois = new ArrayList<>();
		float total = 0;

		for (String strid : oiids) {
			int oiid = Integer.parseInt(strid);
			OrderItem oi = orderItemDAO.get(oiid);
			total += oi.getProduct().getPromotePrice() * oi.getNumber();
			ois.add(oi);
		}

		request.getSession().setAttribute("ois", ois);
		request.setAttribute("total", total);
		return "buy.jsp";
	}

	public String addCart(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		int num = Integer.parseInt(request.getParameter("num"));

		User user = (User) request.getSession().getAttribute("user");
		boolean found = false;

		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for (OrderItem oi : ois) {
			if (oi.getProduct().getId() == p.getId()) {
				oi.setNumber(oi.getNumber() + num);
				orderItemDAO.update(oi);
				found = true;
				break;
			}
		}


		if (!found) {
			OrderItem oi = new OrderItem();
			oi.setUser(user);
			oi.setNumber(num);
			oi.setProduct(p);
			orderItemDAO.add(oi);
		}
		return "%success";
	}

	public String cart(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		request.setAttribute("ois", ois);
		return "cart.jsp";
	}

	public String changeOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if (null == user) {
			return "%fail";
		}

		int pid = Integer.parseInt(request.getParameter("pid"));
		int number = Integer.parseInt(request.getParameter("number"));
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for (OrderItem oi : ois) {
			if (oi.getProduct().getId() == pid) {
				oi.setNumber(number);
				orderItemDAO.update(oi);
				break;
			}

		}
		return "%success";
	}

	public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if (null == user) {
			return "%fail";
		}
		int oiid = Integer.parseInt(request.getParameter("oiid"));
		orderItemDAO.delete(oiid);
		return "%success";
	}

	public String createOrder(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");


		String address = request.getParameter("address");
		String post = request.getParameter("post");
		String receiver = request.getParameter("receiver");
		String mobile = request.getParameter("mobile");
		String userMessage = request.getParameter("userMessage");


		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
		Order order = new Order();
		order.setOrderCode(orderCode);
		order.setAddress(address);
		order.setPost(post);
		order.setReceiver(receiver);
		order.setMobile(mobile);
		order.setUserMessage(userMessage);
		order.setCreateDate(new Date());
		order.setUser(user);
		order.setStatus(OrderDAO.waitPay);

		orderDAO.add(order);

		List<OrderItem> ois = (List<OrderItem>) request.getSession().getAttribute("ois");
		float total = 0;
		for (OrderItem oi : ois) {
			oi.setOrder(order);
			orderItemDAO.update(oi);
			total += oi.getProduct().getPromotePrice() * oi.getNumber();
		}

		return "@forealipay?oid=" + order.getId() + "&total=" + total;
	}

	public String alipay(HttpServletRequest request, HttpServletResponse response, Page page) {
		return "alipay.jsp";
	}

	public String payed(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.get(oid);
		order.setStatus(OrderDAO.waitDelivery);
		order.setPayDate(new Date());
		new OrderDAO().update(order);
		request.setAttribute("o", order);
		return "payed.jsp";
	}

	public String bought(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		List<Order> os = orderDAO.list(user.getId(), OrderDAO.delete);

		orderItemDAO.fill(os);

		request.setAttribute("os", os);
		return "bought.jsp";
	}

	public String confirmPay(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		orderItemDAO.fill(o);
		request.setAttribute("o", o);
		return "confirmPay.jsp";
	}

	public String orderConfirmed(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setStatus(OrderDAO.waitReview);
		o.setConfirmDate(new Date());
		orderDAO.update(o);
		return "orderConfirmed.jsp";
	}


	public String deleteOrder(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setStatus(OrderDAO.delete);
		orderDAO.update(o);
		return "%success";
	}

	public String review(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		orderItemDAO.fill(o);
		Product p = o.getOrderItems().get(0).getProduct();
		List<Review> reviews = reviewDAO.list(p.getId());
		productDAO.setSaleAndReviewNumber(p);
		request.setAttribute("p", p);
		request.setAttribute("o", o);
		request.setAttribute("reviews", reviews);
		return "review.jsp";
	}

	public String doreview(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setStatus(OrderDAO.finish);
		orderDAO.update(o);
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);

		String content = request.getParameter("content");

		content = HtmlUtils.htmlEscape(content);

		User user = (User) request.getSession().getAttribute("user");
		Review review = new Review();
		review.setContent(content);
		review.setProduct(p);
		review.setCreateDate(new Date());
		review.setUser(user);
		reviewDAO.add(review);

		return "@forereview?oid=" + oid + "&showonly=true";
	}



	public String editInfo(HttpServletRequest request, HttpServletResponse response, Page page){

		User user = (User) request.getSession().getAttribute("user");
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("email"));
		System.out.println(request.getParameter("number"));
		System.out.println(!StringUtils.isEmpty(request.getParameter("name")));
		if (!StringUtils.isEmpty(request.getParameter("name"))){
			user.setName(request.getParameter("name"));}
		else {
			user.setName("空");
		}
		if (!StringUtils.isEmpty(request.getParameter("destination"))){
			user.setDestination(request.getParameter("destination"));}
		else{
			user.setDestination("空");
		}
		if (!StringUtils.isEmpty(request.getParameter("number"))){
			user.setNumber(request.getParameter("number"));}
		else{
			user.setNumber("空");
		}
		if (!StringUtils.isEmpty(request.getParameter("email"))){
			user.setEmail(request.getParameter("email"));}
		else{
			user.setEmail("空");
		}
		new UserDAO().update(user);


		return "personpage.jsp";
	}


	public String personalPicUpdate(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User) request.getSession().getAttribute("user");
		//上传文件的输入流
		InputStream is = null;
		//提交上传文件时的其他参数
		Map<String,String> params = new HashMap<>();

		//解析上传，保存参数到params中
		is =  parseUpload(request, params);

		//根据上传的参数生成productImage对象
		String type= params.get("type");
		int id = user.getId();

		//生成文件
		String fileName = user.getId()+ ".jpg";
		String imageFolder;
		String imageFolder_small=null;
		String imageFolder_middle=null;

		imageFolder = request.getSession().getServletContext().getRealPath("img/user");
		imageFolder_small = request.getSession().getServletContext().getRealPath("img/user_small");
		imageFolder_middle = request.getSession().getServletContext().getRealPath("img/user_middle");
		//创建新文件 imageFolder 是用于存储图像文件的文件夹的路径，fileName 是文件的名称
		File f = new File(imageFolder, fileName);
		//调用 f.getParentFile() 方法获取文件的父文件夹，并对其进行创建，以创建新的文件夹
		f.getParentFile().mkdirs();

		// 从输入流中复制文件
		try {
			//检查输入流 is 是否为 null，并且输入流 is 的可用性是否为 0
			if(null!=is && 0!=is.available()){
				//创建一个 FileOutputStream 对象 fos,并将其指向文件 f
				try(FileOutputStream fos = new FileOutputStream(f)){
					byte b[] = new byte[1024 * 1024];
					int length = 0;
					//:从输入流 is 中读取字节，并将其存储在字节数组 b 中。如果读取的字节数不是 -1，
					// 则说明读取成功。
					while (-1 != (length = is.read(b))) {
						fos.write(b, 0, length);
					}
					//将写入的字节立即写入文件
					fos.flush();
					//通过如下代码，把文件保存为jpg格式
					BufferedImage img = ImageUtil.change2jpg(f);
					//将转换后的图像文件写入文件 f 中，保存为 jpg 格式
					ImageIO.write(img, "jpg", f);

					File f_small = new File(imageFolder_small, fileName);
					File f_middle = new File(imageFolder_middle, fileName);

					ImageUtil.resizeImage(f,56,56,f_small);
					ImageUtil.resizeImage(f, 217, 190, f_middle);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return "personpage.jsp";
	}

	public String registerstore(HttpServletRequest request, HttpServletResponse response, Page page){ return "registerstore.jsp"; }
	public String registerS(HttpServletRequest request, HttpServletResponse response,Page page){
		//		//获取user对象
		User user = (User) request.getSession().getAttribute("user");
//		if(storeDAO.isExist(user.getId())){
//			return "storepage.jsp";
//		};

		//得到user的id
		int id = user.getId();
		//建立一个商店
		Store store = new Store();

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		store.setUid(id);
		//在数据库中添加商店
		storeDAO.add(user,name,description);
		request.getSession().setAttribute("store", store);
		return "@registerSuccess.jsp";
	}

	public String storepage(HttpServletRequest request, HttpServletResponse response, Page page)
	{return "storepage.jsp";}
	public String personpage(HttpServletRequest request, HttpServletResponse response, Page page){return "personpage.jsp";}

	public String staradd(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User) request.getSession().getAttribute("user");
		Store store = (Store) request.getSession().getAttribute("s");
		System.out.println(store.getUid());
		if(!starDAO.isExist(user.getId(),store.getUid())) {
			try {
				System.out.println(user.getId());
				starDAO.add(user.getId(), store.getUid());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return "%success";
		}

		return "%fail";
	}
	public String stardelete(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User) request.getSession().getAttribute("user");
		Store store = (Store) request.getSession().getAttribute("s");
		System.out.println(store.getUid());
		if(starDAO.isExist(user.getId(),store.getUid())) {
			try {
				System.out.println(user.getId());
				starDAO.delete(user.getId(), store.getUid());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return "%success";
		}

		return "%fail";
	}




}
