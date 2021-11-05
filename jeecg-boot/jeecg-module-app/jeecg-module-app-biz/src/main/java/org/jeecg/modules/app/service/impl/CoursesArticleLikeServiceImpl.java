package org.jeecg.modules.app.service.impl;

import org.jeecg.modules.app.entity.CoursesArticleLike;
import org.jeecg.modules.app.mapper.CoursesArticleLikeMapper;
import org.jeecg.modules.app.service.ICoursesArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.UUID;

/**
 * @Description: 文章点赞表
 * @Author: jeecg-boot
 * @Date:   2021-06-24
 * @Version: V1.0
 */
@Service
public class CoursesArticleLikeServiceImpl extends ServiceImpl<CoursesArticleLikeMapper, CoursesArticleLike> implements ICoursesArticleLikeService {
    @Autowired
    CoursesArticleLikeMapper coursesArticleLikeMapper;

    @Override
    public int queryArticleLikeNum(String articleId) {
        return coursesArticleLikeMapper.queryArticleLikeNum(articleId) ;
    }

    @Override
    public int queryArticleCollectNum(String articleId) {
        return coursesArticleLikeMapper.queryArticleCollectNum(articleId);
    }

    @Override
    public int addCollectArticle(String userId, String articleId) {
        return coursesArticleLikeMapper.addCollectArticle(UUID.randomUUID().toString(),userId,articleId);
    }

    @Override
    public int deleteCollectArticle(String userId, String articleId) {
        return coursesArticleLikeMapper.deleteCollectArticle(userId, articleId);
    }

    @Override
    public String judgeCollectArticle(String userId, String articleId) {
        return coursesArticleLikeMapper.judgeCollectArticle(userId, articleId);
    }
}
