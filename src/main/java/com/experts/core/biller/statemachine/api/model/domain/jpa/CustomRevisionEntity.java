package com.experts.core.biller.statemachine.api.model.domain.jpa;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "CustomRevisionEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomRevisionEntity extends AbstractEntity {
    private String login;

    @ManyToOne(fetch = FetchType.LAZY , cascade =  CascadeType.ALL )
    private CustomTrackingEntity customTrackingEntity;


}
