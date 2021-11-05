package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;

import lombok.Data;



import java.util.List;

/**
 * @Description: 搜索结果数据类
 * @Author: jiale.zhang
 * @Date:   2021-06-22
 * @Version: V1.0
 */

@Data
@ApiModel(description = "搜索结果数据类")
public class SearchResult {

    private List<CoursesArticleDetailVo> coursesArticleDetailVo;

    private List<CoursesDetailVo> coursesDetailVo;

    private int articleSum;

    private int courseSum;



}


