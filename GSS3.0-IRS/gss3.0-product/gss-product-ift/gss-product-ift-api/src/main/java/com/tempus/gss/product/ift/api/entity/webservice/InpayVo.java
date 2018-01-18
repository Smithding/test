
package com.tempus.gss.product.ift.api.entity.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for inpayVo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="inpayVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="branch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="custname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paydate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymoney" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="payno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payrecord" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payremark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payway" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inpayVo", propOrder = {
    "bank",
    "bankno",
    "branch",
    "custname",
    "customerid",
    "name",
    "orderid",
    "orderno",
    "paydate",
    "paymoney",
    "payno",
    "payrecord",
    "payremark",
    "payway"
})
public class InpayVo {

    protected String bank;
    protected String bankno;
    protected String branch;
    protected String custname;
    protected String customerid;
    protected String name;
    protected String orderid;
    protected String orderno;
    protected String paydate;
    protected Double paymoney;
    protected String payno;
    protected String payrecord;
    protected String payremark;
    protected String payway;

    /**
     * Gets the value of the bank property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBank() {
        return bank;
    }

    /**
     * Sets the value of the bank property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBank(String value) {
        this.bank = value;
    }

    /**
     * Gets the value of the bankno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankno() {
        return bankno;
    }

    /**
     * Sets the value of the bankno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankno(String value) {
        this.bankno = value;
    }

    /**
     * Gets the value of the branch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Sets the value of the branch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranch(String value) {
        this.branch = value;
    }

    /**
     * Gets the value of the custname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustname() {
        return custname;
    }

    /**
     * Sets the value of the custname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustname(String value) {
        this.custname = value;
    }

    /**
     * Gets the value of the customerid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * Sets the value of the customerid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerid(String value) {
        this.customerid = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the orderid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * Sets the value of the orderid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderid(String value) {
        this.orderid = value;
    }

    /**
     * Gets the value of the orderno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderno() {
        return orderno;
    }

    /**
     * Sets the value of the orderno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderno(String value) {
        this.orderno = value;
    }

    /**
     * Gets the value of the paydate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaydate() {
        return paydate;
    }

    /**
     * Sets the value of the paydate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaydate(String value) {
        this.paydate = value;
    }

    /**
     * Gets the value of the paymoney property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPaymoney() {
        return paymoney;
    }

    /**
     * Sets the value of the paymoney property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPaymoney(Double value) {
        this.paymoney = value;
    }

    /**
     * Gets the value of the payno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayno() {
        return payno;
    }

    /**
     * Sets the value of the payno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayno(String value) {
        this.payno = value;
    }

    /**
     * Gets the value of the payrecord property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayrecord() {
        return payrecord;
    }

    /**
     * Sets the value of the payrecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayrecord(String value) {
        this.payrecord = value;
    }

    /**
     * Gets the value of the payremark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayremark() {
        return payremark;
    }

    /**
     * Sets the value of the payremark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayremark(String value) {
        this.payremark = value;
    }

    /**
     * Gets the value of the payway property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayway() {
        return payway;
    }

    /**
     * Sets the value of the payway property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayway(String value) {
        this.payway = value;
    }

}
