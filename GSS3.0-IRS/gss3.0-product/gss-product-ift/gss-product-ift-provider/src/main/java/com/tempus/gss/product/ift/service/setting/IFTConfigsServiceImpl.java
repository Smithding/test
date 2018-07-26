package com.tempus.gss.product.ift.service.setting;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.cps.entity.Channel;
import com.tempus.gss.cps.service.IChannelService;
import com.tempus.gss.mq.event.EventType;
import com.tempus.gss.product.ift.api.entity.SaleChangeExt;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.entity.exception.Errors;
import com.tempus.gss.product.ift.api.entity.setting.IFTConfigs;
import com.tempus.gss.product.ift.api.entity.setting.Status;
import com.tempus.gss.product.ift.api.exceptions.ConfigsException;
import com.tempus.gss.product.ift.api.service.setting.IConfigsService;
import com.tempus.gss.product.ift.api.utils.IFTDateUtil;
import com.tempus.gss.product.ift.api.utils.IFTFestivalUtil;
import com.tempus.gss.product.ift.dao.SaleChangeExtDao;
import com.tempus.gss.product.ift.dao.SaleOrderExtDao;
import com.tempus.gss.product.ift.dao.setting.ConfigsDao;
import com.tempus.gss.product.ift.mq.IFTConfigsMqSender;
import com.tempus.gss.util.EntityUtil;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * <pre>
 * <b>渠道配置维护服务.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> Aaron
 * <b>Date:</b> 2016年10月11日  16:06
 * <b>Copyright:</b> Copyright 2016 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2016年10月11日  16:06   Aaron
 *         new file
 * </pre>
 */
@Service
public class IFTConfigsServiceImpl implements IConfigsService {
    
    /**
     * 消息队列的名称
     */
    @Value("${mq.queue.configsQue}")
    private String queueName;
    
    @Autowired
    protected ConfigsDao mapper;
    
    @Autowired
    protected IFTConfigsMqSender mqSender;
    
    @Reference
    protected IChannelService channelService;
    
    @Autowired
    private IFTFestivalUtil festivalUtil;
    @Autowired
    SaleOrderExtDao saleOrderExtDao;
    @Autowired
    SaleChangeExtDao saleChangeExtDao;
    /**
     * 日志记录器.
     */
    protected static final Logger logger = LogManager.getLogger(IFTConfigsServiceImpl.class);
    
    @Override
    public IFTConfigs getConfigByChannelID(Agent agent, Long channelId) throws ConfigsException {
        
        if (null == channelId) {
            throw new ConfigsException(Errors.E_INVALID_PARAM, "客商ID无效");
        }
        
        IFTConfigs configs = null;
        
        List<IFTConfigs> list = this.getByIds(agent, channelId);
        if (null != list && 0 != list.size()) {
            configs = list.get(0);
        }
        return configs;
    }
    
    @Override
    public List<IFTConfigs> getByIds(Agent agent, Long... channelIds) throws ConfigsException {
        
        if (null == channelIds || 0 == channelIds.length) {
            throw new ConfigsException(Errors.E_INVALID_PARAM, "客商ID清单无效");
        }
        
        int owner = agent.getOwner();
        List<IFTConfigs> configs = mapper.find(owner, channelIds);
        return configs;
    }
    
    @Override
    public List<IFTConfigs> query(Agent agent, Long channelId, Long channelType, Status status) throws ConfigsException {
        
        int owner = agent.getOwner();
        IFTConfigs configs = new IFTConfigs(owner, status);
        
        return mapper.query(configs);
    }
    
    @Override
    public List<Channel> getAllConfigs(Agent agent, Status status) throws ConfigsException {
        
        // 获取所有的渠道配置清单
        List<IFTConfigs> cfgList = this.query(agent, null, null, status);
        if (null == cfgList || 0 == cfgList.size()) {
            return null;
        }
        
        // 依次获取供应信息，并为供应附加上相应的属性配置
        List<Channel> list = new ArrayList<>();
        for (IFTConfigs cfg : cfgList) {
            long channelId = cfg.getChannelId();
            long channelType = cfg.getChannelType();
            
            // 仅需要筛选供应商类的配置
            if (!String.valueOf(channelType).startsWith("1")) {
                continue;
            }
            
            Channel channel = null;
            try {
                // 获取供应商的基本信息
                channel = channelService.getById(agent, channelId);
            } catch (Exception e) {
                logger.error("拉取供应[" + channelId + "]信息失败, " + e.getMessage(), e);
                continue;
            }
            
            // 仅需要供应账号
            if (null == channel.getLevel() || 4 != channel.getLevel()) {
                continue;
            }
            
            // 将渠道配置附加到供应属性设置中
            Map<String, Object> attrs;
            if (MapUtils.isNotEmpty(attrs = cfg.getConfigsMap())) {
                attrs.put("ticketOffice", cfg.getOffice());
                if (null == channel.getAttrs()) {
                    channel.setAttrs(attrs);
                } else {
                    channel.getAttrs().putAll(attrs);
                }
            }
            
            list.add(channel);
        }
        
        return list;
    }
    
    @Override
    public long save(Agent agent, Long id, Long channelId, Long channelType, Map<String, Object> attrs, String remark, Status status) throws ConfigsException {
        
        if (null == channelType || -1L >= channelType) {
            throw new ConfigsException(Errors.E_INVALID_PARAM, "类型无效");
        }
        
        if (null == id && (null == channelId || 0L >= channelId)) {
            throw new ConfigsException(Errors.E_INVALID_PARAM, "配置ID或客商ID无效");
        }
        
        // 构建渠道配置
        IFTConfigs configs = new IFTConfigs(channelId, channelType, attrs, remark, status);
        
        EntityUtil.setAddInfo(configs, agent);
        
        // EventType eventType;
        // 为新客商进行配置时，判断是否已经存在配置
        int count = mapper.exist(configs);
        if (null == id || 0 == count) {
            // 分配ID，便于新增该客商的配置
            
            if (null != id && 0 == id) {
                configs.setId(0L);
            } else {
                configs.setId(id = IdWorker.getId());
            }
            // eventType = EventType.CREATE;
            
            // 执行插入到数据层
            mapper.insert(configs);
            
        } else {
            configs.setId(id);
            // eventType = EventType.UPDATE;
            
            // 执行修改到数据层
            mapper.update(configs);
        }
        
        // 发送消息，更新了渠道配置信息.
        mqSender.send("ubp", "config", JsonUtil.toJson(configs));
        
        if (logger.isInfoEnabled()) {
            String modifier = agent.getAccount();
            logger.info("用户[{}]保存渠道配置[{}]", modifier, channelId);
        }
        
        return id;
    }
    
    @Override
    public void remove(Agent agent, Long channelId) throws ConfigsException {
        
        if (null == channelId || 0L >= channelId) {
            throw new ConfigsException(Errors.E_INVALID_PARAM, "客商ID无效");
        }
        
        int owner = agent.getOwner();
        String modifier = agent.getAccount();
        Date modifyTime = new Date(System.currentTimeMillis());
        IFTConfigs configs = new IFTConfigs(owner, channelId, modifier, modifyTime);
        
        // 执行删除配置
        mapper.delete(configs);
        
        // 发送消息，移除了渠道配置信息.
        mqSender.send(owner, EventType.DELETE, channelId);
        if (logger.isInfoEnabled()) {
            logger.info("用户[{}]删除渠道配置[{}]", modifier, channelId);
        }
    }
    
    @Override
    public Map<String, Object> getInfoByTime(Agent agent, Long channelId, Date time) throws ConfigsException {
        System.out.println(time);
        if (null == channelId) {
            throw new ConfigsException(Errors.E_INVALID_PARAM, "客商ID无效");
        }
        IFTConfigs configs = null;
        List<IFTConfigs> list = this.getByIds(agent, channelId);
        if (null != list && 0 != list.size()) {
            configs = list.get(0);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (time != null) {//   2,按照星期\正常时间;           1,按照节假日
            String times = IFTDateUtil.format(time, IFTDateUtil.DATE);
            Date date = festivalUtil.getDate(times);
            if (configs.getConfig("ticketstatus").toString().equals("1")) { //按节假日出票
                if (festivalUtil.isFestival(date)) {
                    String sprcialstarthour = configs.getConfig("sprcialstarthour") != null ? configs.getConfig("sprcialstarthour").toString() : "00";
                    String sprcialstratminute = configs.getConfig("sprcialstratminute") != null ? configs.getConfig("sprcialstratminute").toString() : "00";
                    String sprcialendhour = configs.getConfig("sprcialendhour") != null ? configs.getConfig("sprcialendhour").toString() : "00";
                    String sprcialendminute = configs.getConfig("sprcialendminute") != null ? configs.getConfig("sprcialendminute").toString() : "00";
                    boolean isRange = this.isRange(time, sprcialstarthour, sprcialstratminute, sprcialendhour, sprcialendminute);
                    result.put("isRange", isRange);
                    result.put("ticketstart", sprcialstarthour + ":" + sprcialstratminute);
                    result.put("ticketend", sprcialendhour + ":" + sprcialendminute);
                } else {
                    if (festivalUtil.isWeekend(date)) {
                        String weekendendhour = configs.getConfig("weekendendhour") != null ? configs.getConfig("weekendendhour").toString() : "00";
                        String weekendendminute = configs.getConfig("weekendendminute") != null ? configs.getConfig("weekendendminute").toString() : "00";
                        String weekendstarthour = configs.getConfig("weekendstarthour") != null ? configs.getConfig("weekendstarthour").toString() : "00";
                        String weekendstartminute = configs.getConfig("weekendstartminute") != null ? configs.getConfig("weekendstartminute").toString() : "00";
                        boolean isRange = this.isRange(time, weekendstarthour, weekendstartminute, weekendendhour, weekendendminute);
                        result.put("isRange", isRange);
                        result.put("ticketstart", weekendstarthour + ":" + weekendstartminute);
                        result.put("ticketend", weekendendhour + ":" + weekendendminute);
                    } else {
                        String normalstarthour = configs.getConfig("normalstarthour") != null ? configs.getConfig("normalstarthour").toString() : "00";
                        String normalstartminut = configs.getConfig("normalstartminut") != null ? configs.getConfig("normalstartminut").toString() : "00";
                        String normalendhour = configs.getConfig("normalendhour") != null ? configs.getConfig("normalendhour").toString() : "00";
                        String normalendminute = configs.getConfig("normalendminute") != null ? configs.getConfig("normalendminute").toString() : "00";
                        boolean isRange = this.isRange(time, normalstarthour, normalstartminut, normalendhour, normalendminute);
                        result.put("isRange", isRange);
                        result.put("ticketstart", normalstarthour + ":" + normalstartminut);
                        result.put("ticketend", normalendhour + ":" + normalendminute);
                    }
                }
            } else {//按工作时间出票
                if (festivalUtil.isWeekend(date)) {
                    String weekendendhour = configs.getConfig("weekendendhour") != null ? configs.getConfig("weekendendhour").toString() : "00";
                    String weekendendminute = configs.getConfig("weekendendminute") != null ? configs.getConfig("weekendendminute").toString() : "00";
                    String weekendstarthour = configs.getConfig("weekendstarthour") != null ? configs.getConfig("weekendstarthour").toString() : "00";
                    String weekendstartminute = configs.getConfig("weekendstartminute") != null ? configs.getConfig("weekendstartminute").toString() : "00";
                    boolean isRange = this.isRange(time, weekendstarthour, weekendstartminute, weekendendhour, weekendendminute);
                    result.put("isRange", isRange);
                    result.put("ticketstart", weekendstarthour + ":" + weekendstartminute);
                    result.put("ticketend", weekendendhour + ":" + weekendendminute);
                } else {
                    String normalstarthour = configs.getConfig("normalstarthour") != null ? configs.getConfig("normalstarthour").toString() : "00";
                    String normalstartminut = configs.getConfig("normalstartminut") != null ? configs.getConfig("normalstartminut").toString() : "00";
                    String normalendhour = configs.getConfig("normalendhour") != null ? configs.getConfig("normalendhour").toString() : "00";
                    String normalendminute = configs.getConfig("normalendminute") != null ? configs.getConfig("normalendminute").toString() : "00";
                    boolean isRange = this.isRange(time, normalstarthour, normalstartminut, normalendhour, normalendminute);
                    result.put("isRange", isRange);
                    result.put("ticketstart", normalstarthour + ":" + normalstartminut);
                    result.put("ticketend", normalendhour + ":" + normalendminute);
                }
            }
            if (configs.getConfig("refundstatus").toString().equals("1")) { //按节假日费票
                if (festivalUtil.isFestival(date)) {
                    String sprcialrefundendhour = configs.getConfig("sprcialrefundendhour") != null ? configs.getConfig("sprcialrefundendhour").toString() : "00";
                    String sprcialrefundendminute = configs.getConfig("sprcialrefundendminute") != null ? configs.getConfig("sprcialrefundendminute").toString() : "00";
                    String sprcialrefundstarthour = configs.getConfig("sprcialrefundstarthour") != null ? configs.getConfig("sprcialrefundstarthour").toString() : "00";
                    String sprcialrefundstartminute = configs.getConfig("sprcialrefundstartminute") != null ? configs.getConfig("sprcialrefundstartminute").toString() : "00";
                    boolean isRange = this.isRange(time, sprcialrefundstarthour, sprcialrefundstartminute, sprcialrefundendhour, sprcialrefundendminute);
                    result.put("isrefundRange", isRange);
                    result.put("ticketrefundstart", sprcialrefundstarthour + ":" + sprcialrefundstartminute);
                    result.put("ticketrefundend", sprcialrefundendhour + ":" + sprcialrefundendminute);
                } else {
                    if (festivalUtil.isWeekend(date)) {
                        String weekendrefundstarthour = configs.getConfig("weekendrefundstarthour") != null ? configs.getConfig("weekendrefundstarthour").toString() : "00";
                        String weekendrefundstartminute = configs.getConfig("weekendrefundstartminute") != null ? configs.getConfig("weekendrefundstartminute").toString() : "00";
                        String weekendrefundendhour = configs.getConfig("weekendrefundendhour") != null ? configs.getConfig("weekendrefundendhour").toString() : "00";
                        String weekendrefundendminute = configs.getConfig("weekendrefundendminute") != null ? configs.getConfig("weekendrefundendminute").toString() : "00";
                        boolean isRange = this.isRange(time, weekendrefundstarthour, weekendrefundstartminute, weekendrefundendhour, weekendrefundendminute);
                        result.put("isrefundRange", isRange);
                        result.put("ticketrefundstart", weekendrefundstarthour + ":" + weekendrefundstartminute);
                        result.put("ticketrefundend", weekendrefundendhour + ":" + weekendrefundendminute);
                    } else {
                        String normalrefundstarthour = configs.getConfig("normalrefundstarthour") != null ? configs.getConfig("normalrefundstarthour").toString() : "00";
                        String normalrefundstartminute = configs.getConfig("normalrefundstartminute") != null ? configs.getConfig("normalrefundstartminute").toString() : "00";
                        String normalrefundendhour = configs.getConfig("normalrefundendhour") != null ? configs.getConfig("normalrefundendhour").toString() : "00";
                        String normalrefundendminute = configs.getConfig("normalrefundendminute") != null ? configs.getConfig("normalrefundendminute").toString() : "00";
                        boolean isRange = this.isRange(time, normalrefundstarthour, normalrefundstartminute, normalrefundendhour, normalrefundendminute);
                        result.put("isrefundRange", isRange);
                        result.put("ticketrefundstart", normalrefundstarthour + ":" + normalrefundstartminute);
                        result.put("ticketrefundend", normalrefundendhour + ":" + normalrefundendminute);
                    }
                }
            } else {//按工作时间出票
                if (festivalUtil.isWeekend(date)) {
                    String weekendrefundstarthour = configs.getConfig("weekendrefundstarthour") != null ? configs.getConfig("weekendrefundstarthour").toString() : "00";
                    String weekendrefundstartminute = configs.getConfig("weekendrefundstartminute") != null ? configs.getConfig("weekendrefundstartminute").toString() : "00";
                    String weekendrefundendhour = configs.getConfig("weekendrefundendhour") != null ? configs.getConfig("weekendrefundendhour").toString() : "00";
                    String weekendrefundendminute = configs.getConfig("weekendrefundendminute") != null ? configs.getConfig("weekendrefundendminute").toString() : "00";
                    boolean isRange = this.isRange(time, weekendrefundstarthour, weekendrefundstartminute, weekendrefundendhour, weekendrefundendminute);
                    result.put("isrefundRange", isRange);
                    result.put("ticketrefundstart", weekendrefundstarthour + ":" + weekendrefundstartminute);
                    result.put("ticketrefundend", weekendrefundendhour + ":" + weekendrefundendminute);
                } else {
                    String normalrefundstarthour = configs.getConfig("normalrefundstarthour") != null ? configs.getConfig("normalrefundstarthour").toString() : "00";
                    String normalrefundstartminute = configs.getConfig("normalrefundstartminute") != null ? configs.getConfig("normalrefundstartminute").toString() : "00";
                    String normalrefundendhour = configs.getConfig("normalrefundendhour") != null ? configs.getConfig("normalrefundendhour").toString() : "00";
                    String normalrefundendminute = configs.getConfig("normalrefundendminute") != null ? configs.getConfig("normalrefundendminute").toString() : "00";
                    boolean isRange = this.isRange(time, normalrefundstarthour, normalrefundstartminute, normalrefundendhour, normalrefundendminute);
                    result.put("isrefundRange", isRange);
                    result.put("ticketrefundstart", normalrefundstarthour + ":" + normalrefundstartminute);
                    result.put("ticketrefundend", normalrefundendhour + ":" + normalrefundendminute);
                }
            }
        } else {//为空的时候
            String sprcialstarthour = configs.getConfig("sprcialstarthour") != null ? configs.getConfig("sprcialstarthour").toString() : "00";
            String sprcialstratminute = configs.getConfig("sprcialstratminute") != null ? configs.getConfig("sprcialstratminute").toString() : "00";
            String sprcialendhour = configs.getConfig("sprcialendhour") != null ? configs.getConfig("sprcialendhour").toString() : "00";
            String sprcialendminute = configs.getConfig("sprcialendminute") != null ? configs.getConfig("sprcialendminute").toString() : "00";
            result.put("sprcialstart", sprcialstarthour + ":" + sprcialstratminute);
            result.put("sprcialend", sprcialendhour + ":" + sprcialendminute);
            String normalstarthour = configs.getConfig("normalstarthour") != null ? configs.getConfig("normalstarthour").toString() : "00";
            String normalstartminut = configs.getConfig("normalstartminut") != null ? configs.getConfig("normalstartminut").toString() : "00";
            String normalendhour = configs.getConfig("normalendhour") != null ? configs.getConfig("normalendhour").toString() : "00";
            String normalendminute = configs.getConfig("normalendminute") != null ? configs.getConfig("normalendminute").toString() : "00";
            String weekendendhour = configs.getConfig("weekendendhour") != null ? configs.getConfig("weekendendhour").toString() : "00";
            String weekendendminute = configs.getConfig("weekendendminute") != null ? configs.getConfig("weekendendminute").toString() : "00";
            String weekendstarthour = configs.getConfig("weekendstarthour") != null ? configs.getConfig("weekendstarthour").toString() : "00";
            String weekendstartminute = configs.getConfig("weekendstartminute") != null ? configs.getConfig("weekendstartminute").toString() : "00";
            result.put("normalstart", normalstarthour + ":" + normalstartminut);
            result.put("normalend", normalendhour + ":" + normalendminute);
            result.put("weekendend", weekendendhour + ":" + weekendendminute);
            result.put("weekendstarthour", weekendstarthour + ":" + weekendstartminute);
            String sprcialrefundendhour = configs.getConfig("sprcialrefundendhour") != null ? configs.getConfig("sprcialrefundendhour").toString() : "00";
            String sprcialrefundendminute = configs.getConfig("sprcialrefundendminute") != null ? configs.getConfig("sprcialrefundendminute").toString() : "00";
            String sprcialrefundstarthour = configs.getConfig("sprcialrefundstarthour") != null ? configs.getConfig("sprcialrefundstarthour").toString() : "00";
            String sprcialrefundstartminute = configs.getConfig("sprcialrefundstartminute") != null ? configs.getConfig("sprcialrefundstartminute").toString() : "00";
            result.put("sprcialrefundend", sprcialrefundendhour + ":" + sprcialrefundendminute);
            result.put("sprcialrefundstart", sprcialrefundstarthour + ":" + sprcialrefundstartminute);
            String normalrefundstarthour = configs.getConfig("normalrefundstarthour") != null ? configs.getConfig("normalrefundstarthour").toString() : "00";
            String normalrefundstartminute = configs.getConfig("normalrefundstartminute") != null ? configs.getConfig("normalrefundstartminute").toString() : "00";
            String normalrefundendhour = configs.getConfig("normalrefundendhour") != null ? configs.getConfig("normalrefundendhour").toString() : "00";
            String normalrefundendminute = configs.getConfig("normalrefundendminute") != null ? configs.getConfig("normalrefundendminute").toString() : "00";
            String weekendrefundstarthour = configs.getConfig("weekendrefundstarthour") != null ? configs.getConfig("weekendrefundstarthour").toString() : "00";
            String weekendrefundstartminute = configs.getConfig("weekendrefundstartminute") != null ? configs.getConfig("weekendrefundstartminute").toString() : "00";
            String weekendrefundendhour = configs.getConfig("weekendrefundendhour") != null ? configs.getConfig("weekendrefundendhour").toString() : "00";
            String weekendrefundendminute = configs.getConfig("weekendrefundendminute") != null ? configs.getConfig("weekendrefundendminute").toString() : "00";
            result.put("normalrefundstar", normalrefundstarthour + ":" + normalrefundstartminute);
            result.put("normalrefundend", normalrefundendhour + ":" + normalrefundendminute);
            result.put("weekendrefundstart", weekendrefundstarthour + ":" + weekendrefundstartminute);
            result.put("weekendrefundend", weekendrefundendhour + ":" + weekendrefundendminute);
        }
        return result;
    }

    @Override
    public boolean getIsDistributeTicket(Agent agent) {
        IFTConfigs configs = getConfigByChannelID(agent, 0l);
        if (configs.getConfig().get("isDistributeTicket")==null) {
            return false;
        }
        boolean isDistributeTicket = Boolean.parseBoolean((String) configs.getConfig().get("isDistributeTicket"));
        return isDistributeTicket;
    }

    @Override
    public <T> T setLockerAsAloneLocker(T ext) {
        if(ext instanceof SaleOrderExt){
            SaleOrderExt saleOrderExt = (SaleOrderExt) ext;
            saleOrderExt.setLocker(saleOrderExt.getAloneLocker());
            saleOrderExtDao.updateLocker(saleOrderExt);
        }
        if(ext instanceof SaleChangeExt){
            SaleChangeExt saleChangeExt = (SaleChangeExt) ext;
            saleChangeExt.setLocker(saleChangeExt.getAloneLocker());
            saleChangeExtDao.updateLocker(saleChangeExt);
        }
        return ext;
    }

    public boolean isRange(Date time, String starthour, String startminute, String endhour, String endminute) {
        System.out.println(time);
        Calendar cal = Calendar.getInstance();
        String times = IFTDateUtil.format(time, IFTDateUtil.DATE_TIME);
        Date endingTime = IFTDateUtil.parse(times, IFTDateUtil.DATE_TIME);
        cal.setTime(endingTime);
        int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
        int minute = cal.get(Calendar.MINUTE);//分
        int starthours = Integer.parseInt(starthour);
        int startminutes = Integer.parseInt(startminute);
        int endhours = Integer.parseInt(endhour);
        int endminutes = Integer.parseInt(endminute);
        if (hour < starthours || hour > endhours) {//小时大于等于开始时间，小于等于结束时间
            return false;
        } else {
            if (hour == starthours) {//例如8点30上班，我的时间是8点40，然后下班时间是18点20;
                if (minute < startminutes) {
                    return false;
                } else {   //这种情况是小时大于等于开始时间，小于等于结束时间，分钟分钟大于等于开始时间，小于等于结束时间
                    return true;
                }
            } else if (hour == endhours) {
                if (minute > endminutes) {
                    return false;
                } else {   //这种情况是小时大于等于开始时间，小于等于结束时间，分钟分钟大于等于开始时间，小于等于结束时间
                    return true;
                }
            } else {
                return true;
            }
        }
    }
}
