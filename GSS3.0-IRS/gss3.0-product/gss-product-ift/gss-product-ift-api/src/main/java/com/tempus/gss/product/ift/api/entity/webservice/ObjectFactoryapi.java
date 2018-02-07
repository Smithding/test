
package com.tempus.gss.product.ift.api.entity.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tempus.gss.product.ift.api.entity.webservice package. 
 * <p>An ObjectFactoryapi allows you to programatically
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
public class ObjectFactoryapi {

    private final static QName _AddInsaleInerrormentResponse_QNAME = new QName("http://tempusservice.tempus.com", "addInsaleInerrormentResponse");
    private final static QName _AddInsaleInerrorment_QNAME = new QName("http://tempusservice.tempus.com", "addInsaleInerrorment");
    private final static QName _DeleteInRefundApprefResponse_QNAME = new QName("http://tempusservice.tempus.com", "deleteInRefundApprefResponse");
    private final static QName _DeleteInRefundAppref_QNAME = new QName("http://tempusservice.tempus.com", "deleteInRefundAppref");
    private final static QName _AddInRefundAppref_QNAME = new QName("http://tempusservice.tempus.com", "addInRefundAppref");
    private final static QName _AddInRefundApprefResponse_QNAME = new QName("http://tempusservice.tempus.com", "addInRefundApprefResponse");

    /**
     * Create a new ObjectFactoryapi that can be used to create new instances of schema derived classes for package: com.tempus.gss.product.ift.api.entity.webservice
     * 
     */
    public ObjectFactoryapi() {
    }

    /**
     * Create an instance of {@link DeleteInRefundAppref }
     * 
     */
    public DeleteInRefundAppref createDeleteInRefundAppref() {
        return new DeleteInRefundAppref();
    }

    /**
     * Create an instance of {@link AddInsaleInerrorment }
     * 
     */
    public AddInsaleInerrorment createAddInsaleInerrorment() {
        return new AddInsaleInerrorment();
    }

    /**
     * Create an instance of {@link DeleteInRefundApprefResponse }
     * 
     */
    public DeleteInRefundApprefResponse createDeleteInRefundApprefResponse() {
        return new DeleteInRefundApprefResponse();
    }

    /**
     * Create an instance of {@link AddInsaleInerrormentResponse }
     * 
     */
    public AddInsaleInerrormentResponse createAddInsaleInerrormentResponse() {
        return new AddInsaleInerrormentResponse();
    }

    /**
     * Create an instance of {@link AddInRefundApprefResponse }
     * 
     */
    public AddInRefundApprefResponse createAddInRefundApprefResponse() {
        return new AddInRefundApprefResponse();
    }

    /**
     * Create an instance of {@link AddInRefundAppref }
     * 
     */
    public AddInRefundAppref createAddInRefundAppref() {
        return new AddInRefundAppref();
    }

    /**
     * Create an instance of {@link ReturnInfo1 }
     * 
     */
    public ReturnInfo1 createReturnInfo() {
        return new ReturnInfo1();
    }

    /**
     * Create an instance of {@link InRefundApprefVo }
     * 
     */
    public InRefundApprefVo createInRefundApprefVo() {
        return new InRefundApprefVo();
    }

    /**
     * Create an instance of {@link InerrordetailVo }
     * 
     */
    public InerrordetailVo createInerrordetailVo() {
        return new InerrordetailVo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInsaleInerrormentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "addInsaleInerrormentResponse")
    public JAXBElement<AddInsaleInerrormentResponse> createAddInsaleInerrormentResponse(AddInsaleInerrormentResponse value) {
        return new JAXBElement<AddInsaleInerrormentResponse>(_AddInsaleInerrormentResponse_QNAME, AddInsaleInerrormentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInsaleInerrorment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "addInsaleInerrorment")
    public JAXBElement<AddInsaleInerrorment> createAddInsaleInerrorment(AddInsaleInerrorment value) {
        return new JAXBElement<AddInsaleInerrorment>(_AddInsaleInerrorment_QNAME, AddInsaleInerrorment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteInRefundApprefResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "deleteInRefundApprefResponse")
    public JAXBElement<DeleteInRefundApprefResponse> createDeleteInRefundApprefResponse(DeleteInRefundApprefResponse value) {
        return new JAXBElement<DeleteInRefundApprefResponse>(_DeleteInRefundApprefResponse_QNAME, DeleteInRefundApprefResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteInRefundAppref }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "deleteInRefundAppref")
    public JAXBElement<DeleteInRefundAppref> createDeleteInRefundAppref(DeleteInRefundAppref value) {
        return new JAXBElement<DeleteInRefundAppref>(_DeleteInRefundAppref_QNAME, DeleteInRefundAppref.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInRefundAppref }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "addInRefundAppref")
    public JAXBElement<AddInRefundAppref> createAddInRefundAppref(AddInRefundAppref value) {
        return new JAXBElement<AddInRefundAppref>(_AddInRefundAppref_QNAME, AddInRefundAppref.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInRefundApprefResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempusservice.tempus.com", name = "addInRefundApprefResponse")
    public JAXBElement<AddInRefundApprefResponse> createAddInRefundApprefResponse(AddInRefundApprefResponse value) {
        return new JAXBElement<AddInRefundApprefResponse>(_AddInRefundApprefResponse_QNAME, AddInRefundApprefResponse.class, null, value);
    }

}
