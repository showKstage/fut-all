package org.jeecg.modules.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesArticleComment;


import java.util.List;

/**
 * @Description: 文章评论表
 * @Author: jeecg-boot
 * @Date:   2021-06-24
 * @Version: V1.0
 */
public interface CoursesArticleCommentMapper extends BaseMapper<CoursesArticleComment> {

    /** 根据文章id查询评论 */
    List<CoursesArticleComment> queryArticleCommentByArticleId(String articleId);

    /** 根据评论id查询子评论 */
    List<CoursesArticleComment> queryArticleSubCommentByCommentId(String articleId);

    /** 删除文章评论及子评论 */
    int deletesArticleComment(String id);

    /** 添加评论点赞*/
    int addArticleCommentLike(@Param("id")String id,@Param("commentId") String commentId, @Param("userId")String userId);

    /** 删除评论点赞*/
    int deleteArticleCommentLike(@Param("commentId") String commentId, @Param("userId")String userId);

    /** 判断评论点赞*/
    String queryArticleCommentLike(@Param("commentId") String commentId, @Param("userId")String userId);

    /** 获取子评论数*/
    int getArticleCommentNum(@Param("commentId") String commentId);

    /** 获取文章点赞数*/
    int getArticleCommentLikeNum(@Param("commentId")String commentId);

    /** 获取文章评论数*/
    int queryArticleCommentNum(@Param("articleId")String articleId);



}
