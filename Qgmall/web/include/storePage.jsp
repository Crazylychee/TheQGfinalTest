<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>


<title>${user.name}的店铺！！</title>

<div>
    <button><img src="img/user_middle/${user.id}.jpg"/></button>
    <div>
        <p class="site-author-name" itemprop="name">店铺名：${store.name}</p>
        <p class="site-author-name" itemprop="name">店铺描述：${store.description}</p>
        <p class="site-author-name" itemprop="name">粉丝数：${store.fans}</p>
    </div>
</div>

<div>
    <a href="editInfoS.jsp">修改资料</a>
</div>