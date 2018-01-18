
package com.tempus.gss.product.ift.api.entity.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>addInsaleInerrorment complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="addInsaleInerrorment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inerrordetailvo" type="{http://tempusservice.tempus.com}inerrordetailVo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addInsaleInerrorment", propOrder = {
    "inerrordetailvo"
})
public class AddInsaleInerrorment {

    protected InerrordetailVo inerrordetailvo;

    /**
     * ��ȡinerrordetailvo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link InerrordetailVo }
     *     
     */
    public InerrordetailVo getInerrordetailvo() {
        return inerrordetailvo;
    }

    /**
     * ����inerrordetailvo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link InerrordetailVo }
     *     
     */
    public void setInerrordetailvo(InerrordetailVo value) {
        this.inerrordetailvo = value;
    }

}
