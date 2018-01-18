package com.tempus.gss.product.tra.api.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "contactName",
    "contactMobile",
    "orderNumber",
    "ticketInfo"
})
public class OrderReturnReq {

    @XmlElement(required = true)
    protected String contactName;
    @XmlElement(required = true)
    protected String contactMobile;
    @XmlElement(name = "OrderNumber", required = true)
    protected String orderNumber;
    @XmlElement(name = "TicketInfo", required = true)
    protected TicketInfo ticketInfo;
    private String passengerId;

    public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	/**
     * Gets the value of the contactName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the value of the contactName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactName(String value) {
        this.contactName = value;
    }

    /**
     * Gets the value of the contactMobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactMobile() {
        return contactMobile;
    }

    /**
     * Sets the value of the contactMobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactMobile(String value) {
        this.contactMobile = value;
    }

    /**
     * Gets the value of the orderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the value of the orderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNumber(String value) {
        this.orderNumber = value;
    }

    /**
     * Gets the value of the ticketInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TicketInfo }
     *     
     */
    public TicketInfo getTicketInfo() {
        return ticketInfo;
    }

    /**
     * Sets the value of the ticketInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TicketInfo }
     *     
     */
    public void setTicketInfo(TicketInfo value) {
        this.ticketInfo = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="refundTicket">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="childBillId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="eOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="eOrderType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="seatNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="passport" type="{http://www.w3.org/2001/XMLSchema}String"/>
     *                   &lt;element name="passportName" type="{http://www.w3.org/2001/XMLSchema}String"/>
     *                   &lt;element name="realName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "refundTicket"
    })
    public static class TicketInfo {

        @XmlElement(required = true)
        protected RefundTicket refundTicket;

        /**
         * Gets the value of the refundTicket property.
         * 
         * @return
         *     possible object is
         *     {@link RefundTicket }
         *     
         */
        public RefundTicket getRefundTicket() {
            return refundTicket;
        }

        /**
         * Sets the value of the refundTicket property.
         * 
         * @param value
         *     allowed object is
         *     {@link RefundTicket }
         *     
         */
        public void setRefundTicket(RefundTicket value) {
            this.refundTicket = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="childBillId" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="eOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="eOrderType" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="seatNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="passport" type="{http://www.w3.org/2001/XMLSchema}String"/>
         *         &lt;element name="passportName" type="{http://www.w3.org/2001/XMLSchema}String"/>
         *         &lt;element name="realName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "childBillId",
            "orderId",
            "eOrderNumber",
            "eOrderType",
            "seatNumber",
            "passport",
            "passportName",
            "realName"
        })
        public static class RefundTicket  implements Serializable {

            @XmlElement(required = true)
            protected String childBillId;
            @XmlElement(required = true)
            protected String orderId;
            @XmlElement(required = true)
            protected String eOrderNumber;
            @XmlElement(required = true)
            protected String eOrderType;
            @XmlElement(required = true)
            protected String seatNumber;
            protected String passport;
            protected String passportName;
            @XmlElement(required = true)
            protected String realName;

            /**
             * Gets the value of the childBillId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getChildBillId() {
                return childBillId;
            }

            /**
             * Sets the value of the childBillId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setChildBillId(String value) {
                this.childBillId = value;
            }

            /**
             * Gets the value of the orderId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOrderId() {
                return orderId;
            }

            /**
             * Sets the value of the orderId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOrderId(String value) {
                this.orderId = value;
            }

            /**
             * Gets the value of the eOrderNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEOrderNumber() {
                return eOrderNumber;
            }

            /**
             * Sets the value of the eOrderNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEOrderNumber(String value) {
                this.eOrderNumber = value;
            }

            /**
             * Gets the value of the eOrderType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEOrderType() {
                return eOrderType;
            }

            /**
             * Sets the value of the eOrderType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEOrderType(String value) {
                this.eOrderType = value;
            }

            /**
             * Gets the value of the seatNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeatNumber() {
                return seatNumber;
            }

            /**
             * Sets the value of the seatNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeatNumber(String value) {
                this.seatNumber = value;
            }

            /**
             * Gets the value of the passport property.
             * 
             */
            public String getPassport() {
                return passport;
            }

            /**
             * Sets the value of the passport property.
             * 
             */
            public void setPassport(String value) {
                this.passport = value;
            }

            /**
             * Gets the value of the passportName property.
             * 
             */
            public String getPassportName() {
                return passportName;
            }

            /**
             * Sets the value of the passportName property.
             * 
             */
            public void setPassportName(String value) {
                this.passportName = value;
            }

            /**
             * Gets the value of the realName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRealName() {
                return realName;
            }

            /**
             * Sets the value of the realName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRealName(String value) {
                this.realName = value;
            }

        }

    }

}
