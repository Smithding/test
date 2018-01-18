package com.tempus.gss.product.tra.api.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "orderNumber",
    "payedPrice",
    "payTime",
    "payType",
    "tradeNumber"
})
public class OrderPayReq implements Serializable {

    @XmlElement(name = "OrderNumber")
    protected String orderNumber;
    @XmlElement(name = "PayedPrice", required = true)
    protected String payedPrice;
    @XmlElement(name = "PayTime", required = true)
    protected String payTime;
    @XmlElement(name = "PayType", required = true)
    protected String payType;
    @XmlElement(required = true)
    protected String tradeNumber;

    /**
     * Gets the value of the orderNumber property.
     * 
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the value of the orderNumber property.
     * 
     */
    public void setOrderNumber(String value) {
        this.orderNumber = value;
    }

    /**
     * Gets the value of the payedPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayedPrice() {
        return payedPrice;
    }

    /**
     * Sets the value of the payedPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayedPrice(String value) {
        this.payedPrice = value;
    }

    /**
     * Gets the value of the payTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayTime() {
        return payTime;
    }

    /**
     * Sets the value of the payTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayTime(String value) {
        this.payTime = value;
    }

    /**
     * Gets the value of the payType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayType() {
        return payType;
    }

    /**
     * Sets the value of the payType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayType(String value) {
        this.payType = value;
    }

    /**
     * Gets the value of the tradeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeNumber() {
        return tradeNumber;
    }

    /**
     * Sets the value of the tradeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeNumber(String value) {
        this.tradeNumber = value;
    }

}
