package cn.insight.sys.core.service.model.organization;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UpdateOrganizationModel extends CreateOrganizationModel {
    @Min(value = 1,message = "IDMin")
    private int id;
}
