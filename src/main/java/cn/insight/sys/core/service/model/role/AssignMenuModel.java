package cn.insight.sys.core.service.model.role;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class AssignMenuModel {
    @Min(value = 1,message = "Min")
    private int roleId;
    private int systemId;
    private int[] roleMenu;

}
