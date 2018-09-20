package com.airdrop.controller;

import com.airdrop.dto.PageDto;
import com.airdrop.dto.QueryDto;
import com.airdrop.entity.Redeem;
import com.airdrop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zengdavy
 * @date: 2018/9/20
 * @description:
 */
//public class TotalMoneyController {
//
//    @Autowired
//    UserService userService;
//    //查询用户历史总余额
//    @GetMapping("/checkTotalMoney")
//    public QueryDto checkTotalMoney( ){
//        return userService.checkTotalMoney();
//    }
//
//}
