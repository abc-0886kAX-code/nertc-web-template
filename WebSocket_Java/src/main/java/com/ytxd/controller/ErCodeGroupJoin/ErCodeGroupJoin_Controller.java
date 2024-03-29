package com.ytxd.controller.ErCodeGroupJoin;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.ScanErcode.ErCodeGroupJoin_Service;
import com.ytxd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author :ZYF
 */
@RestController("/ErCodeGroupJoin_Controller")
@RequestMapping("/ErCodeGroupJoin")
public class ErCodeGroupJoin_Controller {
    @Resource
    private ErCodeGroupJoin_Service service;

    /**
     * 二维码公众参与
     */
    @PostMapping("/addErCodeGroupJoin")
    public R addErCodeGroupJoin(HttpServletRequest request) {

        Map<String, Object> obj = DataUtils.getParameterMap(request);

        R r = new R();

        if (StringUtil.isEmpty(request.getParameter("isdestroy"))) {
            r.put("code", 400);
            r.put("msg", "是否发现有人破坏：不能为空");
            return r;
        }

        if (StringUtils.isEmpty(request.getParameter("ischange"))) {
            r.put("code", 400);
            r.put("msg", "进俩年芙蓉溪生态环境改善是否显著：不能为空");
            return r;
        }

        if (StringUtil.isEmpty(request.getParameter("issewage"))) {
            r.put("code", 400);
            r.put("msg", "芙蓉溪是否有污水乱拍：不能为空");
            return r;
        }

        if (StringUtil.isEmpty(request.getParameter("isdrifter"))) {
            r.put("code", 400);
            r.put("msg", "芙蓉溪河道是否存在垃圾、漂流物：不能为空");
            return r;
        }

        if (StringUtil.isEmpty(request.getParameter("devicestatus"))) {
            r.put("code", 400);
            r.put("msg", "设备是否破坏：不能为空");
            return r;
        }

        if (StringUtil.isEmpty(request.getParameter("interesttype"))) {
            r.put("code", 400);
            r.put("msg", "您对哪些方面感兴趣：不能为空");
            return r;
        }

        if (StringUtil.isEmpty(request.getParameter("score"))) {
            r.put("code", 400);
            r.put("msg", "评分：不能为空");
            return r;
        }

//        if (StringUtil.isEmpty(request.getParameter("suggest"))) {
//            r.put("code", 400);
//            r.put("msg", "意见或者建议：不能为空");
//            return r;
//        }
//
//        if (StringUtil.isEmpty(request.getParameter("filepath"))) {
//            r.put("code", 400);
//            r.put("msg", "照片或者文件：不能为空");
//            return r;
//        }

        // 获取当前日期时间（包含时分秒）
        LocalDateTime now = LocalDateTime.now();

        // 将日期时间转换为字符串（例如 "yyyy-MM-dd HH:mm:ss" 格式）
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        obj.put("createtime", formattedDateTime);

        int msg = service.addErCodeGroupJoin(obj);

        return R.ok(msg);

    }


    @PostMapping("/uploadFile")
    public R uploadFile(@RequestParam("file") MultipartFile[] files) throws IOException {

        R uploadedFilesInfo = new R();

        // 获取服务器上存储上传文件的基础路径
        String serverBasePath = "/UpFile/ercode_group_join/";
        // 根据实际部署情况设置本地硬盘的实际路径  c://ALLFile/uploadFiles/
        String localBasePath = "C:\\ALLFile\\uploadFiles\\frx";

        for (MultipartFile m : files) {
            String originalFilename = m.getOriginalFilename();
            String localAbsolutePath = localBasePath + originalFilename;

            // 保存文件到服务器
            File targetFile = new File(localAbsolutePath);
            m.transferTo(targetFile);

            // 记录相对路径
            String relativePath = serverBasePath + originalFilename;
            uploadedFilesInfo.put("data", relativePath);
        }

        // 返回包含所有上传文件及其相对路径的Map
        return R.ok(uploadedFilesInfo);
    }
}
