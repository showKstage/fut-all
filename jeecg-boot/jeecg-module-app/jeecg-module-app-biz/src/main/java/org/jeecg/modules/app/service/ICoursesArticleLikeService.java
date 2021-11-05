package org.jeecg.modules.app.service;


import org.jeecg.modules.app.entity.CoursesArticleLike;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 文章点赞表
 * @Author: jeecg-boot
 * @Date:   2021-06-24
 * @Version: V1.0
 */
public interface ICoursesArticleLikeService extends IService<CoursesArticleLike> {

    /** 获取文章点赞数*/
    int queryArticleLikeNum(String articleId);

    /** 获取文章收藏数*/
    int queryArticleCollectNum(String articleId);

    /** 收藏文章*/
    int addCollectArticle(String userId,String articleId);

    /** 取消收藏文章*/
    int deleteCollectArticle(String userId,String articleId);

    /** 判断收藏文章*/
    String judgeCollectArticle(String userId,String articleId);

}
