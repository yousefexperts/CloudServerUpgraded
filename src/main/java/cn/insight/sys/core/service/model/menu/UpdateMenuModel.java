package cn.insight.sys.core.service.model.menu;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UpdateMenuModel extends CreateMenuModel {
    @Min(value = 1,message = "IDMin")
    private int id;
}

