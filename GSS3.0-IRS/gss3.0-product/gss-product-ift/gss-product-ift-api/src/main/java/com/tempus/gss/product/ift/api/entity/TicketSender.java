package com.tempus.gss.product.ift.api.entity;

import com.tempus.gss.bbp.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/21 0021.
 */
public class TicketSender implements Serializable {

    private Long id;
    /**用户id*/
    private String userid;
    /**姓名*/
    private String name;
    /***状态*/
    private Integer status;
    /***优先级*/
    private Integer no;
    /**创建人*/
    private String creator;
    /***操作人*/
    private String modifier;
    /**创建时间*/
    private Date createtime;
    /***修改时间*/
    private Date updatetime;
    /**订单数*/
    private Long ordercount;
    /***标记*/
    private Integer point;
    /**顺序号*/
    private Long sequenceNo;
    /***多个ID组成的字符串*/
    @Transient
    private String ids;

    /**修改信息*/
    @Transient
    private String updateInfo;

    @Override
    public String toString() {
        return "TicketSender{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", no=" + no +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", ordercount=" + ordercount +
                ", point=" + point +
                ", sequenceNo=" + sequenceNo +
                ", ids='" + ids + '\'' +
                ", updateInfo='" + updateInfo + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getOrdercount() {
        return ordercount;
    }

    public void setOrdercount(Long ordercount) {
        this.ordercount = ordercount;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Long getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getUpdateInfo() {
        String info = "";
        if(this.updatetime!=null&& StringUtils.isNotBlank(this.modifier)){
            info = this.modifier+"<br/>"+DateUtil.dateToDateString(this.updatetime,"YYYYMMdd HH:mm:ss");
        }
        return info;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }
}
