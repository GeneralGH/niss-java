package com.nanyang.academy.controller;

import com.nanyang.academy.common.Constant;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.config.HengYuanConfig;
import com.nanyang.academy.utils.StringUtil;
import com.nanyang.academy.utils.file.FileUploadUtils;
import com.nanyang.academy.utils.file.FileUtils;
import com.nanyang.academy.utils.file.OfficeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/7/25
 * @Version 1.0
 **/
@RestController
@RequestMapping("/file")
@Api(tags = "文件处理")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    private static final String FILE_DELIMETER = ",";

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("/download")
    @ApiOperation(value = "通用下载请求")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtil.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = HengYuanConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    @ApiOperation(value = "通用上传请求(单个)")
    public ResultEntity uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = HengYuanConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);

            /*String extension = FileUploadUtils.getExtension(file);
            if (extension.equals("wav")){
                int start  = fileName.indexOf("upload")+6;
                String realName = fileName.substring(start,fileName.indexOf("."));
                ChangeAudio.changeToMp3(filePath+realName+".wav",filePath+realName+".mp3");
                fileName = Constant.RESOURCE_PREFIX +"/upload"+ realName + ".mp3";
            }*/
            //String url = serverConfig.getUrl() + fileName;
            String url = HengYuanConfig.getRealmName() + fileName;

            Map ajax = new HashMap<>();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ResultEntity.getOkResult(ajax);
        } catch (Exception e) {
            return ResultEntity.getErrorResult(e.getMessage());
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    @ApiOperation(value = "通用上传请求(多个)")
    public ResultEntity uploadFiles(List<MultipartFile> files) throws Exception {
        try {
            // 上传文件路径
            String filePath = HengYuanConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files) {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                //String url = serverConfig.getUrl() + fileName;
                String url = HengYuanConfig.getRealmName() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            Map ajax = new HashMap();
            ajax.put("urls", StringUtil.join(urls, FILE_DELIMETER));
            ajax.put("fileNames", StringUtil.join(fileNames, FILE_DELIMETER));
            ajax.put("newFileNames", StringUtil.join(newFileNames, FILE_DELIMETER));
            ajax.put("originalFilenames", StringUtil.join(originalFilenames, FILE_DELIMETER));
            return ResultEntity.getOkResult(ajax);
        } catch (Exception e) {
            return ResultEntity.getErrorResult(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    @ApiOperation(value = "本地资源通用下载")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtil.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = HengYuanConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtil.substringAfter(resource, Constant.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtil.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }


    /*@GetMapping("/ppt2pdf")
    @ApiOperation(value = "ppt转pdf")*/
    public ResultEntity<String> ppt2pdf(String resource, HttpServletRequest request, HttpServletResponse response)throws Exception {
        try {
            // 本地资源路径
            String localPath = HengYuanConfig.getProfile();
            // 数据库资源地址
            String sourcePath = localPath + StringUtil.substringAfter(resource, Constant.RESOURCE_PREFIX);
            // 转换pdf后路径
            String targetPath = HengYuanConfig.getTempFile();
            if (sourcePath.endsWith("ppt")){
                OfficeUtils.pptToPdf(sourcePath,targetPath);
            }
            if (sourcePath.endsWith("pptx")){
                OfficeUtils.pptxToPdf(sourcePath,targetPath);
            }
            String res = HengYuanConfig.getRealmName() + "/profile/temp"+sourcePath.substring(sourcePath.lastIndexOf("/"),sourcePath.lastIndexOf(".")) + ".pdf";
            return ResultEntity.getOkResult(res);
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
        return null;
    }

/*
    public static void main(String[] args) {
        String a  = "/profile/upload/2022/09/03/voiceFile_20220903100449A003.wav";
        int start  = a.indexOf("upload")+6;

        System.out.println(a.substring(start,a.indexOf(".")));
    }*/
}
