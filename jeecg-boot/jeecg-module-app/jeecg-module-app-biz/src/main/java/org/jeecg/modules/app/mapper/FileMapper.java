package org.jeecg.modules.app.mapper;

public interface FileMapper {

    /** 保存图片链接 */
    void insertImageLink(String imageName , String url);

    /** 删除图片链接 */
    void deleteImageLink(String imageName);

}
