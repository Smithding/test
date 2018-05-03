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
    /**
     * 用户id
     */
    private String userid;
    /**
     * 姓名
     */
    private String name;
    /***状态*/
    private Integer status;
    /***优先级*/
    private Integer no;
    /**
     * 创建人
     */
    private String creator;
    /***操作人*/
    private String modifier;
    /**
     * 创建时间
     */
    private Date createtime;
    /***修改时间*/
    private Date updatetime;
    /**
     * 订单数
     */
    private Long ordercount;
    /***标记*/
    private Integer point;
    /**
     * 顺序号
     */
    private Long sequenceNo;
    //操作员类型 销售员-salesMan 出票员-ticketSender
    private String type;
    //待处理销售订单数量
    private Integer saleOrderNum;
    //采购退票数量
    private Integer buyRefuseNum;
    //销售退票数量
    private Integer saleRefuseNum;
    //采购改签数量
    private Integer buyChangeNum;
    //销售改签数量
    private Integer saleChangeNum;
    /***多个ID组成的字符串*/
    @Transient
    private String ids;

    //改签数量总和
    private Integer totalChangeNum;
    //退票总和
    private Integer totalRefuseNum;

    /**
     * 修改信息
     */
    @Transient
    private String updateInfo;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TicketSender{");
        sb.append("id=").append(id);
        sb.append(", userid='").append(userid).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", status=").append(status);
        sb.append(", no=").append(no);
        sb.append(", creator='").append(creator).append('\'');
        sb.append(", modifier='").append(modifier).append('\'');
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", ordercount=").append(ordercount);
        sb.append(", point=").append(point);
        sb.append(", sequenceNo=").append(sequenceNo);
        sb.append(", type='").append(type).append('\'');
        sb.append(", saleOrderNum=").append(saleOrderNum);
        sb.append(", buyRefuseNum=").append(buyRefuseNum);
        sb.append(", saleRefuseNum=").append(saleRefuseNum);
        sb.append(", buyChangeNum=").append(buyChangeNum);
        sb.append(", saleChangeNum=").append(saleChangeNum);
        sb.append(", ids='").append(ids).append('\'');
        sb.append(", updateInfo='").append(updateInfo).append('\'');
        sb.append(", totalChangeNum='").append(totalChangeNum).append('\'');
        sb.append(", totalRefuseNum='").append(totalRefuseNum).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getBuyRefuseNum() {
        return buyRefuseNum;
    }

    public void setBuyRefuseNum(Integer buyRefuseNum) {
        this.buyRefuseNum = buyRefuseNum;
    }

    public Integer getSaleRefuseNum() {
        return saleRefuseNum;
    }

    public void setSaleRefuseNum(Integer saleRefuseNum) {
        this.saleRefuseNum = saleRefuseNum;
    }

    public Integer getBuyChangeNum() {
        return buyChangeNum;
    }

    public void setBuyChangeNum(Integer buyChangeNum) {
        this.buyChangeNum = buyChangeNum;
    }

    public Integer getSaleChangeNum() {
        return saleChangeNum;
    }

    public void setSaleChangeNum(Integer saleChangeNum) {
        this.saleChangeNum = saleChangeNum;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSaleOrderNum() {
        return saleOrderNum;
    }

    public void setSaleOrderNum(Integer saleOrderNum) {
        this.saleOrderNum = saleOrderNum;
    }

    public Integer getTotalChangeNum() { return totalChangeNum; }

    public void setTotalChangeNum(Integer totalChangeNum) { this.totalChangeNum = totalChangeNum; }

    public Integer getTotalRefuseNum() {
        return totalRefuseNum;
    }

    public void setTotalRefuseNum(Integer totalRefuseNum) {
        this.totalRefuseNum = totalRefuseNum;
    }

    public String getUpdateInfo() {
        String info = "";
        if (this.updatetime != null && StringUtils.isNotBlank(this.modifier)) {
            info = this.modifier + "<br/>" + DateUtil.dateToDateString(this.updatetime, "YYYY-MM-dd HH:mm:ss");
        }
        return info;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }
}
