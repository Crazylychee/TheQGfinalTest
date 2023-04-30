<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>


<script>
    $(function () {
        // 页面加载事件：解除不可点击的按键状态
        $(".chatput").click(function () {
            var page = "forechatput";
            $.get(
                page,
                function (result) {
                    if ("success" == result) {
                        alert("发送成功！！")
                    } else {
                        alert("发送失败，请重新发送！")
                    }
                }
            );
            return false;
        });
    });

</script>


<div class="chat">
    <div class="chatpage">
        <div class="chatroom">
            <a href="#nowhere" class="selected">客服在线 <span
                    class="productReviewTopReviewLinkNumber">${p.reviewCount}</span> </a>
        </div>

        <div class="productReviewContentPart">
            <c:forEach items="${chatroom1s}" var="r">
                <div class="productReviewItem">

                    <div class="productReviewItemDesc">
                        <div class="productReviewItemContent">
                                ${r.message}
                        </div>
                        <div class="productReviewItemDate"><fmt:formatDate value="${r.createDate}"
                                                                           pattern="yyyy-MM-dd"/></div>
                    </div>
                    <div class="productReviewItemUserInfo">
                            ${r.user1.name}<span class="userInfoGrayPart">用户发送</span>
                    </div>
                    <div style="clear:both"></div>

                </div>
            </c:forEach>
            <c:forEach items="${chatroom2s}" var="t">
                <div class="productReviewItem">

                    <div class="productReviewItemDesc">
                        <div class="productReviewItemContent">
                                ${t.message}
                        </div>
                        <div class="productReviewItemDate"><fmt:formatDate value="${t.createDate}"
                                                                           pattern="yyyy-MM-dd"/></div>
                    </div>
                    <div class="productReviewItemUserInfo">
                            ${t.user1.name}<span class="userInfoGrayPart">用户发送</span>
                    </div>
                    <div style="clear:both"></div>

                </div>
            </c:forEach>
            <form method="post" id="addForm" action="forechatput" enctype="application/www-form-urlencoded">
                <table class="addTable">
                    <tr>
                        <td>发送消息</td>
                        <td><input  id="message" name="message" type="message" class="form-control"></td>
                    </tr>
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