package com.example.service;

import com.example.config.FilePathConfig;
import com.example.domain.Illustration;
import com.example.repository.IllustrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

@Service
public class FileService {
    private IllustrationRepository illustrationRepository;
    private FilePathConfig filePathConfig;

    final private Set<String> allowSuffix = new HashSet<String>(Arrays.asList("jpg", "jpeg", "png", "gif"));
    private static String ACCESS_URL;
    private static String UPLOAD_PATH;

    @Autowired
    public FileService(IllustrationRepository illustrationRepository, FilePathConfig filePathConfig) {
        this.illustrationRepository = illustrationRepository;
        this.filePathConfig = filePathConfig;
    }
    @PostConstruct
    public void init() {
        ACCESS_URL = filePathConfig.getBaseUrl();
        UPLOAD_PATH = filePathConfig.getUploadFolder();
    }

    /*
        上传插画
        通知码code
        0: 成功  -1: 权限不足  -2: 文件格式不合法  -3: 文件重复  -4: 图片保存失败
    */
    public HashMap illustrationUpload(MultipartFile multipartFile, Integer pid, String illustrator, String title, String description) {
        HashMap hashMap = new HashMap();
        String fileName = multipartFile.getOriginalFilename();
        // 解析到文件后缀，判断是否合法
        int index = fileName.lastIndexOf('.');
        String suffix = null;
        if (index < 0 || (suffix = fileName.substring(index + 1)).isEmpty() || !allowSuffix.contains(suffix)) {
            hashMap.put("code", -2);
        } else {
            if (illustrationRepository.findByPid(pid) == null) {
                fileName = pid + "." + suffix;
                String path = UPLOAD_PATH + fileName;
                File dest = new File(path);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                try {
                    multipartFile.transferTo(dest); // 保存文件
                } catch (Exception e) {
                    hashMap.put("code", -4);
                }
                String url = ACCESS_URL + fileName;
                Illustration illustration = new Illustration(pid, illustrator, title, description, url);
                illustrationRepository.save(illustration);
                hashMap.put("code", 0);
            } else {
                hashMap.put("code", -3);
            }
        }
        return hashMap;
    }

    public ArrayList<Illustration> showAllPic(){
        return illustrationRepository.findAll();
    }
}
