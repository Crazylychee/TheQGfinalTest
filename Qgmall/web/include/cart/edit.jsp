<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>


<title>分类管理</title>
<div class="workingArea">
    <div class="panel panel-warning addDiv">
        <div class="panel-heading">修改个人信息</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="foreeditInfo" enctype="application/www-form-urlencoded">
                <table class="addTable">
                    <tr>
                        <td>修改用户名</td>
                        <td><input  id="name" name="name" type="name" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>修改电话</td>
                        <td><input  id="number" name="number" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>修改地址</td>
                        <td><input id="destination" name="destination" type="destination" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>修改邮箱</td>
                        <td><input id="email" name="email" type="email" placeholder="邮箱" type="text"></td>

                    </tr>
                    <tr>
                        <td>修改头像</td>
                        <td>
                            <input id="userPic" accept="image/*" type="file" name="filepath" />
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="submit" value="提交"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

</div>
<div class="footer">
</div>

</body>
</html>
