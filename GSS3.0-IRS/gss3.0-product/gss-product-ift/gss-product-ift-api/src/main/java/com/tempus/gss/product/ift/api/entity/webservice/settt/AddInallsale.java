
package com.tempus.gss.product.ift.api.entity.webservice.settt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>addInallsale complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="addInallsale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inallsalevo" type="{http://tempusservice.tempus.com}inallsaleVo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addInallsale", propOrder = {
    "inallsalevo"
})
public class AddInallsale {

    protected InallsaleVo inallsalevo;

    /**
     * ��ȡinallsalevo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link InallsaleVo }
     *     
     */
    public InallsaleVo getInallsalevo() {
        return inallsalevo;
    }

    /**
     * ����inallsalevo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link InallsaleVo }
     *     
     */
    public void setInallsalevo(InallsaleVo value) {
        this.inallsalevo = value;
    }

}
