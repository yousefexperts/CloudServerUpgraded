package com.experts.core.biller.statemachine.api.soapService;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://localhost:3070/payment", name = "PaymentRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class PaymentOrderRequest {

    @XmlElement
    private String TrxRefNo;

    @XmlElement
    private String CustIDCode;

    @XmlElement
    private String OTP;

    @XmlElement
    private String CustIDNo;

    @XmlElement
    private String OTPRefNo;

    @XmlElement
    private String Transactions;

    @XmlElement
    private String TrxInf;

    @XmlElement
    private String AcctInfo;

    @XmlElement
    private String BillingNo;

    @XmlElement
    private String BillNo;

    @XmlElement
    private String BillerCode;

    @XmlElement
    private String PmtType;

    @XmlElement
    private String InqRefNo;

    @XmlElement
    private String ValidationCode;

    @XmlElement
    private String DueAmt;

    @XmlElement
    private String PaidAmt;

    @XmlElement
    private String FeesAmt;

    @XmlElement
    private String Currency;

    @XmlElement
    private String AccessChannel;

    @XmlElement
    private String PaymentMethod;

    @XmlElement
    private String ServiceTypeDetails;

    @XmlElement
    private String ServiceType;

    @XmlElement
    private String PrepaidCat;

    @XmlElement
    private String StatementNarrative;

    @XmlElement
    private String ExtraInfo;


}
