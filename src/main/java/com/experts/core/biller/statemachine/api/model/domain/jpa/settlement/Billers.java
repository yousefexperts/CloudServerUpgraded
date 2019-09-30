package com.experts.core.biller.statemachine.api.model.domain.jpa.settlement;

import com.experts.core.biller.statemachine.api.domain.enumsapi.ProtocolType;
import com.experts.core.biller.statemachine.api.model.domain.jpa.AbstractEntity;
import com.gigaspaces.annotation.pojo.SpaceClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Biller_Api_Integrations")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@SpaceClass
public class Billers extends AbstractEntity implements Serializable {

    @Column(name  = "biller_name" , nullable = false)

    private String billerName;

    @Column(name  = "billpull_url" , nullable = false)
    private String billPull;

    @Column(name  = "payment_notification_url" , nullable = false)
    private String paymentNotification;

    @Column(name  = "phone_1" , nullable = false)
    private String phone_1;

    @Column(name  = "phone_2" , nullable = true)
    private String phone_2;

    @Column(name  = "email" , nullable = false)
    private String email;

    @Column(name  = "pox" , nullable = true)
    private String pox;

    @Column(name  = "street_address" , nullable = false)
    private String streetAddress;

    @Column(name  = "building_no" , nullable = false)
    private String buildingNo;

    @Column(name  = "floorNo" , nullable =  true)
    private String floorNo;

    @Column(name  = "protocol" , nullable = false)
    private ProtocolType type;

    @Column(name  = "pk_content" , nullable = false)
    private String pkContent;

    @Column(name  = "pub_content" , nullable = false)
    private String pubContent;

    @Column(name  = "cert_content" , nullable = false)
    private String certContent;

    @Column(name  = "folder_name" , nullable = false)
    private String folderName;

    @Column(name  = "file_name" , nullable = false)
    private String fileName;

    @Column(name  = "serial_number" , nullable = false)
    private String serialNumber;

    @Column(name  = "alias_name" , nullable = false)
    private String aliasName;



}
