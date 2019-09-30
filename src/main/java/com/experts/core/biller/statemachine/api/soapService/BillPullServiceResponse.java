package com.experts.core.biller.statemachine.api.soapService;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.experts.ps/ws/billpull", name = "BillPullResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class BillPullServiceResponse {

    @XmlElement
    private String resCount;

    @XmlElement
    private String billRec;

    @XmlElement
    private String billingNo;

    @XmlElement
    private String billNo;

    @XmlElement
    private String result;

    @XmlElement
    private String errorCode;

    @XmlElement
    private String errorDesc;

    @XmlElement
    private String serverity;

    @XmlElement
    private String accInfo;

    @XmlElement
    private String billStatus;

    @XmlElement
    private String dueAmount;

    @XmlElement
    private String currency;

    @XmlElement
    private String issueDate;

    @XmlElement
    private String openDate;

    @XmlElement
    private String dueDate;

    @XmlElement
    private String expiryDate;

    @XmlElement
    private String closeDate;

    @XmlElement
    private String serviceType;

    @XmlElement
    private String billType;

    @XmlElement
    private String pmtConst;

    @XmlElement
    private String allowPart;

    @XmlElement
    private String lower;

    @XmlElement
    private String upper;

    @XmlElement
    private String subPmts;

    @XmlElement
    private String subPmt;

    @XmlElement
    private String amount;

    @XmlElement
    private String bankCode;

    @XmlElement
    private String accNo;

}
