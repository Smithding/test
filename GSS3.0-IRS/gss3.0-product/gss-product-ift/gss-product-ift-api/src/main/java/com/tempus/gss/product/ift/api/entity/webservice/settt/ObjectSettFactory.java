
package com.tempus.gss.product.ift.api.entity.webservice.settt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.tempus.gss.product.ift.api.entity.webservice.InairlinesVo;
import com.tempus.gss.product.ift.api.entity.webservice.InpayVo;
import com.tempus.gss.product.ift.api.entity.webservice.InsaleVo;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tempus.tempusservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectSettFactory {

    private final static QName _AddInallsaleResponse_QNAME = new QName("http://tempusservice.tempus.com", "addInallsaleResponse");
    private final static QName _AddInOthersResponse_QNAME = new QName("http://tempusservice.tempus.com", "addInOthersResponse");
    private final static QName _AddInsalepaymentResponse_QNAME = new QName("http://tempusservice.tempus.com", "addInsalepaymentResponse");
    private final static QName _AddInOthers_QNAME = new QName("http://tempusservice.tempus.com", "addInOthers");
    private final static QName _AddInallsale_QNAME = new QName("http://tempusservice.tempus.com", "addInallsale");
    private final static QName _AddInsalepayment_QNAME = new QName("http://tempusservice.tempus.com", "addInsalepayment");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tempus.tempusservice
     * 
     */
    public ObjectSettFactory() {
    }

    /**
     * Create an instance of {@link AddInOthers }
     * 
     */
    public AddInOthers createAddInOthers() {
        return new AddInOthers();
    }

    /**
     * Create an instance of {@link AddInsalepaymentResponse }
     * 
     */
    public AddInsalepaymentResponse createAddInsalepaymentResponse() {
        return new AddInsalepaymentResponse();
    }

    /**
     * Create an instance of {@link AddInallsaleResponse }
     * 
     */
    public AddInallsaleResponse createAddInallsaleResponse() {
        return new AddInallsaleResponse();
    }

    /**
     * Create an instance of {@link AddInOthersResponse }
     * 
     */
    public AddInOthersResponse createAddInOthersResponse() {
        return new AddInOthersResponse();
    }

    /**
     * Create an instance of {@link AddInallsale }
     * 
     */
    public AddInallsale createAddInallsale() {
        return new AddInallsale();
    }

    /**
     * Create an instance of {@link AddInsalepayment }
     * 
     */
    public AddInsalepayment createAddInsalepayment() {
        return new AddInsalepayment();
    }

    /**
     * Create an instance of {@link ReturnSettInfo }
     * 
     */
    public ReturnSettInfo createReturnInfo() {
        return new ReturnSettInfo();
    }

    /**
     * Create an instance of {@link InOthersVo }
     * 
     */
    public InOthersVo createInOthersVo() {
        return new InOthersVo();
    }

    /**
     * Create an instance of {@link InpayVo }
     * 
     */
    public InpayVo createInpayVo() {
        return new InpayVo();
    }

    /**
     * Create an instance of {@link InsaleVo }
     * 
     */
    public InsaleVo createInsaleVo() {
        return new InsaleVo();
    }

    /**
     * Create an instance of {@link InairlinesVo }
     * 
     */
    public InairlinesVo createInairlinesVo() {
        return new InairlinesVo();
    }

    /**
     * Create an instance of {@link InallsaleVo }
     * 
     */
    public InallsaleVo createInallsaleVo() {
        return new InallsaleVo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInallsaleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "addInallsaleResponse")
    public JAXBElement<AddInallsaleResponse> createAddInallsaleResponse(AddInallsaleResponse value) {
        return new JAXBElement<AddInallsaleResponse>(_AddInallsaleResponse_QNAME, AddInallsaleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInOthersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "addInOthersResponse")
    public JAXBElement<AddInOthersResponse> createAddInOthersResponse(AddInOthersResponse value) {
        return new JAXBElement<AddInOthersResponse>(_AddInOthersResponse_QNAME, AddInOthersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInsalepaymentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "addInsalepaymentResponse")
    public JAXBElement<AddInsalepaymentResponse> createAddInsalepaymentResponse(AddInsalepaymentResponse value) {
        return new JAXBElement<AddInsalepaymentResponse>(_AddInsalepaymentResponse_QNAME, AddInsalepaymentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInOthers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "addInOthers")
    public JAXBElement<AddInOthers> createAddInOthers(AddInOthers value) {
        return new JAXBElement<AddInOthers>(_AddInOthers_QNAME, AddInOthers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInallsale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "addInallsale")
    public JAXBElement<AddInallsale> createAddInallsale(AddInallsale value) {
        return new JAXBElement<AddInallsale>(_AddInallsale_QNAME, AddInallsale.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInsalepayment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "addInsalepayment")
    public JAXBElement<AddInsalepayment> createAddInsalepayment(AddInsalepayment value) {
        return new JAXBElement<AddInsalepayment>(_AddInsalepayment_QNAME, AddInsalepayment.class, null, value);
    }

}
