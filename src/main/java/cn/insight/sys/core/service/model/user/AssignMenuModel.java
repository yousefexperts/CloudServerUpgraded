package cn.insight.sys.core.service.model.user;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class AssignMenuModel {
    @Min(value = 1,message = "Min")
    private int userId;
    private int systemId;
    private int[] userMenu;

}
