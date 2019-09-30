package com.experts.core.biller.statemachine.api.soapService;
import lombok.Data;

import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.sql.Timestamp;
import java.util.Date;
@XmlRootElement(name = "InquiryDetailsResponse")
public class InquiryDetailsResponse {

    private static final long serialVersionUID = 1111648120;

    @XmlElement(name  = "billCode" , required = false , nillable = false)
    private String billCode;

    @XmlElement(name  = "billName" , required = false , nillable = false)
    private String billName;

    @XmlElement(name  = "billerName" , required = false , nillable = false)
    private String billerName;

    @XmlElement(name  = "billerCode" , required = false , nillable = false)
    private String billerCode;

    @XmlElement(name  = "billerAddress" , required = false , nillable = false)
    private String billerAddress;

    @XmlElement(name  = "clientId" , required = false , nillable = false)
    private String clientId;

    @XmlElement(name  = "onServiceBy" , required = false , nillable = true)
    private String onServiceBy;

    @XmlElement(name  = "bankId" , required = false , nillable = false)
    private String bankId;

    @XmlElement(name  = "bankName" , required = false , nillable = false)
    private String bankName;

    @XmlElement(name  = "bankAddress" , required = false , nillable = false)
    private String bankAddress;

    @XmlElement(name  = "bankCode" , required = false , nillable = false)
    private String bankCode;

}
