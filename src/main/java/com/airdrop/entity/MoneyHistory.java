package com.airdrop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 余额历史纪录
 * @date 2018/9/11 11:05
 */
@Data
@Entity(name = "t_money_history")
public class MoneyHistory implements Serializable {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 余额
     */
    private Integer money;

    /**
     * 0：加，1：减
     */
    @Column(name = "plus_or_minus")
    private Integer plusOrMinus;

    /**
     * 创建时间
     */
    @Column(name = "create_stamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createStamp;

    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Integer userId;

    public MoneyHistory() {

    }

    public MoneyHistory(int userId) {
        this.userId = userId;
    }

    public MoneyHistory(int money, int plusOrMinus, int userId) {
        this.money = money;
        this.plusOrMinus = plusOrMinus;
        this.userId = userId;
    }

}
