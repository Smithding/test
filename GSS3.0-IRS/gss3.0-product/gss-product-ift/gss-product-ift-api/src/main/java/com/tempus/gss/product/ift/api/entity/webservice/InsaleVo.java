
package com.tempus.gss.product.ift.api.entity.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for insaleVo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="insaleVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actualbaseprice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="actualproxyrate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="basepricetotal" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="birthdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="chineseName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idnumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invalidbaseprice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="invalidprice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="lianticketno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nationality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="num" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="oldticketno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paidamount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="papersvalid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passengername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passengersex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pmcurrency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="profit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="salesmargin" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tax" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ticketno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "insaleVo", propOrder = {
    "actualbaseprice",
    "actualproxyrate",
    "basepricetotal",
    "birthdate",
    "chineseName",
    "currency",
    "idnumber",
    "inType",
    "invalidbaseprice",
    "invalidprice",
    "lianticketno",
    "nationality",
    "num",
    "oldticketno",
    "paidamount",
    "papersvalid",
    "passengername",
    "passengersex",
    "pmcurrency",
    "profit",
    "salesmargin",
    "tax",
    "ticketno"
})
public class InsaleVo {

    protected Double actualbaseprice;
    protected Double actualproxyrate;
    protected Double basepricetotal;
    protected String birthdate;
    protected String chineseName;
    protected String currency;
    protected String idnumber;
    protected String inType;
    protected Double invalidbaseprice;
    protected Double invalidprice;
    protected String lianticketno;
    protected String nationality;
    protected Integer num;
    protected String oldticketno;
    protected Double paidamount;
    protected String papersvalid;
    protected String passengername;
    protected String passengersex;
    protected String pmcurrency;
    protected Double profit;
    protected Double salesmargin;
    protected Double tax;
    protected String ticketno;

    /**
     * Gets the value of the actualbaseprice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getActualbaseprice() {
        return actualbaseprice;
    }

    /**
     * Sets the value of the actualbaseprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setActualbaseprice(Double value) {
        this.actualbaseprice = value;
    }

    /**
     * Gets the value of the actualproxyrate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getActualproxyrate() {
        return actualproxyrate;
    }

    /**
     * Sets the value of the actualproxyrate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setActualproxyrate(Double value) {
        this.actualproxyrate = value;
    }

    /**
     * Gets the value of the basepricetotal property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBasepricetotal() {
        return basepricetotal;
    }

    /**
     * Sets the value of the basepricetotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBasepricetotal(Double value) {
        this.basepricetotal = value;
    }

    /**
     * Gets the value of the birthdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * Sets the value of the birthdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthdate(String value) {
        this.birthdate = value;
    }

    /**
     * Gets the value of the chineseName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChineseName() {
        return chineseName;
    }

    /**
     * Sets the value of the chineseName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChineseName(String value) {
        this.chineseName = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the idnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdnumber() {
        return idnumber;
    }

    /**
     * Sets the value of the idnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdnumber(String value) {
        this.idnumber = value;
    }

    /**
     * Gets the value of the inType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInType() {
        return inType;
    }

    /**
     * Sets the value of the inType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInType(String value) {
        this.inType = value;
    }

    /**
     * Gets the value of the invalidbaseprice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getInvalidbaseprice() {
        return invalidbaseprice;
    }

    /**
     * Sets the value of the invalidbaseprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setInvalidbaseprice(Double value) {
        this.invalidbaseprice = value;
    }

    /**
     * Gets the value of the invalidprice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getInvalidprice() {
        return invalidprice;
    }

    /**
     * Sets the value of the invalidprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setInvalidprice(Double value) {
        this.invalidprice = value;
    }

    /**
     * Gets the value of the lianticketno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLianticketno() {
        return lianticketno;
    }

    /**
     * Sets the value of the lianticketno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLianticketno(String value) {
        this.lianticketno = value;
    }

    /**
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationality(String value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the num property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNum() {
        return num;
    }

    /**
     * Sets the value of the num property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNum(Integer value) {
        this.num = value;
    }

    /**
     * Gets the value of the oldticketno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldticketno() {
        return oldticketno;
    }

    /**
     * Sets the value of the oldticketno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldticketno(String value) {
        this.oldticketno = value;
    }

    /**
     * Gets the value of the paidamount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPaidamount() {
        return paidamount;
    }

    /**
     * Sets the value of the paidamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPaidamount(Double value) {
        this.paidamount = value;
    }

    /**
     * Gets the value of the papersvalid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPapersvalid() {
        return papersvalid;
    }

    /**
     * Sets the value of the papersvalid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPapersvalid(String value) {
        this.papersvalid = value;
    }

    /**
     * Gets the value of the passengername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassengername() {
        return passengername;
    }

    /**
     * Sets the value of the passengername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassengername(String value) {
        this.passengername = value;
    }

    /**
     * Gets the value of the passengersex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassengersex() {
        return passengersex;
    }

    /**
     * Sets the value of the passengersex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassengersex(String value) {
        this.passengersex = value;
    }

    /**
     * Gets the value of the pmcurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPmcurrency() {
        return pmcurrency;
    }

    /**
     * Sets the value of the pmcurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPmcurrency(String value) {
        this.pmcurrency = value;
    }

    /**
     * Gets the value of the profit property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getProfit() {
        return profit;
    }

    /**
     * Sets the value of the profit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setProfit(Double value) {
        this.profit = value;
    }

    /**
     * Gets the value of the salesmargin property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSalesmargin() {
        return salesmargin;
    }

    /**
     * Sets the value of the salesmargin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSalesmargin(Double value) {
        this.salesmargin = value;
    }

    /**
     * Gets the value of the tax property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTax(Double value) {
        this.tax = value;
    }

    /**
     * Gets the value of the ticketno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketno() {
        return ticketno;
    }

    /**
     * Sets the value of the ticketno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketno(String value) {
        this.ticketno = value;
    }

}
