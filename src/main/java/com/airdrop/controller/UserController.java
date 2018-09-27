package com.airdrop.controller;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.config.exception.ServiceException;
import com.airdrop.dto.PageDto;
import com.airdrop.dto.QueryDto;
import com.airdrop.dto._ResultDto;
import com.airdrop.entity.User;
import com.airdrop.service.UserService;
import com.airdrop.util.StringUtil;
import com.airdrop.util.TokenUtil;
import com.airdrop.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户管理接口
 * @date 2018/9/21 17:22
 */
@Api(description = "用户管理接口")
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @ApiOperation("用户查询")
    @GetMapping
    public QueryDto find(User user, PageDto dto) {
        QueryDto q = userService.find(user, PageRequest.of(dto.getPage(), dto.getRows(), dto.getSort()));
        return q;
    }

    @ApiOperation("用户删除")
    @DeleteMapping("/{id}")
    public _ResultDto delete(@PathVariable Integer id, HttpServletRequest request) {
        return userService.delete(id, request.getHeader(TokenUtil.TOKEN));
    }

    @ApiOperation("修改当前用户密码")
    @PostMapping("/setPass")
    public _ResultDto setPass(@RequestBody User user, HttpServletRequest request) {
        if (StringUtil.isEmpty(user.getPassword())) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), "密码不能为空");
        }
        String token = request.getHeader(TokenUtil.TOKEN);
        // 获得当前用户id
        UserVo currUser = TokenUtil.getUser(token);
        return userService.update(new User(currUser.getId(), user.getPassword()), token);
    }

    @ApiOperation("用户修改")
    @PutMapping("/{id}")
    public _ResultDto update(@PathVariable Integer id, @RequestBody User user, HttpServletRequest request) {
        user.setId(id);
        return userService.update(user, request.getHeader(TokenUtil.TOKEN));
    }

}
