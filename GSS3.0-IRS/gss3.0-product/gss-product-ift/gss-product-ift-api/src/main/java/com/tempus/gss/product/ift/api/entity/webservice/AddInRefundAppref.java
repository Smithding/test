
package com.tempus.gss.product.ift.api.entity.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>addInRefundAppref complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="addInRefundAppref">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inrefundapprefvo" type="{http://tempusservice.tempus.com}inRefundApprefVo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addInRefundAppref", propOrder = {
    "inrefundapprefvo"
})
public class AddInRefundAppref {

    protected InRefundApprefVo inrefundapprefvo;

    /**
     * ��ȡinrefundapprefvo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link InRefundApprefVo }
     *     
     */
    public InRefundApprefVo getInrefundapprefvo() {
        return inrefundapprefvo;
    }

    /**
     * ����inrefundapprefvo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link InRefundApprefVo }
     *     
     */
    public void setInrefundapprefvo(InRefundApprefVo value) {
        this.inrefundapprefvo = value;
    }

}
