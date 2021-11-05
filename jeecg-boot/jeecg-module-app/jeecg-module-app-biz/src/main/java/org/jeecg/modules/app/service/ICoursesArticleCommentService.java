package org.jeecg.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.entity.CoursesArticleComment;
import org.jeecg.modules.app.vo.CoursesArticleCommentVo;

import java.util.List;

/**
 * @Description: 文章评论表
 * @Author: jeecg-boot
 * @Date:   2021-06-20
 * @Version: V1.0
 */
public interface ICoursesArticleCommentService extends IService<CoursesArticleComment> {
    /** 查询文章评论 */
    List<CoursesArticleCommentVo> queryArticleComment(String articleId);

    /** 删除文章评论及子评论 */
    int deletesArticleComment(String id);

    /** 添加评论点赞*/
    int addArticleCommentLike(String commentId,String userId);

    /** 删除评论点赞*/
    int deleteArticleCommentLike(String commentId,String userId);

    /** 获取子评论数*/
    int getArticleCommentNum(String commentId);

    /** 获取评论点赞数*/
    int getArticleCommentLikeNum(String commentId);

    /** 判断评论点赞*/
    String queryArticleCommentLike(String commentId,String userId);

    /** 获取文章评论数*/
    int queryArticleCommentNum(String articleId);


}
