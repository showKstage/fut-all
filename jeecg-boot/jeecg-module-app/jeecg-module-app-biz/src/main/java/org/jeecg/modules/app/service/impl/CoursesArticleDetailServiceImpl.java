package org.jeecg.modules.app.service.impl;

import org.jeecg.modules.app.entity.CoursesArticleDetail;
import org.jeecg.modules.app.mapper.CoursesArticleDetailMapper;
import org.jeecg.modules.app.service.ICoursesArticleDetailService;
import org.jeecg.modules.app.vo.CoursesArticleDetailVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 文章详情表
 * @Author: jeecg-boot
 * @Date:   2021-06-20
 * @Version: V1.0
 */
@Service
public class CoursesArticleDetailServiceImpl extends ServiceImpl<CoursesArticleDetailMapper, CoursesArticleDetail> implements ICoursesArticleDetailService {

    @Resource
    private CoursesArticleDetailMapper coursesArticleDetailMapper;

    @Override
    public List<CoursesArticleDetail> queryByArticleTitle(String articleTitle){
        return coursesArticleDetailMapper.queryByArticleTitle(articleTitle);
    }
    @Override
    public List<CoursesArticleDetail>  queryByArticleType(String articleType) {
        return coursesArticleDetailMapper.queryByArticleType(articleType);
    }

    @Override
    public List<CoursesArticleDetail> queryArticleByTeacherId(String teacherId) {
        List<CoursesArticleDetail> coursesArticleDetails = coursesArticleDetailMapper.queryArticleByTeacherId(teacherId);
        return coursesArticleDetails;
    }

    @Override
    public CoursesArticleDetail queryArticleByid(String id) {
        CoursesArticleDetail coursesArticleDetail = coursesArticleDetailMapper.queryArticleById(id);
        return coursesArticleDetail;
    }

    @Override
    public List<CoursesArticleDetailVo> queryUserArticle(String userId) {
        return coursesArticleDetailMapper.queryUserArticle(userId);
    }
}
