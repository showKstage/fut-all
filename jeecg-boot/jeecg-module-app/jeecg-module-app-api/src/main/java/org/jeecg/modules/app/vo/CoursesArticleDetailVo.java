package org.jeecg.modules.app.vo;

import lombok.Data;
import org.jeecg.modules.app.entity.CoursesArticleComment;
import org.jeecg.modules.app.entity.CoursesArticleDetail;
import org.jeecg.modules.app.entity.CoursesTeacher;

import java.io.Serializable;
import java.util.List;

@Data
public class CoursesArticleDetailVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 文章作者 */
    CoursesTeacher coursesTeacher;
    /** 文章详情 */
    CoursesArticleDetail coursesArticleDetail;
    /** 评论内容 */
    List<CoursesArticleCommentVo> coursesArticleCommentVo;
    /** 相关阅读文章推荐 */
    CoursesArticleDetail relatedArticle;

    /**文章id*/
    private String articleId;

    /**文章标题*/
    private String articleTitle;

    /**文章图片*/
    private String articlePicture;

    /**文章作者*/
    private String teacherName;

    /**阅读量*/
    private int visit;

}
