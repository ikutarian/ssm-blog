package cn.javaex.blog.action;

import cn.javaex.blog.service.ArticleInfoService;
import cn.javaex.blog.service.TypeInfoService;
import cn.javaex.blog.view.ArticleInfo;
import cn.javaex.blog.view.Result;
import cn.javaex.blog.view.TypeInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("portal")
public class PortalAction {

    @Autowired
    private ArticleInfoService articleInfoService;
    @Autowired
    private TypeInfoService typeInfoService;

    /**
     * 首页
     */
    @RequestMapping("index.action")
    public String index(ModelMap map,
                        @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                        @RequestParam(value="pageSize", defaultValue="10") int pageSize) {

        Map<String, String> param = new HashMap<>();
        param.put("status", String.valueOf(ArticleInfo.STATUS_NORMAL));

        // pageHelper分页插件
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleInfo> list = articleInfoService.list(param);
        PageInfo<ArticleInfo> pageInfo = new PageInfo<>(list);
        map.put("pageInfo", pageInfo);

        return "portal/index";
    }

    /**
     * 获取文章分类
     */
    @RequestMapping("get_type.json")
    @ResponseBody
    public Result getType() {
        List<TypeInfo> typeList = typeInfoService.list();
        return Result.success().add("typeList", typeList);
    }

    /**
     * 根据文章分类查询所有文章
     */
    @RequestMapping("type.action")
    public String getArticleByTypeId(ModelMap map,
                        @RequestParam("typeId") String typeId,
                        @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                        @RequestParam(value="pageSize", defaultValue="10") int pageSize) {
        Map<String, String> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("status", String.valueOf(ArticleInfo.STATUS_NORMAL));

        // pageHelper分页插件
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleInfo> list = articleInfoService.list(param);
        if (list == null || list.isEmpty()) {
            return "404";
        }
        PageInfo<ArticleInfo> pageInfo = new PageInfo<>(list);
        map.put("pageInfo", pageInfo);

        map.put("typeInfo", typeInfoService.selectById(typeId));

        return "portal/type";
    }

    /**
     * 文章详情
     */
    @RequestMapping("article.action")
    public String article(ModelMap map,
                          @RequestParam("id") String id) {
        ArticleInfo articleInfo = articleInfoService.selectById(id);
        if (articleInfo == null) {
            return "404";
        }
        map.put("articleInfo", articleInfo);

        return "portal/article";
    }

    /**
     * 关于
     */
    @RequestMapping("about.action")
    public String about() {
        return "portal/about";
    }

    /**
     * 搜索文章
     */
    @RequestMapping("search.action")
    public String search(ModelMap map,
                         @RequestParam("keyWord") String keyWord,
                         @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                         @RequestParam(value="pageSize", defaultValue="10") int pageSize) {
        Map<String, String> param = new HashMap<>();
        if (!StringUtils.isEmpty(keyWord)) {
            param.put("keyWord", keyWord.trim());
        }
        param.put("status", String.valueOf(ArticleInfo.STATUS_NORMAL));

        // pageHelper分页插件
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleInfo> list = articleInfoService.list(param);
        PageInfo<ArticleInfo> pageInfo = new PageInfo<>(list);
        map.put("pageInfo", pageInfo);

        // 查询所有文章分类
        map.put("typeList", typeInfoService.list());
        map.put("keyWord", keyWord);

        return "portal/search";
    }
}
