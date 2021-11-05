package org.jeecg.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.app.entity.CoursesCatalogue;
import java.util.List;

/**
 * @Author: shiHao.qiu
 * @Date: 2021/7/12 8:29
 */

@ApiModel(value="课程目录", description="课程目录")
@Data
public class CoursesCatalogueVo {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**父目录id*/
    @ApiModelProperty(value = "父目录id")
    private String parentId;
    /**课程id*/
    @ApiModelProperty(value = "课程id")
    private String coursesDetailId;
    /**目录名*/
    @ApiModelProperty(value = "目录名")
    private String catalogueName;
    /**视频点播id*/
    @ApiModelProperty(value = "视频点播id")
    private String vodId;
    /**子目录数组*/
    @ApiModelProperty(value = "子目录数组")
    private List<CoursesCatalogueVo> children;


    public CoursesCatalogueVo createNode(CoursesCatalogue coursesCatalogue){
        this.id=coursesCatalogue.getId();
        this.parentId=coursesCatalogue.getParentId();
        this.coursesDetailId=coursesCatalogue.getCoursesDetailId();
        this.catalogueName=coursesCatalogue.getCatalogueName();
        this.vodId=coursesCatalogue.getVodId();
        return  this;
    }


}
