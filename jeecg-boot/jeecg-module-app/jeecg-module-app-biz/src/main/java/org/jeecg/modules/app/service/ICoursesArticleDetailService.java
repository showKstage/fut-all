package org.jeecg.modules.app.service;

import org.jeecg.modules.app.entity.CoursesArticleDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.app.vo.CoursesArticleDetailVo;

import java.util.List;

/**
 * @Description: 文章详情表
 * @Author: jeecg-boot
 * @Date:   2021-06-20
 * @Version: V1.0
 */
public interface ICoursesArticleDetailService extends IService<CoursesArticleDetail> {

    List<CoursesArticleDetail>  queryByArticleType(String articleType);

    List<CoursesArticleDetail> queryByArticleTitle(String articleTitle);

    /** 根据教师id查询文章 */
    List<CoursesArticleDetail> queryArticleByTeacherId(String teacherId);

    /** 根据文章id查询文章详情 */
    CoursesArticleDetail queryArticleByid(String id);

    /** 获取我收藏的文章 */
    List<CoursesArticleDetailVo> queryUserArticle(String userId);


}
