package com.tempus.gss.product.hol.api.entity.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.ibm.icu.math.MathContext;
import com.tempus.gss.product.hol.api.entity.response.tc.OwnerOrderStatus;
import com.tempus.gss.product.hol.api.entity.response.tc.TcOrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 查询报表的实体类，当所有属性为空的时候，查询全部报表
 * 这里的属性为空指的是，这个类的属性为空，这个类的对象不能为空
 *
 * @author Michel
 */
public class ReportVO implements Serializable {
    private static final int SCALA = 3;
    
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 订单号
     */
    private String hotelOrderNo;
    /**
     * 订单号++
     */
    private String saleOrderNo;
    /**
     * 客户类型代码
     */
    private Integer customerType;
    /**
     * 客户类型
     */
    private String customerTypeName;
    /**
     * 客户名称
     */
    private String customerName;
    
    /**
     * 下单时间
     */
    private Date createTime;
    /**
     * 下单时间
     */
    private String createTimeString;
    /**
     * 酒店地址
     */
    private String holAddr;
    
    /**
     * 酒店名称
     */
    private String hotelName;
    
    /**
     * 入住人姓名
     */
    private String guestName;
    /**
     * 入住日期
     */
    private Date arrivalDate;
    
    /**
     * 离店日期
     */
    private Date departureDate;
    /**
     * 入住日期
     */
    private String arrivalDateString;
    
    /**
     * 离店日期
     */
    private String departureDateString;
    
    /**
     * 预定房间数
     */
    private Integer bookPro;
    /**
     * 预定夜数（住几天）
     */
    private Integer bookNight;
    /**
     * 间夜数（房间数*夜数）
     */
    private Integer proNight;
    /**
     * 总价
     */
    private BigDecimal totalPrice;
    /**
     * 佣金率
     */
    private BigDecimal rate;
    /**
     * 佣金
     */
    private BigDecimal saleRefund;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 订单状态
     */
    private String orderStatusName;
    /**
     * 审核状态
     */
    private Integer checkStatus;
    
    private String checkStatusName;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 客商编号
     */
    private Long customerNo;
    /**
     * 下单状态
     */
    private String resultCode;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行卡号
     */
    private Long cardNo;
    /**
     * 开户名称
     */
    private String userName;
    /**
     * 开户省份
     */
    private String province;
    /**
     * 开户城市
     */
    private String bankCity;
    /**
     * 开户地址
     */
    private String bankAddr;
    /**
     * 查询开始时间
     */
    private String startDate;
    /**
     * 查询结束时间
     */
    private String endDate;
    /**
     * 查询时间类别  1、预定时间  2、入住时间  3、离店时间
     */
    private Integer dateType;
    /**
     * 是否模糊查询  1、是  0、否（出票人、退票人)
     */
    private Integer exactlyQuery;
    /**
     * 1、默认明细。2、客户类型。3、按酒店。4、按分销商
     */
    private Integer queryType;
    /**
     * 毛利
     */
    private BigDecimal gProfit;
    /**
     * 净利
     */
    private BigDecimal profit;
    /**
     * 订单数
     */
    private Integer orderCount;
    /**
     * 实际入住人
     */
    private String factGuestName;
    /**
     * 实际到店时间
     */
    private Date factArrivalDate;
    /**
     * 实际到店时间
     */
    private String factArrivalDateString;
    /**
     * 实际离店时间
     */
    private Date factDepartureDate;
    /**
     * 实际离店时间
     */
    private String factDepartureDateString;
    /**
     * 实际入住间数
     */
    private Integer factBookPro;
    /**
     * 实际入住天数
     */
    private Integer factBookNight;
    
    /**
     * 实际入住间夜数
     */
    private Integer factProNight;
    
    /**
     * 实际总价
     */
    private BigDecimal factTotalPrice;
    /**
     * 实际返佣金
     */
    private BigDecimal factSaleRefund;
    /**
     * 实际返率
     */
    private BigDecimal factRate;
    
    public String getFactArrivalDateString() {
        return factArrivalDateString;
    }
    
    public void setFactArrivalDateString(String factArrivalDateString) {
        this.factArrivalDateString = factArrivalDateString;
    }
    
    public void setFactArrivalDateString(Date factArrivalDate) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        if (null != factArrivalDate) {
            this.factArrivalDateString = sf.format(factArrivalDate);
        } else {
            this.factArrivalDateString = "";
        }
        
    }
    
    public String getFactDepartureDateString() {
        return factDepartureDateString;
    }
    
    public void setFactDepartureDateString(String factDepartureDateString) {
        this.factDepartureDateString = factDepartureDateString;
    }
    
    public void setFactDepartureDateString(Date factDepartureDate) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        if (null != factDepartureDate) {
            this.factDepartureDateString = sf.format(factDepartureDate);
        } else {
            this.factDepartureDateString = "";
        }
    }
    
    public String getFactGuestName() {
        return factGuestName;
    }
    
    public void setFactGuestName(String factGuestName) {
        this.factGuestName = factGuestName;
    }
    
    public Date getFactArrivalDate() {
        return factArrivalDate;
    }
    
    public Integer getFactProNight() {
        if (null != this.factBookNight && null != factBookPro) {
            this.setFactProNight(factBookNight * factBookPro);
            return factProNight;
        } else {
            if (null != this.factProNight) {
                return factProNight;
            } else {
                return 0;
            }
        }
    }
    
    public void setFactProNight(Integer factProNight) {
        this.factProNight = factProNight;
    }
    
    public void setFactArrivalDate(Date factArrivalDate) {
        this.factArrivalDate = factArrivalDate;
    }
    
    public Date getFactDepartureDate() {
        return factDepartureDate;
    }
    
    public void setFactDepartureDate(Date factDepartureDate) {
        this.factDepartureDate = factDepartureDate;
    }
    
    public Integer getFactBookPro() {
        return factBookPro;
    }
    
    public void setFactBookPro(Integer factBookPro) {
        this.factBookPro = factBookPro;
    }
    
    public Integer getFactBookNight() {
        return factBookNight;
    }
    
    public void setFactBookNight(Integer factBookNight) {
        this.factBookNight = factBookNight;
    }
    
    public BigDecimal getFactTotalPrice() {
        return factTotalPrice;
    }
    
    public void setFactTotalPrice(BigDecimal factTotalPrice) {
        this.factTotalPrice = factTotalPrice;
    }
    
    public BigDecimal getFactSaleRefund() {
        return factSaleRefund;
    }
    
    public void setFactSaleRefund(BigDecimal factSaleRefund) {
        this.factSaleRefund = factSaleRefund;
    }
    
    public BigDecimal getFactRate() {
        if (null == this.factRate) {
            return new BigDecimal(0);
        }
        return factRate.setScale(SCALA, BigDecimal.ROUND_UP);
    }
    
    public void setFactRate(BigDecimal factRate) {
        this.factRate = factRate;
    }
    
    public Integer getOrderCount() {
        return orderCount;
    }
    
    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }
    
    public BigDecimal getgProfit() {
        return gProfit;
    }
    
    public void setgProfit(BigDecimal gProfit) {
        this.gProfit = gProfit;
    }
    
    public BigDecimal getProfit() {
        return profit;
    }
    
    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
    
    public Integer getQueryType() {
        return queryType;
    }
    
    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }
    
    public Integer getExactlyQuery() {
        return exactlyQuery;
    }
    
    public void setExactlyQuery(Integer exactlyQuery) {
        this.exactlyQuery = exactlyQuery;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public Integer getDateType() {
        return dateType;
    }
    
    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }
    
    /**
     * 重写toString方法
     *
     * @return
     */
    @Override
    public String toString() {
        return "ReportVO{" + "hotelOrderNo='" + hotelOrderNo + '\'' + ", customerType=" + customerType + ", customerTypeName='" + customerTypeName + '\'' + ", customerName='" + customerName + '\'' + ", createTime=" + createTime + ", holAddr='" + holAddr + '\'' + ", hotelName='" + hotelName + '\'' + ", guestName='" + guestName + '\'' + ", arrivalDate=" + arrivalDate + ", departureDate=" + departureDate + ", bookPro=" + bookPro + ", bookNight=" + bookNight + ", proNight=" + proNight + ", totalPrice=" + totalPrice + ", rate=" + rate + ", saleRefund=" + saleRefund + ", orderStatus='" + orderStatus + '\'' + ", checkStatus='" + checkStatus + '\'' + ", creator='" + creator + '\'' + ", customerNo=" + customerNo + ", resultCode='" + resultCode + '\'' + ", bankName='" + bankName + '\'' + ", cardNo=" + cardNo + ", userName='" + userName + '\'' + ", province='" + province + '\'' + ", bankCity='" + bankCity + '\'' + ", bankAddr='" + bankAddr + '\'' + '}';
    }
    
    public String getCheckStatusName() {
        return checkStatusName;
    }
    
    public void setCheckStatusName(String checkStatusName) {
        this.checkStatusName = checkStatusName;
    }
    
    public void setCheckStatusName(Integer checkStatus) {
        if (null != checkStatus) {
            setCheckStatusName(TcOrderStatus.keyOf(checkStatus).getValue());
        } else {
            this.checkStatusName = "";
        }
    }
    
    public String getOrderStatusName() {
        return orderStatusName;
    }
    
    public void setOrderStatusName(String orderStatus) {
        if (null != orderStatus && !"".equals(orderStatus)) {
            this.orderStatusName = OwnerOrderStatus.keyOf(orderStatus).getValue();
        } else {
            this.orderStatusName = "";
        }
    }
    
    public String getHotelOrderNo() {
        return hotelOrderNo;
    }
    
    public void setHotelOrderNo(String hotelOrderNo) {
        this.hotelOrderNo = hotelOrderNo;
    }
    
    public Integer getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }
    
    public String getCustomerTypeName() {
        return customerTypeName;
    }
    
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getHolAddr() {
        return holAddr;
    }
    
    public void setHolAddr(String holAddr) {
        this.holAddr = getCity(holAddr);
    }
    
    public String getHotelName() {
        return hotelName;
    }
    
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
    
    public String getGuestName() {
        return guestName;
    }
    
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
    
    public Date getArrivalDate() {
        return arrivalDate;
    }
    
    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
    
    public Date getDepartureDate() {
        return departureDate;
    }
    
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
    
    public Integer getBookPro() {
        return bookPro;
    }
    
    public void setBookPro(Integer bookPro) {
        this.bookPro = bookPro;
    }
    
    public Integer getBookNight() {
        
        return bookNight;
    }
    
    public void setBookNight(Integer bookNight) {
        this.bookNight = bookNight;
    }
    
    public void setProNight(Integer proNight) {
        this.proNight = proNight;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public BigDecimal getRate() {
        if (null == this.rate) {
            return new BigDecimal(0);
        }
        return this.rate.setScale(SCALA, BigDecimal.ROUND_UP);
    }
    
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
    
    public BigDecimal getSaleRefund() {
        return saleRefund;
    }
    
    public void setSaleRefund(BigDecimal saleRefund) {
        this.saleRefund = saleRefund;
    }
    
    public String getOrderStatus() {
        return orderStatus;
    }
    
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        setOrderStatusName(orderStatus);
    }
    
    public Integer getCheckStatus() {
        return checkStatus;
    }
    
    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }
    
    public String getCreator() {
        return creator;
    }
    
    public void setCreator(String creator) {
        this.creator = creator;
    }
    
    public Long getCustomerNo() {
        return customerNo;
    }
    
    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }
    
    public String getResultCode() {
        return resultCode;
    }
    
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    
    public String getBankName() {
        return bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    public Long getCardNo() {
        return cardNo;
    }
    
    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getProvince() {
        return province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getBankCity() {
        return bankCity;
    }
    
    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }
    
    public String getBankAddr() {
        return bankAddr;
    }
    
    public void setBankAddr(String bankAddr) {
        this.bankAddr = bankAddr;
    }
    
    public Integer getProNight() {
        if (null != this.bookNight && null != bookPro) {
            this.setProNight(bookNight * bookPro);
            return proNight;
        } else {
            if (null != this.proNight) {
                return proNight;
            } else {
                return 0;
            }
        }
    }
    
    public String getCreateTimeString() {
        return createTimeString;
    }
    
    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }
    
    public void setCreateTimeString(Date createTime) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        if (null != createTime) {
            this.createTimeString = sf.format(createTime);
        } else {
            this.createTimeString = "";
        }
    }
    
    public String getArrivalDateString() {
        return arrivalDateString;
    }
    
    public void setArrivalDateString(String arrivalDateString) {
        this.arrivalDateString = arrivalDateString;
    }
    
    public void setArrivalDateString(Date arrivalDate) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        if (null != arrivalDate) {
            this.arrivalDateString = sf.format(arrivalDate);
        } else {
            this.arrivalDateString = "";
        }
    }
    
    public String getDepartureDateString() {
        return departureDateString;
    }
    
    public void setDepartureDateString(String departureDateString) {
        
        this.departureDateString = departureDateString;
    }
    
    public void setDepartureDateString(Date departureDate) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        if (null != departureDate) {
            this.departureDateString = sf.format(departureDate);
        } else {
            this.departureDateString = "";
        }
    }
    
    public void setValues() {
        setArrivalDateString(arrivalDate);
        setDepartureDateString(departureDate);
        setFactArrivalDateString(factArrivalDate);
        setFactDepartureDateString(factDepartureDate);
        setCreateTimeString(createTime);
        setCheckStatusName(checkStatus);
    }
    
    private String getCity(String str) {
        if (null != str && !"".equals(str)) {
            String[] split1 = str.split("市");
            if (split1.length > 1) {
                return split1[0];
            } else {
                return str;
            }
        } else {
            return "";
        }
    }
    
    public String getSaleOrderNo() {
        return saleOrderNo;
    }
    
    public void setSaleOrderNo(String saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }
}