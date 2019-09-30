package cn.insight.sys.core.service.model.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CreateUserModel {
    @NotBlank(message = "NotBlank")
    @Length(max = 20,message = "Length")
    private String username;
    @Length(max = 100,message = "Length")
    @NotBlank(message = "NotBlank")
    private String password;
    @Length(max = 20,message = "Length")
    @NotBlank(message = "NotBlank")
    private String realname;
    private String avatar;
    private String phone;
    private String email;
    private int sex;
    private int locked;
}
