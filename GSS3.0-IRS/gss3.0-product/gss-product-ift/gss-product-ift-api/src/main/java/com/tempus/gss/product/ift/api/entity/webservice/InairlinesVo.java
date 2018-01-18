package com.tempus.gss.product.ift.api.entity.webservice;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for inairlinesVo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="inairlinesVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addprice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="afrerrentnpoint" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="arrivaltime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="baseprice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="cabin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deductedpoints" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="departuredate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="departuretime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flightno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oldticketno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passengerstype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proxyrate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="qvalue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="routing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="routingChinese" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="saleprice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
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
@XmlType(name = "inairlinesVo", propOrder = {
    "addprice",
    "afrerrentnpoint",
    "arrivaltime",
    "baseprice",
    "cabin",
    "deductedpoints",
    "departuredate",
    "departuretime",
    "flightno",
    "oldticketno",
    "passengerstype",
    "proxyrate",
    "qvalue",
    "routing",
    "routingChinese",
    "saleprice",
    "ticketno"
})
public class InairlinesVo {

    protected Double addprice;
    protected Double afrerrentnpoint;
    protected String arrivaltime;
    protected Double baseprice;
    protected String cabin;
    protected Double deductedpoints;
    protected String departuredate;
    protected String departuretime;
    protected String flightno;
    protected String oldticketno;
    protected String passengerstype;
    protected Double proxyrate;
    protected Double qvalue;
    protected String routing;
    protected String routingChinese;
    protected Double saleprice;
    protected String ticketno;

    /**
     * Gets the value of the addprice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAddprice() {
        return addprice;
    }

    /**
     * Sets the value of the addprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAddprice(Double value) {
        this.addprice = value;
    }

    /**
     * Gets the value of the afrerrentnpoint property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAfrerrentnpoint() {
        return afrerrentnpoint;
    }

    /**
     * Sets the value of the afrerrentnpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAfrerrentnpoint(Double value) {
        this.afrerrentnpoint = value;
    }

    /**
     * Gets the value of the arrivaltime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivaltime() {
        return arrivaltime;
    }

    /**
     * Sets the value of the arrivaltime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivaltime(String value) {
        this.arrivaltime = value;
    }

    /**
     * Gets the value of the baseprice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBaseprice() {
        return baseprice;
    }

    /**
     * Sets the value of the baseprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBaseprice(Double value) {
        this.baseprice = value;
    }

    /**
     * Gets the value of the cabin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCabin() {
        return cabin;
    }

    /**
     * Sets the value of the cabin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCabin(String value) {
        this.cabin = value;
    }

    /**
     * Gets the value of the deductedpoints property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDeductedpoints() {
        return deductedpoints;
    }

    /**
     * Sets the value of the deductedpoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDeductedpoints(Double value) {
        this.deductedpoints = value;
    }

    /**
     * Gets the value of the departuredate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeparturedate() {
        return departuredate;
    }

    /**
     * Sets the value of the departuredate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeparturedate(String value) {
        this.departuredate = value;
    }

    /**
     * Gets the value of the departuretime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeparturetime() {
        return departuretime;
    }

    /**
     * Sets the value of the departuretime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeparturetime(String value) {
        this.departuretime = value;
    }

    /**
     * Gets the value of the flightno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightno() {
        return flightno;
    }

    /**
     * Sets the value of the flightno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightno(String value) {
        this.flightno = value;
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
     * Gets the value of the passengerstype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassengerstype() {
        return passengerstype;
    }

    /**
     * Sets the value of the passengerstype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassengerstype(String value) {
        this.passengerstype = value;
    }

    /**
     * Gets the value of the proxyrate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getProxyrate() {
        return proxyrate;
    }

    /**
     * Sets the value of the proxyrate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setProxyrate(Double value) {
        this.proxyrate = value;
    }

    /**
     * Gets the value of the qvalue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getQvalue() {
        return qvalue;
    }

    /**
     * Sets the value of the qvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setQvalue(Double value) {
        this.qvalue = value;
    }

    /**
     * Gets the value of the routing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRouting() {
        return routing;
    }

    /**
     * Sets the value of the routing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRouting(String value) {
        this.routing = value;
    }

    /**
     * Gets the value of the routingChinese property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingChinese() {
        return routingChinese;
    }

    /**
     * Sets the value of the routingChinese property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingChinese(String value) {
        this.routingChinese = value;
    }

    /**
     * Gets the value of the saleprice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSaleprice() {
        return saleprice;
    }

    /**
     * Sets the value of the saleprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSaleprice(Double value) {
        this.saleprice = value;
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
