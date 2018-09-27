package com.airdrop.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 空投首页管理值
 * @date 2018/9/26 16:41
 */
@Data
public class AirDropVo implements Serializable {

    private int status;

    private int count;

    private int remainingNum;

    private int loosenNum;

}
