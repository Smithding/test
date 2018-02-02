
package com.tempus.gss.product.ift.api.entity.webservice.settt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.tempus.gss.product.ift.api.entity.webservice.InpayVo;


/**
 * <p>addInsalepayment complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="addInsalepayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inpayvo" type="{http://tempusservice.tempus.com}inpayVo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addInsalepayment", propOrder = {
    "inpayvo"
})
public class AddInsalepayment {

    protected InpayVo inpayvo;

    /**
     * ��ȡinpayvo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link InpayVo }
     *     
     */
    public InpayVo getInpayvo() {
        return inpayvo;
    }

    /**
     * ����inpayvo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link InpayVo }
     *     
     */
    public void setInpayvo(InpayVo value) {
        this.inpayvo = value;
    }

}
