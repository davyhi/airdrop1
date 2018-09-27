package com.airdrop.controller;

import com.airdrop.dto.UpdateDto;
import com.airdrop.service.AirDropService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 空投值控制层
 * @date 2018/9/26 16:52
 */
@Api(description = "空投接口Api")
@RestController
@RequestMapping("/air")
public class AirDropController {

    @Autowired
    private AirDropService airDropService;

    @ApiOperation("查询空投信息")
    @GetMapping
    public UpdateDto findAir() {
        return airDropService.findAirDrop();
    }

}
