//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.06 at 07:26:16 PM CDT 
//


package svc.data.citations.datasources.regis.org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import svc.data.citations.datasources.regis.org.datacontract.schemas._2004._07.rejis_services_court.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetByTicketNumberResult" type="{http://schemas.datacontract.org/2004/07/REJIS.Services.Court.MuniCourt}ArrayOfCaseIndex" minOccurs="0"/&gt;
 *         &lt;element name="p_TotalRows" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getByTicketNumberResult",
    "pTotalRows"
})
@XmlRootElement(name = "GetByTicketNumberResponse")
public class GetByTicketNumberResponse {

    @XmlElementRef(name = "GetByTicketNumberResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfCaseIndex> getByTicketNumberResult;
    @XmlElement(name = "p_TotalRows")
    protected Integer pTotalRows;

    /**
     * Gets the value of the getByTicketNumberResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfCaseIndex }{@code >}
     *     
     */
    public JAXBElement<ArrayOfCaseIndex> getGetByTicketNumberResult() {
        return getByTicketNumberResult;
    }

    /**
     * Sets the value of the getByTicketNumberResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfCaseIndex }{@code >}
     *     
     */
    public void setGetByTicketNumberResult(JAXBElement<ArrayOfCaseIndex> value) {
        this.getByTicketNumberResult = value;
    }

    /**
     * Gets the value of the pTotalRows property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPTotalRows() {
        return pTotalRows;
    }

    /**
     * Sets the value of the pTotalRows property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPTotalRows(Integer value) {
        this.pTotalRows = value;
    }

}