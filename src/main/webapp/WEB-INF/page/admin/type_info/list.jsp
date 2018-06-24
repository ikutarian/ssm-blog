<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
    <meta charset="utf-8">
    <title>文章分类</title>
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
                        <span>分类管理</span>
                        <span class="divider">/</span>
                        <span class="active">文章分类</span>
                    </div>
                </div>
                <div class="list-content">
                    <!-- 块元素 -->
                    <div class="block">
                        <h2>文章分类</h2>
                        <!-- 正文内容 -->
                        <div class="main-20">
                            <!-- 表格上方的操作元素，添加，删除，搜索等 -->
                            <div class="operation-wrap">
                                <button id="add" class="button blue"><span class="icon-plus"> 添加</span></button>
                                <button id="save" class="button green"><span class="icon-check"> 保存</span></button>
                                <button id="delete" class="button red"><span class="icon-minus"> 删除</span></button>
                            </div>
                            <table id="table" class="table">
                                <thead>
                                    <tr>
                                        <th><input type="checkbox" class="fill listen-1"/> </th>
                                        <th>显示排序</th>
                                        <th>分类名称</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${list}" var="entity" varStatus="status">
                                        <tr>
                                            <td style="width: 20px"><input type="checkbox" class="fill listen-1-2" name="id" value="${entity.id}" /> </td>
                                            <td style="width: 30px"><input type="text" class="text" name="sort" data-type="整数" error-msg="必须输入正整数" value="${entity.sort}" /></td>
                                            <td><input type="text" class="text" name="name" data-type="必填" placeholder="请输入分类名称" value="${entity.name}" /></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script>
    var idArr = new Array();
    var sortArr = new Array();
    var nameArr = new Array();

    // 动态添加 tr 行
    $("#add").click(function () {
        var html = '';
        html += '<tr>'
        html += '<td style="width: 20px"><input type="checkbox" class="fill listen-1-2" name="id" value="${entity.id}" /> </td>';
        html += '<td style="width: 30px"><input type="text" class="text" name="sort" data-type="正整数" error-msg="必须输入正整数" value="${entity.sort}" /></td>';
        html += '<td><input type="text" class="text" name="name" data-type="必填" placeholder="请输入分类名称" value="" /></td>';
        html += '</tr>';

        $("#table tbody").append(html);
        // 重新渲染
        javaex.render();
    });

    // 保存按钮点击事件
    $("#save").click(function () {
        if (javaexVerify()) {
            idArr = [];
            sortArr = [];
            nameArr = [];

            // id
            $(':checkbox[name="id"]').each(function () {
                idArr.push($(this).val());
            });
            // sort
            $(':input[name="sort"]').each(function () {
                sortArr.push($(this).val());
            });
            // name
            $(':input[name="name"]').each(function () {
                nameArr.push($(this).val());
            });

            console.log(idArr);
            console.log(sortArr);
            console.log(nameArr);

            $.ajax({
                url: "save.json",
                type: "POST",
                dataType: "json",
                traditional: "true",
                data: {
                    "idArr": idArr,
                    "sortArr": sortArr,
                    "nameArr": nameArr
                },
                success: function (rtn) {
                    console.log(rtn);
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

    // 点击删除按钮事件
    $("#delete").click(function () {
        idArr = [];
        // 遍历所有被勾选的复选框
        $(':checkbox[name="id"]:checked').each(function () {
            // 添加主键存在的记录
            var id = $(this).val();
            if (id != "") {
                idArr.push(id);
            }
        });

        // 判断所勾选的是不是新增的空白记录
        if (idArr.length == 0) {
            $(':checkbox[name="id"]:checked').each(function () {
                // 前台刷新去除新增的tr
                $(this).parent().parent().parent().remove();
            });
        } else {
            $.ajax({
                url: "delete.json",
                type: "POST",
                dataType: "json",
                traditional: "true",
                data: {
                    "idArr": idArr
                },
                success: function (rtn) {
                    console.log(rtn);
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
