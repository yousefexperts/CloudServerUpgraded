package cn.insight.sys.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import cn.insight.sys.common.dto.response.MenuInfoResponseDTO;

@Data
public class UserAuthInfoDO implements Serializable {
    private Set<String> permissions;
    private Set<String> roles;
    private List<MenuInfoResponseDTO> menus;
}
