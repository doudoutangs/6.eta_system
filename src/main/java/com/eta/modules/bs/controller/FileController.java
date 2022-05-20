package com.eta.modules.bs.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.AnalysisReport;
import com.eta.modules.bs.service.AnalysisReportService;
import com.eta.modules.sys.model.SysUser;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 文件管理
 */
@Controller
@RequestMapping("/bs/file")
public class FileController {

    @Autowired
    private AnalysisReportService analysisReportService;


    @Value("${fileTemplate.path}")
    private String upload_folder;

    @SysLog("附件上传")
    @PostMapping(value = "/upload")
    @ResponseBody
    public ProcessResult upload(@RequestParam(value = "file") MultipartFile file,
                                HttpServletRequest request) {
        //判断文件是否为空
        if (file == null) {
            return new ProcessResult(ProcessResult.ERROR, "当前附件为空！");
        }
        //获取上传的文件名
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize(); //获取文件大小
        String pathString = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/");

        //创建文件对象
        File temp = new File(upload_folder);
        //判断模板文件夹是否存在
        if (!temp.exists()) {
            //创建文件夹
            temp.mkdirs();
        }

        //获取原始文件的.的索引
        int endIndexOf = originalFilename.lastIndexOf(".");
        //获取上传的文件后缀
        String fileSuffix = originalFilename.substring(endIndexOf, originalFilename.length());
        //创建新的文件名使用uuid随机数
        String newFileName = RandomStringUtils.randomNumeric(10).toString() + fileSuffix;

        //创建本地File对象
        String pathname = upload_folder + newFileName;
        File localFile = new File(pathname);
        try {
            //把上传的文件保存至本地
            file.transferTo(localFile);
            AnalysisReport report = new AnalysisReport();
            report.setFileName(originalFilename);
            report.setPath(pathname);
            report.setFileSuffix(fileSuffix);
            report.setCreateTime(DateUtil.now());
            report.setUpdateTime(DateUtil.now());

            return new ProcessResult(report);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }

    @GetMapping(value = "/download/{id}")
    @RequiresPermissions("bs:file:download")
    public ResponseEntity<byte[]> download(HttpServletResponse response, HttpServletRequest request, @PathVariable Integer id) {
        AnalysisReport fileBean = analysisReportService.getById(id);
        String filePath = fileBean.getPath();
        String fileName = fileBean.getFileName();
        HttpHeaders headers = new HttpHeaders();
        // 响应头设置ContentType
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (null != fileBean) {
            try {
                fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");

                // 附件形式
                headers.setContentDispositionFormData("attachment", fileName);
                byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
                return responseEntity;
            } catch (IOException e) {
                return new ResponseEntity<byte[]>(null, headers,
                        HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<byte[]>(null, headers, HttpStatus.NOT_FOUND);
        }
    }
}
