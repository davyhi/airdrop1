package com.airdrop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 接收兑换码相关数据
 * @date 2018/9/20 11:09
 */
@Data
public class RedeemDto extends _RequestDto {

    @ApiModelProperty(value = "空投值", dataType = "string")
    private Integer airDrop;

    @ApiModelProperty(value = "备注", dataType = "string")
    private String remark;

    @ApiModelProperty(value = "密钥长度", dataType = "int")
    private Integer length = 16;

    @ApiModelProperty(value = "密钥数量", dataType = "int")
    private Integer count = 1;
}
