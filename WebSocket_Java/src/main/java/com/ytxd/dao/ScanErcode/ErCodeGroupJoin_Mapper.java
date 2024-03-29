package com.ytxd.dao.ScanErcode;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author :CYC
 */
@Mapper
public interface ErCodeGroupJoin_Mapper {


    /**
     * 二维码公众参与
     */
    Integer insertErCodeGroupJoin(Map<String, Object> obj);
}
