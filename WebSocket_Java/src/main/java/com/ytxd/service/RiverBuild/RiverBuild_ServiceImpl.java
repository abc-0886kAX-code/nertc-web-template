package com.ytxd.service.RiverBuild;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.UIDUtils;
import com.ytxd.dao.RiverBuild.RiverBuild_Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Classname RiverBuild_ServiceImpl
 * @Author TY
 * @Date 2023/8/17 15:28
 * @Description TODO 涉河建设项目
 */
@Service
public class RiverBuild_ServiceImpl implements RiverBuild_Service{
    @Resource
    private RiverBuild_Mapper mapper;
    /**
     *
     * @Desription TODO 查询涉河建设项目列表
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/8/17 15:31
     * @Auther TY
     */
    @Override
    public PageInfo getRiverBuildList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_RiverBuild_List(obj);
        // 文件处理
        Iterator<Map<String,Object>> iterator = data.listIterator();
        while (iterator.hasNext()){
            Map<String,Object> map = iterator.next();
            String relevant_documents = DataUtils.getMapKeyValue(map,"relevant_documents");
            if(StringUtils.isNotEmpty(relevant_documents)){
                map.put("relevant_documents",relevant_documents.split("\\|"));
            }
        }
        return new PageInfo(data);
    }
    /**
     *
     * @Desription TODO 通过buildID获取涉河建设项目文件列表
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/8/17 15:35
     * @Auther TY
     */
    @Override
    public PageInfo getRiverBuildFileList(Map<String, Object> obj) throws Exception {
        String buildid = DataUtils.getMapKeyValue(obj,"buildid");
        if(StringUtils.isEmpty(buildid)){
            throw new RRException("请选择一条记录！");
        }
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_RiverBuild_File_List(obj);
        return new PageInfo(data);
    }
    /**
     *
     * @Desription TODO 保存涉河建设项目
     * @param obj
     * @return java.lang.String
     * @date 2023/8/17 15:40
     * @Auther TY
     */
    @Override
    public String SaveRiverBuild(Map<String, Object> obj) throws Exception {
        String id = DataUtils.getMapKeyValue(obj,"id");
        String userid = DataUtils.getMapKeyValue(obj,"userid");
        obj.put("submitman",userid);
        obj.put("submittime",DataUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        if(StringUtils.isNotEmpty(id)){
            mapper.Update_River_Build(obj);
        }else {
            id = UIDUtils.getUUID();
            obj.put("id",id);
            mapper.Insert_River_Build(obj);
        }
        String filelist = DataUtils.getMapKeyValue(obj,"filelist");
        if(StringUtils.isNotEmpty(filelist) && DataUtils.isJson(filelist)){
            try {
                JSONArray fileArray = JSONArray.parseObject(filelist, JSONArray.class);
                for(Integer i = 0 ;i < fileArray.size();i++){
                    JSONObject fileObj = fileArray.getJSONObject(i);
                    String fileid = fileObj.getString("id");
                    String filetype = fileObj.getString("filetype");
                    String filepath = fileObj.getString("filepath");
                    Map<String,Object> fileMap = new HashMap<>();
                    fileMap.put("filetype",filetype);
                    fileMap.put("filepath",filepath);
                    fileMap.put("buildid",id);
                   if(StringUtils.isNotEmpty(fileid)){
                       fileMap.put("id",fileid);
                       mapper.Update_River_Build_File(fileMap);
                   }else {
                       fileMap.put("id",UIDUtils.getUUID());
                       mapper.Insert_River_Build_File(fileMap);
                   }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return id;
    }
    /**
     *
     * @Desription TODO 删除涉河建设项目
     * @param obj
     * @return java.lang.String
     * @date 2023/8/17 16:07
     * @Auther TY
     */
    @Override
    public String DeleteRiverBuild(Map<String, Object> obj) throws Exception {
        String id = DataUtils.getMapKeyValue(obj,"id");
        if(StringUtils.isEmpty(id)){
           throw new RRException("请选择需要删除的数据");
        }
        mapper.Delete_River_Build(id);
        mapper.Delete_River_Build_FileByBuildId(id);
        return "ok";
    }
    /**
     *
     * @Desription TODO 通过文件ID删除文件信息
     * @param obj
     * @return java.lang.String
     * @date 2023/8/17 16:09
     * @Auther TY
     */
    @Override
    public String DeleteRiverBuildFileById(Map<String, Object> obj) throws Exception {
        String id = DataUtils.getMapKeyValue(obj,"id");
        if(StringUtils.isEmpty(id)){
            throw new RRException("请选择需要删除的数据");
        }
        mapper.Delete_River_Build_FileByID(id);
        return id;
    }
}
