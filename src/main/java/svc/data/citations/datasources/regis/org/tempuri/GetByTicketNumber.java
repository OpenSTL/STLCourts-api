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
 *         &lt;element name="p_ticketNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="p_agcyId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="p_PageNum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="p_RowsPerPage" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
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
    "pTicketNum",
    "pAgcyId",
    "pPageNum",
    "pRowsPerPage",
    "pTotalRows"
})
@XmlRootElement(name = "GetByTicketNumber")
public class GetByTicketNumber {

    @XmlElementRef(name = "p_ticketNum", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> pTicketNum;
    @XmlElementRef(name = "p_agcyId", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> pAgcyId;
    @XmlElement(name = "p_PageNum")
    protected Integer pPageNum;
    @XmlElement(name = "p_RowsPerPage")
    protected Integer pRowsPerPage;
    @XmlElement(name = "p_TotalRows")
    protected Integer pTotalRows;

    /**
     * Gets the value of the pTicketNum property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPTicketNum() {
        return pTicketNum;
    }

    /**
     * Sets the value of the pTicketNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPTicketNum(JAXBElement<String> value) {
        this.pTicketNum = value;
    }

    /**
     * Gets the value of the pAgcyId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPAgcyId() {
        return pAgcyId;
    }

    /**
     * Sets the value of the pAgcyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPAgcyId(JAXBElement<String> value) {
        this.pAgcyId = value;
    }

    /**
     * Gets the value of the pPageNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPPageNum() {
        return pPageNum;
    }

    /**
     * Sets the value of the pPageNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPPageNum(Integer value) {
        this.pPageNum = value;
    }

    /**
     * Gets the value of the pRowsPerPage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPRowsPerPage() {
        return pRowsPerPage;
    }

    /**
     * Sets the value of the pRowsPerPage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPRowsPerPage(Integer value) {
        this.pRowsPerPage = value;
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
