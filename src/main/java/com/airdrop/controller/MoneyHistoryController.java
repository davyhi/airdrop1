package com.airdrop.controller;

import com.airdrop.dto.PageDto;
import com.airdrop.dto.QueryDto;
import com.airdrop.service.MoneyHistoryService;
import com.airdrop.util.TokenUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户余额历史
 * @date 2018/9/26 18:51
 */
@RestController
@RequestMapping("/moneyHis")
public class MoneyHistoryController {

    @Autowired
    private MoneyHistoryService moneyHistoryService;


    @ApiOperation("用户余额历史查询")
    @GetMapping("/{id}")
    public QueryDto find(@PathVariable Integer id, PageDto pageDto) {
        return moneyHistoryService.find(id, PageRequest.of(pageDto.getPage(), pageDto.getRows(), pageDto.getSort()));
    }

    @ApiOperation("查询当前用户余额历史")
    @GetMapping("/findCurrent")
    public QueryDto find(HttpServletRequest request, PageDto pageDto) {
        return moneyHistoryService.find(PageRequest.of(pageDto.getPage(), pageDto.getRows(), pageDto.getSort()), request.getHeader(TokenUtil.TOKEN));
    }

}
