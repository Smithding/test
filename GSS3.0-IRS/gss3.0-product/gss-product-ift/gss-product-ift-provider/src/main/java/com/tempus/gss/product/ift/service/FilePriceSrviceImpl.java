package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.Cabin;
import com.tempus.gss.product.ift.api.entity.FilePrice;
import com.tempus.gss.product.ift.api.entity.Ift_cabin;
import com.tempus.gss.product.ift.api.entity.vo.FilePriceVo;
import com.tempus.gss.product.ift.api.service.IFilePriceService;
import com.tempus.gss.product.ift.dao.FilePriceDao;
import com.tempus.gss.product.ift.dao.Ift_cabinDao;
import com.tempus.gss.system.service.IMaxNoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
@Service
@Component
public class FilePriceSrviceImpl implements IFilePriceService {

    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    FilePriceDao filePriceDao;
    @Autowired
    Ift_cabinDao ift_cabinDao;
    @Reference
    private IMaxNoService maxNoService;
    /**
     * 列表查询
     * @param page
     * @param requestWithActor
     * @return
     */
    @Override
    public Page<FilePrice> pageList(Page<FilePrice> page, RequestWithActor<FilePriceVo> requestWithActor) {
        log.info("查询文件价政策开始");
        try {
            if (requestWithActor == null ) {
                throw new GSSException("查询文件价政策模块", "0301", "传入参数为空");
            }
            if ( requestWithActor.getAgent().getOwner() == 0) {
                throw new GSSException("查询文件价政策模块", "0301", "传入参数为空");
            }
            requestWithActor.getEntity().setValid(1);
            List<FilePrice> filePriceList = filePriceDao.queryObjByKey(page,requestWithActor.getEntity());
            page.setRecords(filePriceList);
        } catch (Exception e) {
            log.error("文件价政策表中未查询出相应的结果集", e);
            throw new GSSException("查询文件价政策模块", "0302", "查询文件价政策失败");
        }
        log.info("查询文件价政策结束");
        return page;
    }

    /**
     * 获取单条数据
     * @param
     * @return
     */
    @Override
    public FilePrice queryById(Long id) {
        List<FilePrice> filePriceList = filePriceDao.queryById(id);
        if(filePriceList!=null&&filePriceList.size()>0){
            for(FilePrice filePrice:filePriceList){
                List<Ift_cabin> ift_cabinList = ift_cabinDao.selectCabinByPolicyNo(filePrice.getPolicyNo());
                filePrice.setCabinList(ift_cabinList);
            }
        }
        return filePriceList.get(0);
    }

    /**
     * 修改数据
     * @param filePrice
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Deprecated
    public int update(FilePrice filePrice) {
        int status = 0;
        ift_cabinDao.delByPolicyNo(filePrice.getPolicyNo());
        if(filePrice!=null&&filePrice.getCabinList()!=null){
            for(Ift_cabin cabin:filePrice.getCabinList()){
                if(cabin==null) continue;
                Long policyCabinNo = maxNoService.generateBizNo("POLICY_CABIN_NO", 57);
                cabin.setCabinGrade(filePrice.getCabinGrade());
                cabin.setPolicyNo(filePrice.getPolicyNo());
                cabin.setPolicyCabinNo(policyCabinNo);
                status = ift_cabinDao.insert(cabin);
            }
        }
        int num = filePriceDao.updateByIds(filePrice);
        return status+num;
    }

    /**
     * 新增数据
     * @param filePrice
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Deprecated
    public int insert(FilePrice filePrice) {
        if(filePrice!=null&&filePrice.getCabinList()!=null){
            for(Ift_cabin cabin:filePrice.getCabinList()){
                if(cabin==null) continue;
                Long policyCabinNo = maxNoService.generateBizNo("POLICY_CABIN_NO", 57);
                cabin.setCabinGrade(filePrice.getCabinGrade());
                cabin.setPolicyCabinNo(policyCabinNo);
                cabin.setPolicyNo(filePrice.getPolicyNo());
                ift_cabinDao.insert(cabin);
            }
        }
        int num = filePriceDao.insert(filePrice);
        return num;
    }

    /**
     * 逻辑删除数据
     * @param filePrice
     * @return
     */
    @Override
    public int deleteByBean(FilePrice filePrice) {
        return filePriceDao.updateByIds(filePrice);
    }
}
