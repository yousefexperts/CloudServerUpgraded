package com.experts.core.biller.statemachine.api.soapService;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.experts-prepaid.com/prepaid", name = "PrePaidResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class PrePaidServiceResponse {

    private String TrxInf;
    private String AcctInfo;
    private String BillingNo;
    private String BillerCode;
    private String BankTrxID;
    private String ValidationCode;
    private String PmtStatus;
    private String DueAmt;
    private String PaidAmt;
    private String ProcessDate;
    private String Currency;
    private String STMTDate;
    private String JOEBPPSTrx;
    private String Result;
    private String ErrorCode;
    private String ErrorDesc;
    private String Severity;

}
