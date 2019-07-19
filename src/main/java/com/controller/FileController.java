package com.controller;

import com.annotation.SysLog;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.util.CommonFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class FileController {

    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private CommonFileUtil fileUtil;
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping("/goIndex")
    public String goIndex(){
        logger.info("进入主页面");
        return "/file";
    }

    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("fileName") MultipartFile file){

        String targetFilePath = "E:/uploads/";

        if(file.isEmpty()){
            logger.info("this file is empty");
        }

        String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //获取原来文件名称
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        if(!fileSuffix.equals(".jpg") || !fileSuffix.equals(".png")){
            logger.info("文件格式不正确");
        }
        //拼装新的文件名
        String targetFileName = targetFilePath + newFileName + fileSuffix;
        //上传文件
        try {
            FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(targetFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/success";
    }

    //使用fastdfs进行文件上传
    @RequestMapping("/uploadFileToFast")
    @SysLog("-----自定义注解类-----")
    public String uoloadFileToFast(@RequestParam("fileName")MultipartFile file,Model model) throws IOException{

        if(file.isEmpty()){
            logger.info("文件不存在");
        }
        String path = fileUtil.uploadFile(file);
        model.addAttribute("allPath",path);
        model.addAttribute("filename",path.substring(path.lastIndexOf("/")+1));
        System.out.println(path);
        return "success";
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(String filename, String allPath, HttpServletResponse response){
        try {
            allPath = allPath.substring(allPath.indexOf("group1"));
            fileUtil.downloadFile(filename,allPath,response);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }



}
