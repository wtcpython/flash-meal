package com.flashmeal.controller.admin;

import com.flashmeal.constant.MessageConstant;
import com.flashmeal.result.Result;
import com.flashmeal.utils.AWSS3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {

    private final AWSS3Util awss3Util;

    public CommonController(AWSS3Util awss3Util) {
        this.awss3Util = awss3Util;
    }

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传: {}", file);

        try {
            String originalFileName = file.getOriginalFilename();
            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
            String objectName = UUID.randomUUID() + ext;

            String filePath = awss3Util.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.info("文件上传失败: ", e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
