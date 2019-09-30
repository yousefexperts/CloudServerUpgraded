package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpacePersist;
import com.gigaspaces.annotation.pojo.SpaceVersion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@Data
@Entity
@AllArgsConstructor
@XmlRootElement(name  = "billing_creation")
@XmlAccessorType
@SpaceClass
@NoArgsConstructor
public class BillingCreation extends AbstractEntity implements Serializable {

    @XmlElement(name  = "billCode" , required = true , nillable = false)
    private String billCode;

    @XmlElement(name  = "billName" , required = true , nillable = false)
    private String billName;

    @XmlElement(name  = "billerName" , required = true , nillable = false)
    private String billerName;

    @XmlElement(name  = "billerCode" , required = true , nillable = false)
    private String billerCode;

    @XmlElement(name  = "billerAddress" , required = true , nillable = false)
    private String billerAddress;

    @XmlElement(name  = "clientId" , required = true , nillable = false)
    private String clientId;

    @XmlElement(name  = "onServiceBy" , required = false , nillable = true)
    private String onServiceBy;

    @XmlElement(name  = "bankId" , required = true , nillable = false)
    private String bankId;

    @XmlElement(name  = "bankName" , required = true , nillable = false)
    private String bankName;

    @XmlElement(name  = "bankAddress" , required = true , nillable = false)
    private String bankAddress;

    @XmlElement(name  = "bankCode" , required = true , nillable = false)
    private String bankCode;



}
