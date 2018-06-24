<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
    <meta charset="utf-8">
    <title>文章编辑</title>
</head>
<body>
    <div class="wrap">
        <!--头部-->
        <div id="header">
            <c:import url="../header.jsp"/>
        </div>
        <!-- 左侧菜单和主体内容 -->
        <div class="grid-1-7" style="flex: 1;margin:0;">
            <c:import url="../menu.jsp"/>
            <div class="content">
                <!-- 面包屑导航 -->
                <div class="content-header">
                    <div class="breadcrumb">
                        <span>文章管理</span>
                        <span class="divider">/</span>
                        <span class="active">文章编辑</span>
                    </div>
                </div>
                <div class="list-content">
                    <!-- 块元素 -->
                    <div class="block">
                        <!--修饰块元素名称-->
                        <div class="banner">
                            <p class="tab fixed">文章编辑<span class="hint">Article Editors </span></p>
                        </div>

                        <!--正文内容-->
                        <div class="main">
                            <form id="form" action="" method="">
                                <input type="hidden" name="id" value="${id}">
                                <!--文本框-->
                                <div class="unit">
                                    <div class="left">
                                        <p class="subtitle">标题</p>
                                    </div>
                                    <div class="right">
                                        <input type="text" class="text" name="title" data-type="必填" placeholder="请输入文章标题" value="${articleInfo.title}">
                                    </div>
                                    <!--清浮动-->
                                    <span class="clearfix"></span>
                                </div>

                                <div class="divider"></div>

                                <!--下拉选择框-->
                                <div class="unit">
                                    <div class="left"><p class="subtitle">所属分类</p></div>
                                    <div class="right">
                                        <select id="type_id" name="typeId">
                                            <c:forEach items="${typeList}" var="typeInfo" varStatus="status">
                                                <option value="${typeInfo.id}" <c:if test="${articleInfo.typeId == typeInfo.id}">selected</c:if>>${typeInfo.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <!--清浮动-->
                                    <span class="clearfix"></span>
                                </div>

                                <div class="divider"></div>

                                <!--文章封面-->
                                <div class="unit">
                                    <div class="left"><p class="subtitle">文章封面</p></div>
                                    <div class="right">
                                        <a href="javascript:;">
                                            <!-- 图片承载容器 -->
                                            <label id="container" for="upload" style="display: inline-block; width: 132px; height: 74px;">
                                                <c:choose>
                                                    <c:when test="${empty articleInfo.cover}">
                                                        <img src="${pageContext.request.contextPath}/static/javaex/pc/images/default.png" width="100%" height="100%"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${articleInfo.cover}" width="100%" height="100%" />
                                                    </c:otherwise>
                                                </c:choose>

                                            </label>
                                            <input type="file" class="hide" id="upload" accept="image/gif, image/jpeg, image/jpg, image/png" />
                                        </a>
                                        <input type="hidden" id="cover" name="cover" value="">
                                    </div>
                                    <!--清浮动-->
                                    <span class="clearfix"></span>
                                </div>

                                <div class="divider"></div>

                                <!--文本域-->
                                <div class="unit">
                                    <div class="left"><p class="subtitle">内容</p></div>
                                    <div class="right">
                                        <div id="edit" class="edit-container"></div>
                                        <input type="hidden" id="content" name="content" value="">
                                        <input type="hidden" id="contentText" name="contentText" value="">
                                    </div>
                                    <!--清浮动-->
                                    <span class="clearfix"></span>
                                </div>

                                <div class="divider"></div>

                                <!--提交按钮-->
                                <div class="unit" style="width: 800px;">
                                    <div style="text-align: center;">
                                        <!--表单提交时，必须是input元素，并指定type类型为button，否则ajax提交时，会返回error回调函数-->
                                        <input type="button" id="return" class="button no" value="返回" />
                                        <input type="button" id="save" class="button yes" value="保存" />
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script>
    javaex.select({
        id : "type_id",
        isSearch : false
    });

    javaex.upload({
        type : "image",
        url : "upload.json",	// 请求路径
        id : "upload",	// <input type="file" />的id
        param : "file",			// 参数名称，SSM中与MultipartFile的参数名保持一致
        dataType : "url",		// 返回的数据类型：base64 或 url
        callback : function (rtn) {
            // 后台返回的数据
            if (rtn.code == "000000") {
                var imgUrl = rtn.data.imgUrl;
                $("#container").find("img").attr("src", imgUrl);
                $("#cover").val(imgUrl);
            } else {
                javaex.optTip({
                    content: rtn.message,
                    type: "error"
                });
            }
        }
    });

    var content = '${articleInfo.content}';
    javaex.edit({
        id : "edit",
        image : {
            url : "upload.json",	// 请求路径
            param : "file",		// 参数名称，SSM中与MultipartFile的参数名保持一致
            dataType : "url",	// 返回的数据类型：base64 或 url
            rtn : "rtnData",	// 后台返回的数据对象，在前面页面用该名字存储
            imgUrl : "data.imgUrl"	// 根据返回的数据对象，获取图片地址。  例如后台返回的数据为：{code: "000000", message: "操作成功！", data: {imgUrl: "1.jpg"}}
        },
        content : content,	// 这里必须是单引号，因为html代码中都是双引号，会产生冲突
        callback : function(rtn) {
            console.log(rtn);
            $("#content").val(rtn.html);
            $("#contentText").val(rtn.text.substring(0, 100));
        }
    });

    $("#return").click(function () {
        history.back();
    });

    $("#save").click(function () {
        $.ajax({
            url: "save.json",
            type: "POST",
            dataType: "json",
            data: $("#form").serialize(),
            success: function (rtn) {
                if (rtn.code == "000000") {
                    javaex.optTip({
                        content: rtn.message
                    });
                    setTimeout(function () {
                        // 跳转页面
                        window.location.href = "${pageContext.request.contextPath}/article_info/list_normal.action";
                    } ,2000);
                } else {
                    javaex.optTip({
                        content: rtn.message,
                        type: "error"
                    });
                }
            }
        });
    });
</script>
</html>
