package cn.javaex.blog.service;

import cn.javaex.blog.view.ArticleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArticleInfoService {

    List<ArticleInfo> list(Map<String, String> param);

    ArticleInfo selectById(String id);

    void save(ArticleInfo articleInfo);

    void updateTypeId(String[] idArr, String typeId);

    void updateStatus(String[] idArr, String status);

    void delete(String[] idArr);
}
