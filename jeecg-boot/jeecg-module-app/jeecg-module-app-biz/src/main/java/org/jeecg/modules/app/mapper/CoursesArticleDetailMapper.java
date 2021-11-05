package org.jeecg.modules.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.app.entity.CoursesArticleDetail;
import org.jeecg.modules.app.vo.CoursesArticleDetailVo;


import java.util.List;

/**
 * @Description: 文章详情表
 * @Author: jeecg-boot
 * @Date:   2021-06-24
 * @Version: V1.0
 */
public interface CoursesArticleDetailMapper extends BaseMapper<CoursesArticleDetail> {

    public List<CoursesArticleDetail> queryByArticleTitle(@Param("articleTitle") String articleTitle);

    public List<CoursesArticleDetail>  queryByArticleType(@Param("articleType") String articleType);
    /** 根据教师id查询文章 */
    List<CoursesArticleDetail> queryArticleByTeacherId(String teacherId);
    /** 根据文章id查询文章详情 */
    CoursesArticleDetail queryArticleById(String id);
    /** 获取我收藏的文章 */
    List<CoursesArticleDetailVo> queryUserArticle(String userId);
}
