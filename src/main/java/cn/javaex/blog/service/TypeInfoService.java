package cn.javaex.blog.service;

import cn.javaex.blog.exception.QingException;
import cn.javaex.blog.view.TypeInfo;

import java.util.List;

public interface TypeInfoService {

    /**
     * 查询所有文章分类
     */
    List<TypeInfo> list();

    /**
     * 批量更新/插入文章分类
     */
    void save(String[] idArr, String[] sortArr, String[] nameArr);

    /**
     * 批量删除文章分类
     */
    void delete(String[] idArr) throws QingException;

    TypeInfo selectById(String typeId);
}
