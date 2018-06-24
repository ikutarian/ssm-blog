package cn.javaex.blog.action;

import cn.javaex.blog.service.ArticleInfoService;
import cn.javaex.blog.service.TypeInfoService;
import cn.javaex.blog.view.ArticleInfo;
import cn.javaex.blog.view.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("article_info")
public class ArticleInfoAction {

    @Autowired
    private ArticleInfoService articleInfoService;
    @Autowired
    private TypeInfoService typeInfoService;

    @RequestMapping("list_normal.action")
    public String listNormal(ModelMap map,
                             @RequestParam(required = false, value = "typeId") String typeId,
                             @RequestParam(required = false, value = "startDate") String startDate,
                             @RequestParam(required = false, value = "endDate") String endDate,
                             @RequestParam(required = false, value = "keyWord") String keyWord,
                             @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                             @RequestParam(value="pageSize", defaultValue="10") int pageSize) {

        Map<String, String> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
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

        map.put("typeId", typeId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("keyWord", keyWord);
        map.put("status", String.valueOf(ArticleInfo.STATUS_NORMAL));

        return "admin/article_info/list_normal";
    }

    @RequestMapping("list_recycle.action")
    public String listRecycle(ModelMap map,
                              @RequestParam(required = false, value = "typeId") String typeId,
                              @RequestParam(required = false, value = "startDate") String startDate,
                              @RequestParam(required = false, value = "endDate") String endDate,
                              @RequestParam(required = false, value = "keyWord") String keyWord,
                              @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                              @RequestParam(value="pageSize", defaultValue="10") int pageSize) {
        Map<String, String> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        if (!StringUtils.isEmpty(keyWord)) {
            param.put("keyWord", keyWord.trim());
        }
        param.put("status", String.valueOf(ArticleInfo.STATUS_RECYCLE));

        // pageHelper分页插件
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleInfo> list = articleInfoService.list(param);
        PageInfo<ArticleInfo> pageInfo = new PageInfo<>(list);
        map.put("pageInfo", pageInfo);

        // 查询所有文章分类
        map.put("typeList", typeInfoService.list());

        map.put("typeId", typeId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("keyWord", keyWord);
        map.put("status", String.valueOf(ArticleInfo.STATUS_NORMAL));

        return "admin/article_info/list_recycle";
    }

    /**
     * 文章编辑
     */
    @RequestMapping("edit.action")
    public String edit(ModelMap map,
                       @RequestParam(required = false, value = "id") String id) {
        if (!StringUtils.isEmpty(id)) {
            ArticleInfo articleInfo = articleInfoService.selectById(id);
            map.put("articleInfo", articleInfo);
        }

        // 查询所有文章分类
        map.put("typeList", typeInfoService.list());

        map.put("id", id);

        return "admin/article_info/edit";
    }

    /**
     * 上传文件到磁盘（物理路径）
     */
    @RequestMapping("upload.json")
    @ResponseBody
    public Result upload(MultipartFile file, HttpServletRequest request) throws IOException {
        // 文件原名称
        String szFileName = file.getOriginalFilename();
        // 重命名后的文件名称
        String szNewFileName = "";
        // 根据日期自动创建3级目录
        String szDateFolder = "";

        // 上传文件
        if (szFileName != null && szFileName.length() > 0) {
            Date date = new Date();
            szDateFolder = new SimpleDateFormat("yyyy/MM/dd").format(date);
            // 存储文件的物理路径
            String szFilePath = "E:\\IDEAWorkspace\\blog_img\\upload\\" + szDateFolder;
            // 自动创建文件夹
            File f = new File(szFilePath);
            if (!f.exists()) {
                f.mkdirs();
            }

            // 新的文件名称
            szNewFileName = UUID.randomUUID() + szFileName.substring(szFileName.lastIndexOf("."));
            // 新文件
            File newFile = new File(szFilePath + "\\" + szNewFileName);

            // 将内存中的数据写入磁盘
            file.transferTo(newFile);
        }

        return Result.success().add("imgUrl", "http://localhost:8080/upload/" + szDateFolder + "/" + szNewFileName);
    }

    /**
     * 文章保存
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result save(ArticleInfo articleInfo) {
        articleInfoService.save(articleInfo);
        return Result.success();
    }

    /**
     * 批量移动文章到某一个分类
     */
    @RequestMapping("move.json")
    @ResponseBody
    public Result move(@RequestParam("idArr") String[] idArr,
                       @RequestParam("typeId") String typeId) {
        articleInfoService.updateTypeId(idArr, typeId);
        return Result.success();
    }

    /**
     * 批量更新文章状态
     */
    @RequestMapping("update_status.json")
    @ResponseBody
    public Result recycle(@RequestParam("idArr") String[] idArr,
                          @RequestParam("status") String status) {
        articleInfoService.updateStatus(idArr, status);
        return Result.success();
    }

    /**
     * 批量彻底删除文章
     */
    @RequestMapping("delete.json")
    @ResponseBody
    public Result delete(@RequestParam("idArr") String[] idArr) {
        articleInfoService.delete(idArr);
        return Result.success();
    }
}
