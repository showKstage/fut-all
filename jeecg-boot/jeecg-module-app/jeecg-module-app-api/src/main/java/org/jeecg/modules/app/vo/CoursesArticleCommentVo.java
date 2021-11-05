package org.jeecg.modules.app.vo;

import lombok.Data;
import org.jeecg.modules.app.entity.CoursesArticleComment;

import java.util.List;

@Data
public class CoursesArticleCommentVo {
    /** 文章评论内容 */
    CoursesArticleComment coursesArticleComment;
    /** 子评论内容 */
    List<CoursesArticleComment> articleSubCommentList;
}
