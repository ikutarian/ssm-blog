<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
    <meta charset="utf-8">
    <title>回收站</title>
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
                        <span class="active">回收站</span>
                    </div>
                </div>
                <div class="list-content">
                    <!-- 块元素 -->
                    <div class="block">
                        <h2>文章列表</h2>
                        <!-- 正文内容 -->
                        <div class="main-20">
                            <div style="text-align: right; margin-bottom: 10px;">
                                <!-- 文章分类 -->
                                <select id="type_id" class="no-shadow">
                                    <option value="">请选择</option>
                                    <c:forEach items="${typeList}" var="typeInfo" varStatus="status">
                                        <option value="${typeInfo.id}" <c:if test="${typeInfo.id == typeId}">selected</c:if>>${typeInfo.name}</option>
                                    </c:forEach>
                                </select>
                                <!-- 日期范围 -->
                                <input type="text" id="date2" class="date" style="width: 300px;" value="" readonly/>
                                <!-- 标题检索 -->
                                <input type="text" class="text" id="title" value="${keyWord}" placeholder="检索文章标题"/>
                                <!-- 点击查询按钮 -->
                                <button class="button blue" style="margin-bottom: 5px;" onclick="search()"><span class="icon-search"></span></button>
                            </div>

                            <table id="table" class="table">
                                <thead>
                                    <tr>
                                        <th style="width: 20px;"><input type="checkbox" class="fill listen-1"/> </th>
                                        <th style="width: 60px;">序号</th>
                                        <th>分类</th>
                                        <th>文章标题</th>
                                        <th>撰写日期</th>
                                        <th>阅读量</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${fn:length(pageInfo.list) == 0}">
                                            <tr>
                                                <td colspan="7" style="text-align: center">暂无记录</td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${pageInfo.list}" var="entity" varStatus="status">
                                                <tr>
                                                    <td><input type="checkbox" class="fill listen-1-2" name="id" value="${entity.id}" /> </td>
                                                    <td>${status.index + 1}</td>
                                                    <td>${entity.typeInfo.name}</td>
                                                    <td>${entity.title}</td>
                                                    <td><fmt:formatDate value="${entity.updateTime}" type="date" pattern="yyyy-MM-dd"/></td>
                                                    <td>${entity.viewCount}</td>
                                                </tr>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                            <!-- 分页 -->
                            <div class="page">
                                <ul id="page" class="pagination"></ul>
                            </div>

                            <div class="block no-shadow">
                                <div class="banner">
                                    <p class="tab fixed">批量操作<span class="hint">Batch Operation</span></p>
                                </div>
                                <div class="main" style="margin-bottom: 200px;">
                                    <label zoom="1.2"><input type="radio" class="fill" name="radio" value="back"/>批量还原</label>
                                    <br/>
                                    <label zoom="1.2"><input type="radio" class="fill" name="radio" value="delete"/>彻底删除</label>
                                    <br/>
                                    <button id="submit" class="button green" style="margin-top: 20px"><span class="icon-check"></span>提交</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script>
    // 文章分类
    javaex.select({
        id : "type_id",
        isSearch: false
    });

    // 日期范围
    var startDate = "";
    var endDate = "";
    javaex.date({
        id : "date2",		// 承载日期组件的id
        monthNum : 2,		// 2代表选择范围日期
        alignment: "right",
        startDate : "${startDate}",	// 开始日期
        endDate : "${endDate}",		// 结束日期
        // 重新选择日期之后返回一个时间对象
        callback : function(rtn) {
            startDate = rtn.startDate;
            endDate = rtn.endDate;
        }
    });

    // 分页
    var currentPage = "${pageInfo.pageNum}";
    var pageCount = "${pageInfo.pages}";
    javaex.page({
        id : "page",
        pageCount : pageCount,	// 总页数
        currentPage : currentPage,// 默认选中第几页
        position : "right",
        // 返回当前选中的页数
        callback:function(rtn) {
            search(rtn.pageNum);
        }
    });

    function search(pageNum) {
        if (pageNum == undefined) {
            pageNum = 1;
        }
        // 文章分类
        var typeId = $("#type_id").val();
        // 文章标题
        var keyWord = $("#title").val();

        window.location.href = "list_recycle.action"
            + "?pageNum=" + pageNum
            + "&typeId=" + typeId
            + "&startDate=" + startDate
            + "&endDate=" + endDate
            + "&keyWord=" + keyWord;
    }

    javaex.select({
        id : "type_id2",
        isSearch: false
    });

    $("#submit").click(function () {
        var idArr = new Array();
        $(':checkbox[name="id"]:checked').each(function () {
            idArr.push($(this).val());
        });

        // 判断至少选择一条记录
        if (idArr.length == 0) {
            javaex.optTip({
                content: "至少选择一条记录",
                type: "error"
            });
            return;
        }

        // 判断选择的是哪一个单选框进行操作
        var opt = $(':radio[name="radio"]:checked').val();
        if (opt == "back") {
            $.ajax({
                url: "update_status.json",
                type: "POST",
                dataType: "json",
                traditional: "true",
                data: {
                    "idArr": idArr,
                    "status": "1"
                },
                success: function (rtn) {
                    if (rtn.code = "000000") {
                        javaex.optTip({
                            content: rtn.message
                        });
                        // 延迟2秒刷新页面
                        setTimeout(function () {
                            window.location.reload();
                        }, 2000);
                    } else {
                        javaex.optTip({
                            content: rtn.message,
                            type: "error"
                        });
                    }
                }
            });
        } else if (opt == "delete") {
            $.ajax({
                url: "delete.json",
                type: "POST",
                dataType: "json",
                traditional: "true",
                data: {
                    "idArr": idArr,
                },
                success: function (rtn) {
                    if (rtn.code = "000000") {
                        javaex.optTip({
                            content: rtn.message
                        });
                        // 延迟2秒刷新页面
                        setTimeout(function () {
                            window.location.reload();
                        }, 2000);
                    } else {
                        javaex.optTip({
                            content: rtn.message,
                            type: "error"
                        });
                    }
                }
            });
        }
    });
</script>
</html>
