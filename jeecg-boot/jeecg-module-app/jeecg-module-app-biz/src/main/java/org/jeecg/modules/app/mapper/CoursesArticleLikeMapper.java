package org.jeecg.modules.app.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.app.entity.CoursesArticleLike;

/**
 * @Description: 文章点赞表
 * @Author: jeecg-boot
 * @Date:   2021-06-24
 * @Version: V1.0
 */
public interface CoursesArticleLikeMapper extends BaseMapper<CoursesArticleLike> {
    /** 获取文章点赞数*/
    int queryArticleLikeNum(@Param("articleId") String articleId);

    /** 获取文章收藏数*/
    int queryArticleCollectNum(@Param("articleId")String articleId);

    /** 收藏文章*/
    int addCollectArticle(@Param("id")String id,@Param("userId")String userId,@Param("articleId")String articleId);

    /** 取消收藏文章*/
    int deleteCollectArticle(@Param("userId")String userId,@Param("articleId")String articleId);

    /** 判断收藏文章*/
    String judgeCollectArticle(@Param("userId")String userId,@Param("articleId")String articleId);



}
