package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpItemType;
import com.tempus.gss.product.unp.api.entity.vo.SaleReportQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpItemTypeReportVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpSaleReportVo;
import com.tempus.gss.product.unp.api.service.UnpSaleReportService;
import com.tempus.gss.product.unp.dao.UnpSaleReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;
@Service
public class UnpSaleReportServiceImpl implements UnpSaleReportService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UnpSaleReportMapper unpSaleReportMapper;
    @Override
    public Page<UnpSaleReportVo> queryUnpSaleReports(Page<UnpSaleReportVo> page, SaleReportQueryVo saleReportQueryVo) {
        logger.info("传入查询参数:"+saleReportQueryVo);

        try {
            List<UnpSaleReportVo> unpSaleReportVos= unpSaleReportMapper.queryUnpSaleReports(page,saleReportQueryVo);
               /* UnpItemTypeReportVo unpItemTypeReportVo = new UnpItemTypeReportVo();
                if (unpSaleReportVos!=null){
                    for (int i = 0; i <unpSaleReportVos.size() ; i++) {
                        if (unpSaleReportVos.get(i).getAdditionalInfo()!=null && unpSaleReportVos.get(i).getAdditionalInfo()!="" ){
                            List< UnpItemTypeReportVo> unpItemTypeReportVos = ( List<UnpItemTypeReportVo>)JSON.parseArray(unpSaleReportVos.get(i).getAdditionalInfo(), unpItemTypeReportVo.getClass());
                            unpSaleReportVos.get(i).setUnpItemTypeReportVo(unpItemTypeReportVos);
                        }
                    }
                }*/
            page.setRecords(unpSaleReportVos);
        } catch (Exception e) {
           logger.error("Error",e);
        }
        return page;
    }
}
