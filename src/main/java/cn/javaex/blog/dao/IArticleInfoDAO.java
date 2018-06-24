package cn.javaex.blog.dao;

import cn.javaex.blog.view.ArticleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IArticleInfoDAO {

    /**
     * 查询所有文章（正常）
     */
    List<ArticleInfo> list(Map<String, String> param);

    ArticleInfo selectById(String id);

    int insert(ArticleInfo articleInfo);

    int update(ArticleInfo articleInfo);

    void updateTypeId(@Param("idArr") String[] idArr,
                      @Param("typeId") String typeId);

    void updateStatus(@Param("idArr") String[] idArr,
                      @Param("status") String status);

    void delete(@Param("idArr") String[] idArr);

    int countByTypeId(@Param("typeIdArr") String[] typeIdArr,
                      @Param("status") int status);

    void deleteByTypeId(@Param("typeIdArr") String[] typeIdArr);

    void updateViewCount(@Param("id") String id,
                         @Param("viewCount")int newViewCount);
}
