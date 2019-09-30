package com.experts.core.biller.statemachine.api.soapService;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.experts-payment.com/payment", name = "PaymentOrderResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class PaymentOrderResponse {

    private String Result;
    private String PmtType;
    private String Severity;
    private String TrxRefNo;
    private String CustIDNo;
    private String idType;
    private String Transactions;
    private String TrxInf;
    private String ErrorCode;
    private String AcctInfo;
    private String BillingNo;
    private String BillNo;
    private String BillerCode;
    private String InqRefNo;
    private String ValidationCode;
    private String DueAmt;
    private String PaidAmt;
    private String ExtraInfo;

}
