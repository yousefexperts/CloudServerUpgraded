package cn.insight.sys.core.service.model.common;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class AssignPermissionModel {
    @Min(value = 1,message = "IDSmall")
    private int id;
    private int systemId;
    private int[] permission;

}
