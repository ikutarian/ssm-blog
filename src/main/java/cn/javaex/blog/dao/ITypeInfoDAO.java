package cn.javaex.blog.dao;

import cn.javaex.blog.view.TypeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ITypeInfoDAO {

    /**
     * 查询所有文章分类
     */
    List<TypeInfo> list();

    /**
     * 插入一条新的数据
     *
     * @param sort 排序
     * @param name 分类名称
     */
    int insert(@Param("sort") String sort,
               @Param("name") String name);

    /**
     * 更新一条数据
     *
     * @param id   主键
     * @param sort 排序
     * @param name 分类名称
     */
    int update(@Param("id") String id,
               @Param("sort") String sort,
               @Param("name") String name);

    /**
     * 批量删除文章分类
     */
    int delete(@Param("idArr") String[] idArr);

    TypeInfo selectById(String id);
}
