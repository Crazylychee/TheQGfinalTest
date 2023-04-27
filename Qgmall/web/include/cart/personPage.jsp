<%--
  Created by IntelliJ IDEA.
  User: yc
  Date: 2023/4/26
  Time: 1:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<title>${user.name}的个人中心</title>

<div>
    <button><img src="img/user_small/${user.id}.jpg"/></button>
    <div>
        <p class="site-author-name" itemprop="name">用户名：${user.name}</p>
        <p class="site-author-name" itemprop="name">手机号：${user.number}</p>
        <p class="site-author-name" itemprop="name">住址：${user.destination}</p>
        <p class="site-author-name" itemprop="name">邮箱：${user.email}</p>
    </div>
</div>

<div>
        <a href="editInfo.jsp">修改资料</a>
</div>

