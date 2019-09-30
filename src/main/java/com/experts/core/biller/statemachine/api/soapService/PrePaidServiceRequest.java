package com.experts.core.biller.statemachine.api.soapService;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://localhost:3070/prepaid", name = "PrePaidRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class PrePaidServiceRequest {
    @XmlElement
   private String TrxInf;

    @XmlElement
   private String AcctInfo;

    @XmlElement
   private String BillingNo;

    @XmlElement
   private String BillerCode;

    @XmlElement
   private String BankTrxID;

    @XmlElement
   private String ValidationCode;

    @XmlElement
   private String PmtStatus;

    @XmlElement
   private String DueAmt;

    @XmlElement
   private String PaidAmt;

    @XmlElement
   private String ProcessDate;

    @XmlElement
   private String Currency;

    @XmlElement
   private String STMTDate;

    @XmlElement
   private String JOEBPPSTrx;

    @XmlElement
   private String Result;

    @XmlElement
   private String ErrorCode;

    @XmlElement
   private String ErrorDesc;

    @XmlElement
   private String Severity;

}
