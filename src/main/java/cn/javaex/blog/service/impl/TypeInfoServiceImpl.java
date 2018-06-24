package cn.javaex.blog.service.impl;

import cn.javaex.blog.dao.IArticleInfoDAO;
import cn.javaex.blog.dao.ITypeInfoDAO;
import cn.javaex.blog.exception.QingException;
import cn.javaex.blog.service.TypeInfoService;
import cn.javaex.blog.view.ArticleInfo;
import cn.javaex.blog.view.TypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TypeInfoServiceImpl implements TypeInfoService {

    @Autowired
    private ITypeInfoDAO iTypeInfoDAO;

    @Autowired
    private IArticleInfoDAO iArticleInfoDAO;

    @Override
    public List<TypeInfo> list() {
        return iTypeInfoDAO.list();
    }

    @Override
    public void save(String[] idArr, String[] sortArr, String[] nameArr) {
        for (int i = 0; i < sortArr.length; i++) {
            // 根据是否有 id 判断这条数据是需要更新还是插入
            if (StringUtils.isEmpty(idArr[i])) {
                // 插入
                iTypeInfoDAO.insert(sortArr[i], nameArr[i]);
            } else {
                // 更新
                iTypeInfoDAO.update(idArr[i], sortArr[i], nameArr[i]);
            }
        }
    }

    @Override
    public void delete(String[] idArr) throws QingException {
        // 判断该分类下是否有正常文章
        int count = iArticleInfoDAO.countByTypeId(idArr, ArticleInfo.STATUS_NORMAL);
        if (count > 0) {
            // 有，那么就禁止删除
            throw new QingException("存在已被占用的分类，无法删除");
        }

        // 先删除该分类下所有回收站的文章
        iArticleInfoDAO.deleteByTypeId(idArr);
        // 再删除分类
        iTypeInfoDAO.delete(idArr);
    }

    @Override
    public TypeInfo selectById(String typeId) {
        return iTypeInfoDAO.selectById(typeId);
    }
}
