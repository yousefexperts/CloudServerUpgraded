package cn.insight.sys.core.service.model.system;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UpdateSystemModel extends CreateSystemModel {
    @Min(value = 1,message = "Min,Min")
    private int id;
}
