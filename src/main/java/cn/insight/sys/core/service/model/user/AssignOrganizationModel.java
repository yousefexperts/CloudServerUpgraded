package cn.insight.sys.core.service.model.user;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class AssignOrganizationModel {
    @Min(value = 1,message = "Min")
    private int userId;
    private int[] userOrganization;

}
