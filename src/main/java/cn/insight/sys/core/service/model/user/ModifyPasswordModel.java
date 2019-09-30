package cn.insight.sys.core.service.model.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

@Data
public class ModifyPasswordModel {

    @Min(value = 1,message = "Min,Min")
    private int id;
    @Length(max = 100,message = "Length")
    @NotBlank(message = "NotBlank")
    private String password;

}
