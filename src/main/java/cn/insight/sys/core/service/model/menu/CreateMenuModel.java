package cn.insight.sys.core.service.model.menu;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CreateMenuModel {
    private Integer systemId;
    private Integer pid;
    @NotBlank(message = "NotBlank")
    private String name;
    @NotBlank(message = "NotBlank")
    private String url;
    private String target;
    private String icon;
    private long orders;
}
