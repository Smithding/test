package com.tempus.gss.product.ift.api.entity.setting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tempus.gss.product.ift.api.entity.EntityIft;
import com.tempus.gss.util.JsonUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * <b>渠道配置</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> wlh
 * <b>Date:</b> 2016年9月13日 下午2:34:53
 * <b>Copyright:</b> Copyright ©2016 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2016年9月13日 下午2:34:53    wlh
 *         new file.
 * </pre>
 */
@SuppressWarnings("unchecked")
public class IFTConfigs extends EntityIft {
    
    /**
     * SVUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 供应ID, 外键
     */
    protected Long channelId;
    protected String channelName;
    
    /**
     * 供应类型ID, 外键
     */
    protected Long channelType;
    protected String channelTypeName;
    
    /**
     * 适用Office号
     */
    protected String office;
    
    /**
     * 配置项, 格式如:{"num":1231, "switch":true, "times":3}
     */
    protected Map<String, Object> Config;
    
    /**
     * 备注,描述
     */
    protected String remark;
    
    /**
     * 状态, 0:禁用(默认); 1:启用
     */
    protected Integer status;
    
    /**
     * 构造方法
     */
    public IFTConfigs() {
        super();
    }
    
    /**
     * 构造方法
     *
     * @param owner
     * @param status
     */
    public IFTConfigs(int owner, Status status) {
        super();
        this.owner = owner;
        this.status = (null == status) ? null : status.getKey();
    }
    
    /**
     * 构造方法
     *
     * @param owner
     * @param channelId
     * @param modifier
     * @param modifyTime
     */
    public IFTConfigs(int owner, Long channelId, String modifier, Date modifyTime) {
        super();
        this.owner = owner;
        this.channelId = channelId;
        this.modifier = modifier;
        this.modifyTime = modifyTime;
    }
    
    /**
     * 构造方法
     *
     * @param channelId   客商ID, 外键
     * @param channelType 客商类型
     * @param Config      配置项参数
     * @param remark      备注,描述
     * @param status      状态
     */
    public IFTConfigs(Long channelId, Long channelType, Map<String, Object> Config, String remark, Status status) {
        super();
        this.channelId = channelId;
        this.channelType = channelType;
        this.office = MapUtils.isEmpty(Config) ? null : (null == Config.get("office") ? null : Config.get("office").toString());
        this.Config = Config;
        this.remark = remark;
        this.status = status.getKey();
    }
    
    @JsonIgnore
    @JSONField(serialize = false)
    public String getConfigs() {
        return JsonUtil.toJson(Config, "{}");
    }
    
    public Object getConfig(String code) {
        return (null == Config ? null : Config.get(code));
    }
    
    @JsonGetter(value = "IFTConfigs")
    @JSONField(serialize = true, name = "IFTConfigs")
    public Map<String, Object> getConfigsMap() {
        return Config;
    }
    
    public void setConfigs(String Config) {
        if (!StringUtils.isEmpty(Config)) {
            this.Config = JSON.parseObject(Config, Map.class);
        }
    }
    
    public void setConfig(String code, Object value) {
        if (null == this.Config) {
            this.Config = new HashMap<>();
        }
        this.Config.put(code, value);
    }
    
    public Map<String, Object> getConfig() {
        return Config;
    }
    
    public void setConfig(Map<String, Object> config) {
        Config = config;
    }
    
    
    public void setConfigs(Map<String, Object> values) {
        this.Config = values;
    }
    
    public String getStatusName() {
        return Status.getLabel(status);
    }
    
    public Long getChannelId() {
        return channelId;
    }
    
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
    
    public String getChannelName() {
        return channelName;
    }
    
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    
    public Long getChannelType() {
        return channelType;
    }
    
    public void setChannelType(Long channelType) {
        this.channelType = channelType;
    }
    
    public String getChannelTypeName() {
        return channelTypeName;
    }
    
    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }
    
    public String getOffice() {
        return office;
    }
    
    public void setOffice(String office) {
        this.office = office;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Config [id=" + id + ", owner=" + owner + ", channelId=" + channelId + ", channelName=" + channelName + ", channelType=" + channelType + ", channelTypeName=" + channelTypeName + ", office=" + office + ", Config=" + Config + ", remark=" + remark + ", status=" + status + ", creator=" + creator + ", createTime=" + createTime + ", modifier=" + modifier + ", modifyTime=" + modifyTime + "]";
    }
}
