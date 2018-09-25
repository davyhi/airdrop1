package com.airdrop.controller;

import com.airdrop.dto.PageDto;
import com.airdrop.dto.QueryDto;
import com.airdrop.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 日志控制层
 * @date 2018/09/25 11:10
 */
@Api(description = "日志接口API")
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @ApiOperation(value = "日志查询")
    @GetMapping
    public QueryDto find(PageDto pageDto) {
        return logService.find(PageRequest.of(pageDto.getPage(), pageDto.getRows(), pageDto.getSort()));
    }
}
