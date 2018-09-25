package com.airdrop.vo;

import com.airdrop.entity.Privileges;
import com.airdrop.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户值对象
 * @date 2018/9/11 11:34
 */
@Data
public class UserVo implements Serializable {

    private Integer id;

    private String name;

    private String phone;

    private String email;

    private String token;

    private String eos;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lastStamp;

    private Integer money;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createStamp;

    private List<Privileges> pris;

    private List<Role> roles;

    public UserVo(Integer id, String name, String email, String phone, List<Privileges> privileges, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pris = privileges;
        this.roles = roles;
    }

    public UserVo() {

    }

}
