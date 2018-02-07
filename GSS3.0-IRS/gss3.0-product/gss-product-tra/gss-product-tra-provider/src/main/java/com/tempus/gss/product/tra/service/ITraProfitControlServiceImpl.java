package com.tempus.gss.product.tra.service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.tra.api.entity.*;
import com.tempus.gss.product.tra.api.entity.vo.TraProfitControlVo;
import com.tempus.gss.product.tra.api.service.ITraProfitControlService;
import com.tempus.gss.product.tra.dao.TraProfitControlDao;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.exception.GSSException;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


/**
 * Created by 杨威 on 2017/2/25.
 */
@Service
@EnableAutoConfiguration
public class ITraProfitControlServiceImpl implements ITraProfitControlService {
    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    TraProfitControlDao traProfitControlDao;
	
	@Reference
    IMaxNoService maxNoService;

	/**
	 * 创建控润信息
	 */
	@Override
	public int createProfitControl(RequestWithActor<TraProfitControl> requestWithActor) {
		int flag=0;
		TraProfitControl profitControl=requestWithActor.getEntity();
		if(requestWithActor.getAgent()==null || profitControl==null){
			throw new GSSException("创建火车控润agent为空", "0101", "创建火车控润信息失败");
		}
		if(profitControl.getInsuranceNo()==null){
			throw new GSSException("创建火车控润产品信息为空", "0102", "创建火车控润信息失败");
		}
		if(profitControl.getBrokerage()==null){
			throw new GSSException("创建火车控润手续费为空", "0103", "创建火车控润信息失败");
		}
		if(profitControl.getCustomerTypeNo()==null){
			throw new GSSException("创建火车控润渠道类型为空", "0104", "创建火车控润信息失败");
		}
		Long profitControlNo = maxNoService.generateBizNo("TRA_PROFIT_CONTROL_NO", 17);
		profitControl.setProfitControlNo(profitControlNo);
		profitControl.setOwner(requestWithActor.getAgent().getOwner());
		profitControl.setCreator(requestWithActor.getAgent().getAccount());
		profitControl.setCreateTime(new Date());
		profitControl.setValid(true);
		try{
			flag=traProfitControlDao.insert(requestWithActor.getEntity());
		}catch(Exception e){
			log.error("创建火车控润信息失败"+e);
			throw new GSSException("创建火车控润信息失败", "0101", "创建火车控润信息失败");
		}
		return flag;
	}
	/**
	 * 删除控润
	 */
	@Override
	public int deleteProfitControl(RequestWithActor<Long> requestWithActor) {
		if(requestWithActor.getAgent()==null || requestWithActor.getEntity()==null){
			throw new GSSException("删除火车控润agent为空", "0101", "删除火车控润信息失败");
		}
		int flag=traProfitControlDao.deleteByPrimaryKey(requestWithActor.getEntity());
		if(flag==0){
			throw new GSSException("删除火车控润信息失败", "0101", "删除火车控润信息失败");
		}
		return flag;
	}
	/**
	 * 修改控润
	 */
	@Override
	public int updateProfitControl(RequestWithActor<TraProfitControl> requestWithActor) {
		int flag=0;
		if(requestWithActor.getAgent()==null || requestWithActor.getEntity()==null){
			throw new GSSException("修改火车控润agent为空", "0101", "修改火车控润信息失败");
		}
		if(requestWithActor.getEntity().getProfitControlNo()==null){
			throw new GSSException("修改火车控润单号为空", "0102", "修改火车控润信息失败");
		}
		requestWithActor.getEntity().setModifier(requestWithActor.getAgent().getAccount());
		requestWithActor.getEntity().setModifyTime(new Date());
		
		try{
			flag=traProfitControlDao.updateByPrimaryKeySelective(requestWithActor.getEntity());
		}catch(Exception e ){
			log.error("修改火车控润信息失败"+e);
			throw new GSSException("修改火车控润信息失败", "0103", "修改火车控润信息失败");
		}
		return flag;
	}
	/**
	 * 根据控润编号进行查询
	 */
	@Override
	public TraProfitControl queryProfitControl(RequestWithActor<Long> requestWithActor) {
		if(requestWithActor.getAgent()==null || requestWithActor.getEntity()==null){
			throw new GSSException("查询火车控润agent为空", "0101", "查询火车控润信息失败");
		}
		TraProfitControl traProfitControl=new TraProfitControl();
		try{
			traProfitControl=traProfitControlDao.selectByPrimaryKey(requestWithActor.getEntity());
		}catch(Exception e){
			log.error("查询火车控润信息失败"+e);
			throw new GSSException("查询火车控润信息失败", "0101", "查询火车控润信息失败");
		}
		return traProfitControl;
	}
	/**
	 * 分页查询
	 */
	@Override
	public Page<TraProfitControl> queryPageProfitControlList(Page<TraProfitControl> page,
			RequestWithActor<TraProfitControlVo> pageRequest) {
		if(pageRequest.getAgent()==null || pageRequest.getEntity()==null ){
			throw new GSSException("查询火车控润agent为空", "0101", "查询火车控润信息失败");
		}
		try{
			List<TraProfitControl> traProfitControlList=traProfitControlDao.queryPageProfitControl(page,pageRequest.getEntity());
			page.setRecords(traProfitControlList);
		}catch(Exception e){
			log.error("分页查询火车控润信息失败"+e);
			throw new GSSException("分页查询火车控润信息失败", "0102", "查询火车控润信息失败"+e);
		}
		
		return page;
	}
	/**
	 * 取消控润
	 */
	@Override
	public boolean cancelProfitControl(RequestWithActor<Long> requestWithActor) {
		if(requestWithActor.getAgent()==null || requestWithActor.getEntity()==null ){
			throw new GSSException("取消火车控润agent为空", "0101", "取消火车控润信息失败");
		}
		TraProfitControl traProfitControl=new TraProfitControl();
		traProfitControl.setProfitControlNo(requestWithActor.getEntity());
		traProfitControl.setValid(false);
		try{
			traProfitControlDao.updateByPrimaryKeySelective(traProfitControl);
		}catch(Exception e){
			log.error("取消控润信息失败"+e);
			return false;
		}
		return true;
	}
	/**
	 * 获取控润
	 */

	@Override
	public List<TraProfitControl>   selectByTrainName (String name){
		return traProfitControlDao.selectByTrainName(name);
	}

	/**
	 * 查询控润信息
	 */
	public List<TraProfitControl> queryProfitControlList(){
		List<TraProfitControl> list=traProfitControlDao.queryProfitControlList();
		return list;
	}
}
