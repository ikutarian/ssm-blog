package cn.javaex.blog.view;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ArticleInfo {

    /**
     * 文章状态：正常
     */
    public static final int STATUS_NORMAL = 1;
    /**
     * 文章状态：回收站
     */
    public static final int STATUS_RECYCLE = 0;

    private String id;
    private String typeId;
    private String title;
    private String content;
    private String contentText;
    private String cover;
    private int viewCount;
    private Date updateTime;
    private int status;
    private TypeInfo typeInfo;
}
