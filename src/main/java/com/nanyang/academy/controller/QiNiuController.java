package com.nanyang.academy.controller;


import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.utils.file.QiniuCloudUtil;
import com.qiniu.util.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*@Slf4j
@RestController
@Api(tags = "七牛云")
@RequestMapping("/api/qiniu")*/
public class QiNiuController {

    /**
     * 设置需要操作的账号的AK和SK,均在七牛云中获得
     */
    private static final String ACCESS_KEY = "Otzs95KYf3kXThOa12LltYeux8WqedOL8fqQCjDm";

    private static final String SECRET_KEY = "fIuqvBVDLyFDvXny6aLpH64dKYhqiWLMhIrMk-d5";

    /**
     * 要上传的空间（刚刚新建空间的名称）
     */
    private static final String BUCKET_NAME = "guoyun";

    @ApiOperation(value = "上传图片视频")
    @PostMapping(value = "/uploadImg")
    @PreAuthorize("@ss.hasPermi('system:qiniu:upload')")
    public ResultEntity uploadImg(MultipartFile file) {
        if (file == null) {
            throw new CustomException("文件不存在");
        }
        Map<String, String> map = new HashMap<>();
        try {
            byte[] bytes = file.getBytes();
            String imageName = UUID.randomUUID().toString();
            try {
                //使用base64方式上传到七牛云
                map = QiniuCloudUtil.put64image(bytes, imageName);
                System.out.println("==========>" + map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new CustomException("上传图片异常");
        }
        return new ResultEntity(HttpStatus.OK.value(), "上传成功", map);
    }


    @ApiOperation(value = "获取上传Token")
    @PostMapping(value = "/getToken")
    public Object getToken() {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return new ResultEntity(HttpStatus.OK.value(), "返回token值", auth.uploadToken(BUCKET_NAME));
    }

    @ApiOperation(value = "删除图片")
    @DeleteMapping(value = "/delete")
    @PreAuthorize("@ss.hasPermi('system:qiniu:delete')")
    public Object delete(@RequestParam String fileName) {
        QiniuCloudUtil.delete(fileName);
        return new ResultEntity(HttpStatus.OK.value(), "删除成功");
    }
}
