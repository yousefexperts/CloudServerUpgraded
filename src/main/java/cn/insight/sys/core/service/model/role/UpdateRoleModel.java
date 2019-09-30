package cn.insight.sys.core.service.model.role;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UpdateRoleModel extends CreateRoleModel {
    @Min(value = 1,message = "Min，Min")
    private int id;
}
