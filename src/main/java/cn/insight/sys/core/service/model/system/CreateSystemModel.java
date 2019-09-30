package cn.insight.sys.core.service.model.system;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CreateSystemModel {

    @NotBlank(message = "NotBlank.")
    private String basepath;
    private Byte status;
    @NotBlank(message = "NotBlank.")
    private String name;
    @NotBlank(message = "NotBlank.")
    private String title;
    private String description;
    private long orders;
}
