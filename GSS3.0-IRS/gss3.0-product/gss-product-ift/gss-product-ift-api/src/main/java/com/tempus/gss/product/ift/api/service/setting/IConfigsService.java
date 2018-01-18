package com.tempus.gss.product.ift.api.service.setting;

import com.tempus.gss.cps.entity.Channel;
import com.tempus.gss.product.ift.api.entity.setting.IFTConfigs;
import com.tempus.gss.product.ift.api.entity.setting.Status;
import com.tempus.gss.product.ift.api.exceptions.ConfigsException;
import com.tempus.gss.vo.Agent;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * <b>客商配置维护服务接口.</b>
 * <b>Description:</b>
 * <b>Author:</b> wlh
 * <b>Date:</b> 2016年9月13日 下午2:19:42
 * <b>Copyright:</b> Copyright ©2016 tempus.cn. All rights reserved.
 * <b>Changelog:</b> Ver Date Author Detail
 * ----------------------------------------------------------------------
 * 1.0 2016年9月13日 下午2:19:42 wlh new file
 * </pre>
 */
public interface IConfigsService {
    
    /**
     * 获取指定客商ID对应的配置
     *
     * @param agent     终端信息
     * @param channelId 客商ID
     * @return IFTConfigs 客商配置信息
     * @throws ConfigsException
     */
    IFTConfigs getConfigByChannelID(Agent agent, Long channelId) throws ConfigsException;
    
    /**
     * 获取多个客商ID对应的配置
     *
     * @param agent      终端信息
     * @param channelIds 多个渠道ID
     * @return List<IFTConfigs> 客商配置信息清单
     * @throws ConfigsException
     */
    List<IFTConfigs> getByIds(Agent agent, Long... channelIds) throws ConfigsException;
    
    /**
     * 提取系统所有的供应商（含配置）列表
     *
     * @param agent 终端信息
     * @return List<Channel> 供应商清单
     * @throws ConfigsException
     */
    List<Channel> getAllConfigs(Agent agent, Status status) throws ConfigsException;
    
    /**
     * 查询符合条件的客商配置
     *
     * @param agent
     * @param channelId
     * @param channelType
     * @param status
     * @return List<IFTConfigs>
     * @throws ConfigsException
     */
    List<IFTConfigs> query(Agent agent, Long channelId, Long channelType, Status status) throws ConfigsException;
    
    /**
     * 保存客商的相关配置信息
     *
     * @param agent       终端信息
     * @param id          客商配置ID
     * @param channelId   客商ID
     * @param channelType 客商类型
     * @param configs     配置集合
     * @param remark      备注信息
     * @param status      状态
     * @return long 客商配置ID
     * @throws ConfigsException
     */
    long save(Agent agent, Long id, Long channelId, Long channelType, Map<String, Object> configs, String remark, Status status) throws ConfigsException;
    
    /**
     * 移除客商ID的配置信息
     *
     * @param agent     终端信息
     * @param channelId 客商ID
     * @throws ConfigsException
     */
    void remove(Agent agent, Long channelId) throws ConfigsException;
    
    Map<String, Object> getInfoByTime(Agent agent, Long channelId, Date time) throws ConfigsException;
    
}
