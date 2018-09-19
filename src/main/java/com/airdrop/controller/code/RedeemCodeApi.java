package com.airdrop.controller.code;

import com.airdrop.dto.PageDto;
import com.airdrop.dto.QueryDto;
import com.airdrop.dto.UpdateDto;
import com.airdrop.dto._ResultDto;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "length", dataType = "int", defaultValue = "16", required = true, paramType = "query"),
            @ApiImplicitParam(name = "count", dataType = "int", defaultValue = "1", required = true, paramType = "query")
    })
    @PostMapping
    public UpdateDto createCode(@RequestParam("airDrop") String airDrop, @RequestParam(name = "length", defaultValue = "16", required = false) Integer length, @RequestParam(name = "count", defaultValue = "1") Integer count, HttpServletRequest request) {
        return redeemService.createRedeem(length, count, airDrop, request.getHeader(TokenUtil.TOKEN));
    }

    @ApiOperation("查询兑换码")
    @GetMapping
    public QueryDto find(Redeem redeem, PageDto pageDto) {
        return redeemService.find(redeem, PageRequest.of(pageDto.getPage(), pageDto.getRows(), pageDto.getSort()));
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


}
