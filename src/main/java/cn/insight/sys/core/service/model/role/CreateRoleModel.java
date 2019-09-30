package cn.insight.sys.core.service.model.role;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CreateRoleModel {
    @NotBlank(message = "NotBlank")
    private String name;
    @NotBlank(message = "NotBlank")
    private String title;
    private String description;
}
