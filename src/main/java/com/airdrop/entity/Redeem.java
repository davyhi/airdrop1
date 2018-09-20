package com.airdrop.entity;

import com.airdrop.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
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
     * 备注
     */
    private String remark;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_stamp")
    private Timestamp createStamp;

    public Redeem() {

    }

    public Redeem(String code, int userid, String airDrop, String remark) {
        this.redeemCode = code;
        this.userId = userid;
        this.airDrop = airDrop;
        this.dataStatus = 0;
        this.useStatus = 0;
        this.remark = remark;
    }

    /**
     * 数据替换
     *
     * @param redeem
     */
    public void replace(Redeem redeem) {
        if (StringUtil.isNotEmpty(redeem.getRedeemCode())) {
            this.redeemCode = redeem.getRedeemCode();
        }
        if (StringUtil.isNotEmpty(redeem.getAirDrop())) {
            this.airDrop = redeem.getAirDrop();
        }
        if (redeem.getUseStatus() != null) {
            this.useStatus = redeem.getUseStatus();
        }
        if (StringUtil.isNotEmpty(redeem.getRemark())) {
            this.remark = redeem.getRemark();
        }
    }

}
