package com.airdrop.controller;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.config.exception.ServiceException;
import com.airdrop.dto.UpdateDto;
import com.airdrop.dto._ResultDto;
import com.airdrop.service.UserService;
import com.airdrop.util.CookieUtil2;
import com.airdrop.util.SessionUtil;
import com.airdrop.util.StringUtil;
import com.airdrop.util.TokenUtil;
import com.airdrop.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 登陆接口控制
 * @date 2018/9/11 11:51
 */
@Api(description = "登陆Api接口")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    CookieUtil2 cookieUtil = new CookieUtil2();

    @ApiOperation("返回账户未登录信息")
    @GetMapping("/login")
    public _ResultDto login() {
        return new _ResultDto(CodeEnum.CODE_401.getCode(), "账户未登录");
    }

    @ApiOperation("账户登陆接口")
    @PostMapping("/login")
    public UpdateDto login(@RequestParam String username, @RequestParam String password, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {

        return userService.login(username, password, session, request, response);
    }

    @ApiOperation("账户注销登陆")
    @PostMapping("/logout")
    public _ResultDto logout(HttpServletRequest request) {
        // 获得token
        String token = request.getHeader(TokenUtil.TOKEN);
        // 校验token是否有效
        if (null != token && SessionUtil.checkUser(token)) {
            SessionUtil.removeUser(token);
        }
        return new _ResultDto();
    }

    @ApiOperation("后台系统登陆")
    @PostMapping("/bLogin")
    public UpdateDto bLogin(@RequestParam(required = false) String username, @RequestParam(required = false) String password, HttpServletRequest request) {
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), "用户名或密码不能为空");
        }
        return userService.bLogin(username, password, request.getSession());
    }

    @ApiOperation("后台和后端心跳连接")
    @PostMapping("/heartbeat")
    public UpdateDto heartbeat(HttpServletRequest request) {
        String token = request.getHeader(TokenUtil.TOKEN);
        if (SessionUtil.checkUser(token)) {
            // 返回用户信息
            UserVo user = TokenUtil.getUser(token);
            return new UpdateDto(new HashMap<String,String>(){
                {
                    this.put("username", user.getPhone());
                    this.put("email", user.getEmail());
                    this.put("head", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3701485667,96126389&fm=200&gp=0.jpg");
                }
            });
        } else {
            return new UpdateDto(CodeEnum.CODE_401.getCode(), "登陆失效，请重新登录");
        }
    }

}
