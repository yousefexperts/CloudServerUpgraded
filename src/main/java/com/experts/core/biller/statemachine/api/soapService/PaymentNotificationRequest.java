package com.experts.core.biller.statemachine.api.soapService;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://localhost:3070/notification", name = "PaymentNotificationRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class PaymentNotificationRequest {

    @XmlElement
    private String transactions;

    @XmlElement
    private String trxInf;

    @XmlElement
    private String acctInfo;

    @XmlElement
    private String billNo;

    @XmlElement
    private String billingNo;

    @XmlElement
    private String billerCode;

    @XmlElement
    private String JOEBPPSTrx;

    @XmlElement
    private String BankTrxID;

    @XmlElement
    private String billStatus;

    @XmlElement
    private String PmtSrc;

    @XmlElement
    private String BankCode;

    @XmlElement
    private String PmtStatus;

    @XmlElement
    private String DueAmt;

    @XmlElement
    private String PaidAmt;

    @XmlElement
    private String FeesAmt;

    @XmlElement
    private String FeesOnBiller;

    @XmlElement
    private String ProcessDate;

    @XmlElement
    private String STMTDate;

    @XmlElement
    private String AccessChannel;

    @XmlElement
    private String PaymentMethod;

    @XmlElement
    private String ServiceType;

    @XmlElement
    private String SubPmts;

    @XmlElement
    private String subPmt;

    @XmlElement
    private String Amount;

    @XmlElement
    private String SetBnkCode;

    @XmlElement
    private String PayerInfo;

    @XmlElement
    private String IdType;

    @XmlElement
    private String Id;

    @XmlElement
    private String Nation;

    @XmlElement
    private String Name;

    @XmlElement
    private String Phone;

    @XmlElement
    private String Address;

    @XmlElement
    private String Email;

    @XmlElement
    private String JOEBPPSNo;

}
