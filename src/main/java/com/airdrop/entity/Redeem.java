package com.airdrop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 兑换码实体类
 * @date 2018/9/18 18:16
 */
@Entity(name = "t_redeem")
@Data
public class Redeem implements Serializable {

    /**
     * 编号
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 兑换码
     */
    @Column(name = "redeem_code")
    private String redeemCode;

    /**
     * 空投值
     */
    @Column(name = "air_drop")
    private String airDrop;

    /**
     * 兑换码领取人
     */
    @Column(name = "get_user_id")
    private Integer getUserId;

    /**
     * 兑换码领取时间
     */
    @Column(name = "get_stamp")
    private Timestamp getStamp;

    /**
     * 创建人
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 状态 0: 有效， 1：无效
     */
    @Column(name = "`data_status`")
    private Integer dataStatus;

    /**
     * 使用状态： 0：未使用 1：已使用
     */
    @Column(name = "use_status")
    private Integer useStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_stamp")
    private Timestamp createStamp;

    public Redeem() {

    }

    public Redeem(String code, int userid, String airDrop) {
        this.redeemCode = code;
        this.userId = userid;
        this.airDrop = airDrop;
        this.dataStatus = 0;
        this.useStatus = 0;
    }

}
