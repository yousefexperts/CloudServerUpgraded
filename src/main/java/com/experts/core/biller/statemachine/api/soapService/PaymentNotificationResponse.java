package com.experts.core.biller.statemachine.api.soapService;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.experts.ps/ws/notifaction", name = "PaymentNotificationResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class PaymentNotificationResponse {

   private String Transactions;
   private String TrxInf;
   private String JOEBPPSTrx;
   private String STMTDate;
   private String Result;
   private String idType;
   private String ErrorCode;
   private String Severity;

}
