package com.airdrop.controller.code;

import com.airdrop.dto.*;
import com.airdrop.entity.Redeem;
import com.airdrop.service.RedeemService;
import com.airdrop.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 兑换码Api
 * @date 2018/9/18 17:33
 */
@Api(description = "兑换码对外接口")
@RestController
@RequestMapping("/redeem")
public class RedeemCodeApi {

    @Autowired
    private RedeemService redeemService;

    @ApiOperation("生成兑换码")
    @PostMapping
    public UpdateDto createCode(@RequestBody RedeemDto redeemDto, HttpServletRequest request) {
        return redeemService.createRedeem(redeemDto, request.getHeader(TokenUtil.TOKEN));
    }

    @ApiOperation("查询兑换码")
    @GetMapping
    public QueryDto find(Redeem redeem, PageDto pageDto) {
        return redeemService.find(redeem, PageRequest.of(pageDto.getPage(), pageDto.getRows(), pageDto.getSort()));
    }

    @ApiOperation("校验兑换码是否可使用")
    @ApiImplicitParam(name = "redeemCode", dataType = "string", required = true)
    @PostMapping("/check")
    public _ResultDto check(@RequestBody Redeem redeem) {
        return redeemService.check(redeem);
    }

    @ApiOperation("删除数据")
    @DeleteMapping("/{id}")
    public _ResultDto delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        return redeemService.delete(id, request.getHeader(TokenUtil.TOKEN));
    }

    @ApiOperation("修改")
    @PutMapping("/{id}")
    public _ResultDto update(@PathVariable("id") Integer id, @RequestBody Redeem redeem, HttpServletRequest request) {
        redeem.setId(id);
        return redeemService.update(redeem, request.getHeader(TokenUtil.TOKEN));
    }

    @ApiOperation("后台系统首页需要的空投数据")
    @GetMapping("/home")
    public UpdateDto home() {

        return new UpdateDto();
    }


}
