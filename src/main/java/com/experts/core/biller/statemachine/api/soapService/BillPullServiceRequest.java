package com.experts.core.biller.statemachine.api.soapService;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(namespace = "http://localhost:3070/ws/billpull", name = "BillPullRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillPullServiceRequest", namespace = "http://localhost:3070/ws/billpull", propOrder = {"acctInfo","billingNo","billNo","serviceType","payerInfo","idType","id","nation","name","phone","address","email","joebppsno"})
@Data
public class BillPullServiceRequest {

    @XmlElement
    private String acctInfo;

    @XmlElement
    private String billingNo;

    @XmlElement
    private String billNo;

    @XmlElement
    private String serviceType;

    @XmlElement
    private String payerInfo;

    @XmlElement
    private String idType;

    @XmlElement
    private String id;

    @XmlElement
    private String nation;

    @XmlElement
    private String name;

    @XmlElement
    private String phone;

    @XmlElement
    private String address;

    @XmlElement
    private String email;

    @XmlElement
    private String joebppsno;


}
