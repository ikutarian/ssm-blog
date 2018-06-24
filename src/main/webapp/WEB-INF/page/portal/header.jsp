<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="grid-1-3 container">
    <div id="logo">
        <a href="${pageContext.request.contextPath}/portal/index.action">我的博客</a>
    </div>
    <div id="nav">
        <ul class="nav">
            <li><a href="${pageContext.request.contextPath}/portal/index.action">首页</a></li>
            <li style="position: relative;">
                <a href="javascript:;">文章目录</a>
                <ul id="typeList" class="classified-nav">
                </ul>
            </li>
            <li><a href="${pageContext.request.contextPath}/portal/about.action">关于我</a></li>
            <li><a href="javascript:;" onClick="openSearch()"><span class="icon-search" style="font-size: 14px;"></span></a></li>
        </ul>
        <div id="search" class="hide">
						<span class="search-field-wrapper form-group">
							<input type="text" class="search" placeholder="搜索…" value="" onkeydown="if (event.keyCode == 13) {search(this.value);}" >
						</span>
            <span id="close-search" class="icon-close" style="font-size: 16px;"></span>
        </div>
    </div>
</div>
<script>
    function search(keyWord) {
        keyWord = keyWord.replace(/(^\s*)|(\s*$)/g, "");
        if (keyWord != "") {
            window.location.href = "${pageContext.request.contextPath}/portal/search.action?keyWord=" + keyWord;
        }
    }

    // 页面一加载就向后台请求文章分类的数据
    $(function () {
        $.ajax({
            url: "${pageContext.request.contextPath}/portal/get_type.json",
            type: "POST",
            dataType: "json",
            data: {},
            success: function (rtn) {
                console.log(rtn);
                var typeList = rtn.data.typeList;
                var html = '';
                for (var i = 0; i < typeList.length; i++) {
                    html += '<li><a href="${pageContext.request.contextPath}/portal/type.action?typeId=' + typeList[i].id + '">' + typeList[i].name + '</a></li>';
                }
                $('#typeList').append(html);
            }
        });
    });
</script>