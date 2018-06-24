package cn.javaex.blog.service.impl;

import cn.javaex.blog.dao.IArticleInfoDAO;
import cn.javaex.blog.service.ArticleInfoService;
import cn.javaex.blog.view.ArticleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {

    @Autowired
    private IArticleInfoDAO iArticleInfoDAO;

    @Override
    public List<ArticleInfo> list(Map<String, String> param) {
        return iArticleInfoDAO.list(param);
    }

    @Override
    public ArticleInfo selectById(String id) {
        ArticleInfo articleInfo = iArticleInfoDAO.selectById(id);
        if (articleInfo != null) {
            int newViewCount = articleInfo.getViewCount() + 1;
            articleInfo.setViewCount(newViewCount);
            iArticleInfoDAO.updateViewCount(id, newViewCount);
        }
        return articleInfo;
    }

    @Override
    public void save(ArticleInfo articleInfo) {
        if (StringUtils.isEmpty(articleInfo.getId())) {
            // 新增
            articleInfo.setStatus(ArticleInfo.STATUS_NORMAL);
            articleInfo.setUpdateTime(new Date());
            articleInfo.setViewCount(1);

            iArticleInfoDAO.insert(articleInfo);
        } else {
            // 更新
            articleInfo.setUpdateTime(new Date());

            iArticleInfoDAO.update(articleInfo);
        }
    }

    @Override
    public void updateTypeId(String[] idArr, String typeId) {
        iArticleInfoDAO.updateTypeId(idArr, typeId);
    }

    @Override
    public void updateStatus(String[] idArr, String status) {
        iArticleInfoDAO.updateStatus(idArr, status);
    }

    @Override
    public void delete(String[] idArr) {
        iArticleInfoDAO.delete(idArr);
    }
}
