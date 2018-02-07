package com.tempus.gss.product.ift.api.entity.webservice.settt;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.tempus.gss.product.ift.api.entity.webservice.InairlinesVo;
import com.tempus.gss.product.ift.api.entity.webservice.InpayVo;
import com.tempus.gss.product.ift.api.entity.webservice.InsaleVo;


/**
 * <p>Java class for inallsaleVo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="inallsaleVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agencyid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agreement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aircode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="airlinesList" type="{http://tempusservice.tempus.com}inairlinesVo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="billingCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bookticketperson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bookticketpersondep" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clearingway" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contacts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customeradd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorway" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filenumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invoicecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isGss" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isOnline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="issuedate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="issuer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="limitcondition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="memberid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payList" type="{http://tempusservice.tempus.com}inpayVo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="paymentterms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paystatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="platformorderid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="platfromcustomername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pnr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="policy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="saleList" type="{http://tempusservice.tempus.com}insaleVo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="salecom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="saledept" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="selldate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sellperson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="supplier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="supplieradd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tanagementarea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticketcom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticketdep" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tickettype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tripartiteAgreementNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inallsaleVo", propOrder = {
    "agencyid",
    "agreement",
    "aircode",
    "airlinesList",
    "billingCode",
    "billtype",
    "bookticketperson",
    "bookticketpersondep",
    "businessline",
    "clearingway",
    "contacts",
    "customeradd",
    "customerid",
    "customername",
    "errorway",
    "filenumber",
    "invoicecode",
    "isGss",
    "isOnline",
    "issuedate",
    "issuer",
    "legtype",
    "limitcondition",
    "memberid",
    "orderid",
    "payList",
    "paymentterms",
    "payno",
    "paystatus",
    "phone",
    "platformorderid",
    "platfromcustomername",
    "pnr",
    "policy",
    "rate",
    "remark",
    "saleList",
    "salecom",
    "saledept",
    "selldate",
    "sellperson",
    "status",
    "supplier",
    "supplieradd",
    "tanagementarea",
    "ticketcom",
    "ticketdep",
    "tickettype",
    "tripartiteAgreementNo",
    "type"
})
public class InallsaleVo {

    protected String agencyid;
    protected String agreement;
    protected String aircode;
    @XmlElement(nillable = true)
    protected List<InairlinesVo> airlinesList;
    protected String billingCode;
    protected String billtype;
    protected String bookticketperson;
    protected String bookticketpersondep;
    protected String businessline;
    protected String clearingway;
    protected String contacts;
    protected String customeradd;
    protected String customerid;
    protected String customername;
    protected String errorway;
    protected String filenumber;
    protected String invoicecode;
    protected String isGss;
    protected String isOnline;
    protected String issuedate;
    protected String issuer;
    protected String legtype;
    protected String limitcondition;
    protected String memberid;
    protected String orderid;
    @XmlElement(nillable = true)
    protected List<InpayVo> payList;
    protected String paymentterms;
    protected String payno;
    protected String paystatus;
    protected String phone;
    protected String platformorderid;
    protected String platfromcustomername;
    protected String pnr;
    protected String policy;
    protected Double rate;
    protected String remark;
    @XmlElement(nillable = true)
    protected List<InsaleVo> saleList;
    protected String salecom;
    protected String saledept;
    protected String selldate;
    protected String sellperson;
    protected String status;
    protected String supplier;
    protected String supplieradd;
    protected String tanagementarea;
    protected String ticketcom;
    protected String ticketdep;
    protected String tickettype;
    protected String tripartiteAgreementNo;
    protected String type;

    /**
     * Gets the value of the agencyid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgencyid() {
        return agencyid;
    }

    /**
     * Sets the value of the agencyid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgencyid(String value) {
        this.agencyid = value;
    }

    /**
     * Gets the value of the agreement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreement() {
        return agreement;
    }

    /**
     * Sets the value of the agreement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreement(String value) {
        this.agreement = value;
    }

    /**
     * Gets the value of the aircode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAircode() {
        return aircode;
    }

    /**
     * Sets the value of the aircode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAircode(String value) {
        this.aircode = value;
    }

    /**
     * Gets the value of the airlinesList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the airlinesList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAirlinesList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InairlinesVo }
     * 
     * 
     */
    public List<InairlinesVo> getAirlinesList() {
        if (airlinesList == null) {
            airlinesList = new ArrayList<InairlinesVo>();
        }
        return this.airlinesList;
    }

    /**
     * Gets the value of the billingCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingCode() {
        return billingCode;
    }

    /**
     * Sets the value of the billingCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingCode(String value) {
        this.billingCode = value;
    }

    /**
     * Gets the value of the billtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * Sets the value of the billtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBilltype(String value) {
        this.billtype = value;
    }

    /**
     * Gets the value of the bookticketperson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookticketperson() {
        return bookticketperson;
    }

    /**
     * Sets the value of the bookticketperson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookticketperson(String value) {
        this.bookticketperson = value;
    }

    /**
     * Gets the value of the bookticketpersondep property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookticketpersondep() {
        return bookticketpersondep;
    }

    /**
     * Sets the value of the bookticketpersondep property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookticketpersondep(String value) {
        this.bookticketpersondep = value;
    }

    /**
     * Gets the value of the businessline property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessline() {
        return businessline;
    }

    /**
     * Sets the value of the businessline property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessline(String value) {
        this.businessline = value;
    }

    /**
     * Gets the value of the clearingway property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClearingway() {
        return clearingway;
    }

    /**
     * Sets the value of the clearingway property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClearingway(String value) {
        this.clearingway = value;
    }

    /**
     * Gets the value of the contacts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * Sets the value of the contacts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContacts(String value) {
        this.contacts = value;
    }

    /**
     * Gets the value of the customeradd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomeradd() {
        return customeradd;
    }

    /**
     * Sets the value of the customeradd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomeradd(String value) {
        this.customeradd = value;
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
     * Gets the value of the customername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomername() {
        return customername;
    }

    /**
     * Sets the value of the customername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomername(String value) {
        this.customername = value;
    }

    /**
     * Gets the value of the errorway property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorway() {
        return errorway;
    }

    /**
     * Sets the value of the errorway property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorway(String value) {
        this.errorway = value;
    }

    /**
     * Gets the value of the filenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilenumber() {
        return filenumber;
    }

    /**
     * Sets the value of the filenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilenumber(String value) {
        this.filenumber = value;
    }

    /**
     * Gets the value of the invoicecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoicecode() {
        return invoicecode;
    }

    /**
     * Sets the value of the invoicecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoicecode(String value) {
        this.invoicecode = value;
    }

    /**
     * Gets the value of the isGss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsGss() {
        return isGss;
    }

    /**
     * Sets the value of the isGss property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsGss(String value) {
        this.isGss = value;
    }

    /**
     * Gets the value of the isOnline property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsOnline() {
        return isOnline;
    }

    /**
     * Sets the value of the isOnline property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsOnline(String value) {
        this.isOnline = value;
    }

    /**
     * Gets the value of the issuedate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuedate() {
        return issuedate;
    }

    /**
     * Sets the value of the issuedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuedate(String value) {
        this.issuedate = value;
    }

    /**
     * Gets the value of the issuer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * Sets the value of the issuer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuer(String value) {
        this.issuer = value;
    }

    /**
     * Gets the value of the legtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegtype() {
        return legtype;
    }

    /**
     * Sets the value of the legtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegtype(String value) {
        this.legtype = value;
    }

    /**
     * Gets the value of the limitcondition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitcondition() {
        return limitcondition;
    }

    /**
     * Sets the value of the limitcondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitcondition(String value) {
        this.limitcondition = value;
    }

    /**
     * Gets the value of the memberid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemberid() {
        return memberid;
    }

    /**
     * Sets the value of the memberid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemberid(String value) {
        this.memberid = value;
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
     * Gets the value of the payList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the payList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPayList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InpayVo }
     * 
     * 
     */
    public List<InpayVo> getPayList() {
        if (payList == null) {
            payList = new ArrayList<InpayVo>();
        }
        return this.payList;
    }

    /**
     * Gets the value of the paymentterms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentterms() {
        return paymentterms;
    }

    /**
     * Sets the value of the paymentterms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentterms(String value) {
        this.paymentterms = value;
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
     * Gets the value of the paystatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaystatus() {
        return paystatus;
    }

    /**
     * Sets the value of the paystatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaystatus(String value) {
        this.paystatus = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the platformorderid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlatformorderid() {
        return platformorderid;
    }

    /**
     * Sets the value of the platformorderid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlatformorderid(String value) {
        this.platformorderid = value;
    }

    /**
     * Gets the value of the platfromcustomername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlatfromcustomername() {
        return platfromcustomername;
    }

    /**
     * Sets the value of the platfromcustomername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlatfromcustomername(String value) {
        this.platfromcustomername = value;
    }

    /**
     * Gets the value of the pnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPnr() {
        return pnr;
    }

    /**
     * Sets the value of the pnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPnr(String value) {
        this.pnr = value;
    }

    /**
     * Gets the value of the policy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicy() {
        return policy;
    }

    /**
     * Sets the value of the policy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicy(String value) {
        this.policy = value;
    }

    /**
     * Gets the value of the rate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRate(Double value) {
        this.rate = value;
    }

    /**
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * Gets the value of the saleList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the saleList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSaleList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InsaleVo }
     * 
     * 
     */
    public List<InsaleVo> getSaleList() {
        if (saleList == null) {
            saleList = new ArrayList<InsaleVo>();
        }
        return this.saleList;
    }

    /**
     * Gets the value of the salecom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalecom() {
        return salecom;
    }

    /**
     * Sets the value of the salecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalecom(String value) {
        this.salecom = value;
    }

    /**
     * Gets the value of the saledept property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaledept() {
        return saledept;
    }

    /**
     * Sets the value of the saledept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaledept(String value) {
        this.saledept = value;
    }

    /**
     * Gets the value of the selldate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelldate() {
        return selldate;
    }

    /**
     * Sets the value of the selldate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelldate(String value) {
        this.selldate = value;
    }

    /**
     * Gets the value of the sellperson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSellperson() {
        return sellperson;
    }

    /**
     * Sets the value of the sellperson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSellperson(String value) {
        this.sellperson = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the supplier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * Sets the value of the supplier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplier(String value) {
        this.supplier = value;
    }

    /**
     * Gets the value of the supplieradd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplieradd() {
        return supplieradd;
    }

    /**
     * Sets the value of the supplieradd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplieradd(String value) {
        this.supplieradd = value;
    }

    /**
     * Gets the value of the tanagementarea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTanagementarea() {
        return tanagementarea;
    }

    /**
     * Sets the value of the tanagementarea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTanagementarea(String value) {
        this.tanagementarea = value;
    }

    /**
     * Gets the value of the ticketcom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketcom() {
        return ticketcom;
    }

    /**
     * Sets the value of the ticketcom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketcom(String value) {
        this.ticketcom = value;
    }

    /**
     * Gets the value of the ticketdep property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketdep() {
        return ticketdep;
    }

    /**
     * Sets the value of the ticketdep property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketdep(String value) {
        this.ticketdep = value;
    }

    /**
     * Gets the value of the tickettype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTickettype() {
        return tickettype;
    }

    /**
     * Sets the value of the tickettype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTickettype(String value) {
        this.tickettype = value;
    }

    /**
     * Gets the value of the tripartiteAgreementNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTripartiteAgreementNo() {
        return tripartiteAgreementNo;
    }

    /**
     * Sets the value of the tripartiteAgreementNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTripartiteAgreementNo(String value) {
        this.tripartiteAgreementNo = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }
    
    public void setAirlinesList(List<InairlinesVo> airlinesList) {
		this.airlinesList = airlinesList;
	}

	public void setPayList(List<InpayVo> payList) {
		this.payList = payList;
	}

	public void setSaleList(List<InsaleVo> saleList) {
		this.saleList = saleList;
	}

}
