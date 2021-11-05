package org.jeecg.modules.app.service;

import org.springframework.stereotype.Service;

@Service
public interface IFileService {
    /** 保存图片链接 **/
    void saveImageLink(String imageName,String url);

    /** 删除图片链接 */
    void deleteImageLink(String imageName);
}
