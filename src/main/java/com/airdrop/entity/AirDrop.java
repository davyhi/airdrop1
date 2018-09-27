package com.airdrop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 空投实体类
 * @date 2018/9/26 16:26
 */
@Entity(name = "t_airdrop")
@Data
public class AirDrop implements Serializable {

    /**
     * 编号
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 剩余可投放空投
     */
    @Column(name = "remaining_num")
    private String remainingNum;

    /**
     * 已投放空投
     */
    @Column(name = "loosen_num")
    private String loosenNum;

}
