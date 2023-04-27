
package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.ProductImageDAO;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class ProductImageServlet extends BaseBackServlet {
	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		//上传文件的输入流
		InputStream is = null;
		//提交上传文件时的其他参数
		Map<String,String> params = new HashMap<>();

		//解析上传，保存参数到params中
		is =  parseUpload(request, params);

		//根据上传的参数生成productImage对象
		String type= params.get("type");
		int pid = Integer.parseInt(params.get("pid"));
		Product p =productDAO.get(pid);

		ProductImage pi = new ProductImage();
		pi.setType(type);
		pi.setProduct(p);
		productImageDAO.add(pi);


		//生成文件
		String fileName = pi.getId()+ ".jpg";
		String imageFolder;
		String imageFolder_small=null;
		String imageFolder_middle=null;
		//看是细节图片还是，简略图
		if(ProductImageDAO.type_single.equals(pi.getType())) {
			imageFolder = request.getSession().getServletContext().getRealPath("img/productSingle");
			imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
			imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
		} else {imageFolder= request.getSession().getServletContext().getRealPath("img/productDetail");}
		//创建新文件 imageFolder 是用于存储图像文件的文件夹的路径，fileName 是文件的名称
		File f = new File(imageFolder, fileName);
		//调用 f.getParentFile() 方法获取文件的父文件夹，并对其进行创建，以创建新的文件夹
		f.getParentFile().mkdirs();

		// 复制文件
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
					//检查产品类型是否等于 type_single。如果是，则执行以下操作：
					if(ProductImageDAO.type_single.equals(pi.getType())){
						File f_small = new File(imageFolder_small, fileName);
						//创建一个新的 File 对象 f_middle,表示中型图像文件的存储路径和名称
						File f_middle = new File(imageFolder_middle, fileName);

						ImageUtil.resizeImage(f, 56, 56, f_small);
						ImageUtil.resizeImage(f, 217, 190, f_middle);
					}


				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		return "@admin_productImage_list?pid="+p.getId();
	}





	@Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		ProductImage pi = productImageDAO.get(id);
		productImageDAO.delete(id);
		
        
        if(ProductImageDAO.type_single.equals(pi.getType())){
        	String imageFolder_single= request.getSession().getServletContext().getRealPath("img/productSingle");
        	String imageFolder_small= request.getSession().getServletContext().getRealPath("img/productSingle_small");
        	String imageFolder_middle= request.getSession().getServletContext().getRealPath("img/productSingle_middle");
        	
        	File f_single =new File(imageFolder_single,pi.getId()+".jpg");
    		f_single.delete();
    		File f_small =new File(imageFolder_small,pi.getId()+".jpg");
    		f_small.delete();
    		File f_middle =new File(imageFolder_middle,pi.getId()+".jpg");
    		f_middle.delete();
        	
        }

        else{
        	String imageFolder_detail= request.getSession().getServletContext().getRealPath("img/productDetail");
    		File f_detail =new File(imageFolder_detail,pi.getId()+".jpg");
    		f_detail.delete();        	
        }
		return "@admin_productImage_list?pid="+pi.getProduct().getId();
	}

	
	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;		
	}

	
	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;	
	}

	
	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p =productDAO.get(pid);
		List<ProductImage> pisSingle = productImageDAO.list(p, ProductImageDAO.type_single);
		List<ProductImage> pisDetail = productImageDAO.list(p, ProductImageDAO.type_detail);
		
		request.setAttribute("p", p);
		request.setAttribute("pisSingle", pisSingle);
		request.setAttribute("pisDetail", pisDetail);
		
		return "admin/listProductImage.jsp";
	}
}
