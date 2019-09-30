package cn.insight.sys.core.service.model.organization;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CreateOrganizationModel {
    private Integer pid;
    @NotBlank(message = "NotBlank")
    private String name;
    private String description;
}
