package com.experts.core.biller.statemachine.api.cas.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StorageLogin {

    static final Logger LOG = LoggerFactory.getLogger(StorageLogin.class);

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private JdbcTemplate jdbcTemplate;

    private final static String admin_user = "sso-admin@cheyipai.com";
    private final static String add_user_by = "1";


    public String adInfo2DB(Map<String, Object> attributeMap) {
        String userId = null;
        String login_name = attributeMap.get("userName").toString();

        if (login_name.toLowerCase().equals(admin_user)) {
            return "1";
        }
        String dn = attributeMap.get("dn").toString();
        String officeId = office2DB(dn);
        if (isNotBlank(officeId)) {
            userId = user2DB(attributeMap, officeId); //user2db
        }
        return userId;
    }


    private String user2DB(Map<String, Object> attributeMap, String officeId) {
        String login_name = attributeMap.get("userName").toString();
        String userId = findUserByLogin(login_name);
        if (isNotBlank(userId)) {
            return userId;
        }
        String mail = attributeMap.get("mail").toString();
        String name = attributeMap.get("displayName").toString();
        String sql = "INSERT INTO SYS_USER " +
                "(id,company_id,office_id,login_name,name,email," +
                "login_ip,login_date,create_by,create_date,update_by,update_date,del_flag)" +
                " VALUES (?,?,?,?,?,?,?,now(),?,now(),?,now(),0)";
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        String company_id = "1";
        String ip = getRemoteAddr();
        try {
            jdbcTemplate.update(sql, new Object[]{id, company_id, officeId,
                    login_name, name, mail, ip, add_user_by, add_user_by});
            addDefaultRole(id);    //user2role
        } catch (Exception e) {
            LOG.error("user2DB error : {} , SQL : {}", e.getMessage(), sql, e);
        }
        return id;
    }

    private void addDefaultRole(String userId) {
        String sql = "INSERT INTO SYS_USER_ROLE VALUES(?,?)";
        try {
            jdbcTemplate.update(sql, new Object[]{userId, 2});
        } catch (Exception e) {
            LOG.error("addDefaultRole error : {} , SQL : {}", e.getMessage(), sql, e);
        }
    }

    private String findUserByLogin(String login) {
        String sql = "SELECT id FROM SYS_USER WHERE login_name = ?";
        return queryId(sql, login);
    }

    private String findOfficeByName(String name) {
        String sql = "SELECT id FROM SYS_OFFICE WHERE name = ?";
        return queryId(sql, name);
    }

    private String findOfficeNextCode() {
        String next = null;
        String sql = "SELECT max(code) FROM SYS_OFFICE WHERE grade = 1 and type = 2";
        try {
            Object o = jdbcTemplate.queryForObject(sql, String.class);
            if (o == null) {
                next = "100000001";
            } else {
                Integer code = Integer.parseInt(o.toString()) + 1;
                next = code.toString();
            }
        } catch (Exception e) {
            LOG.error(" error : {}", e.getMessage(), e);
        }
        return next;
    }


    private String office2DB(String dn) {
        String name;
        if (isNotBlank(dn)) {
            String[] dn_array = dn.split(",");
            name = dn.split(",")[dn_array.length - 3].split("=")[1];
        } else {
            return null;
        }
        String officeId = findOfficeByName(name);
        if (isNotBlank(officeId)) {
            return officeId;
        }
        String sql = "INSERT INTO SYS_OFFICE " +
                "(id,parent_id,parent_ids,name,sort," +
                "area_id,code,type,grade,USEABLE," +
                "create_by,create_date,update_by,update_date,del_flag) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),0)";
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        String code = findOfficeNextCode();
        try {
            jdbcTemplate.update(sql, new Object[]{id, "1", "0,1,",
                    name, 30, 2, code, 2, 1, 1, add_user_by, add_user_by});
        } catch (Exception e) {
            LOG.error("office2DB error : {} , SQL : {}", e.getMessage(), sql, e);
        }
        return id;
    }


    public static String getRemoteAddr() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }


    String queryId(String sql, String str) {
        try {
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[]{str});
            if (list != null && !list.isEmpty()) {
                Map<String, Object> map = list.get(0);
                if (map != null && map.get("id") != null) {
                    return map.get("id").toString();
                }
            }
        } catch (Exception e) {
            LOG.error("queryId error : {} , SQL : {} , STR : {}", e.getMessage(), sql, str, e);
        }
        return null;
    }

    static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }


    static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
