package com.ytxd.service.ScanErcode;

import com.ytxd.dao.ScanErcode.ErCodeGroupJoin_Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author :CYC
 */
@Service
public class ErCodeGroupJoin_ServiceImpl implements ErCodeGroupJoin_Service {

    @Resource
    private ErCodeGroupJoin_Mapper mapper;


    /**
     * 二维码公众参与
     */
    @Override
    public int addErCodeGroupJoin(Map<String, Object> obj) {
        return mapper.insertErCodeGroupJoin(obj);
    }
}
