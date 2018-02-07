
package com.tempus.gss.product.ift.api.entity.webservice.settt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>addInOthers complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="addInOthers">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inothersvo" type="{http://tempusservice.tempus.com}inOthersVo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addInOthers", propOrder = {
    "inothersvo"
})
public class AddInOthers {

    protected InOthersVo inothersvo;

    /**
     * ��ȡinothersvo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link InOthersVo }
     *     
     */
    public InOthersVo getInothersvo() {
        return inothersvo;
    }

    /**
     * ����inothersvo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link InOthersVo }
     *     
     */
    public void setInothersvo(InOthersVo value) {
        this.inothersvo = value;
    }

}
