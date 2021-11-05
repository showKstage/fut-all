package org.jeecg.modules.app.service.impl;

import org.jeecg.modules.app.mapper.FileMapper;
import org.jeecg.modules.app.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    FileMapper fileMapper;

    @Override
    public void saveImageLink(String imageName,String url) {
        fileMapper.insertImageLink(imageName,url);
    }

    @Override
    public void deleteImageLink(String imageName) {
        fileMapper.deleteImageLink(imageName);
    }
}
