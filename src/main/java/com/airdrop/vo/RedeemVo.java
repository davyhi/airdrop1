package com.airdrop.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 兑换码值对象
 * @date 2018/9/21 10:14
 */
@Data
public class RedeemVo implements Serializable {

    private Integer id;

    /**
     * 兑换码
     */
    private String redeemCode;

    /**
     * 空投值
     */
    private String airDrop;

    /**
     * 兑换码领取人
     */
    private Integer getUserId;

    /**
     * 兑换码领取时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp getStamp;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Integer userId;

    /**
     * 状态 0: 有效， 1：无效
     */
    private Integer dataStatus;

    /**
     * 使用状态： 0：未使用 1：已使用
     */
    private Integer useStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createStamp;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

}
