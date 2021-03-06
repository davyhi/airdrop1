package com.airdrop.service;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.config.exception.ServiceException;
import com.airdrop.dto.QueryDto;
import com.airdrop.dto.UpdateDto;
import com.airdrop.dto._ResultDto;
import com.airdrop.entity.Privileges;
import com.airdrop.entity.Role;
import com.airdrop.entity.User;
import com.airdrop.entity.UserRole;
import com.airdrop.repository.PrivilegesRepository;
import com.airdrop.repository.RoleRepository;
import com.airdrop.repository.UserRepository;
import com.airdrop.repository.UserRoleRepository;
import com.airdrop.repository.dao.UserDao;
import com.airdrop.util.*;
import com.airdrop.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;


/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户业务层
 * @date 2018/9/10 18:36
 */
@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegesRepository privilegesRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LogService logService;

    CookieUtil2 cookieUtil = new CookieUtil2();

    /**
     * 前台登陆方法
     *
     * @param username
     * @param password
     * @return
     */
    public UpdateDto login(String username, String password, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 获取用户信息

        User user = userRepository.findByUsername(username);
        // 判断用户、密码是否正确
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        if (null == user || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), "用户名或密码输入错误，登录失败!");
        }
        // 判断用户是否被禁用
        //if (user.getEnable()) {
        //    throw new ServiceException(CodeEnum.CODE_400.getCode(), "账户被禁用，登录失败，请联系管理员!");
        //}
        // 设置用户登陆信息
        String token = setUserLoginInfo(session, user, request, response);
        // 更新日志
        logService.insertLog(user.getId(), user.getName() + "登陆前台系统", LogUtil.SUCCESS);
        return new UpdateDto(new HashMap<String, String>() {
            {
                this.put(TokenUtil.TOKEN, token);
            }
        });
    }

    public static void main(String[] args) {
        boolean b = new BCryptPasswordEncoder().matches("123456", "$2a$10$EXmnU1NjZ3SGTi8s4SFKfePymziPESGTRlLdbvSDAK/wu199TcQPu");
        System.err.println(b);
    }

    /**
     * 设置用户登陆信息保存到session
     *
     * @param session
     * @param user
     * @return 返回token
     */
    private String setUserLoginInfo(HttpSession session, User user, HttpServletRequest request, HttpServletResponse response) {
        // 获取用户权限相关信息，
        List<Role> roles = roleRepository.findByUserId(user.getId());
        List<Privileges> pris = null;
        if (roles.size() > 0) {
            // 根据用户的角色去获取权限
            StringBuffer sb = new StringBuffer();
            for (Role r : roles) {
                sb.append(r.getId()).append(',');
            }
            pris = privilegesRepository.findByROles(sb.substring(0, sb.length() - 1));
        }
        // 生成token,保存用户信息到session
        String token = TokenUtil.createToken(new UserVo(user.getId(), user.getName(), user.getEmail(), user.getPhone(), pris, roles));
        session.setAttribute(token, true);
        //setCookie(request, response, "davy", token, -1);
        ServletContext app1 = request.getServletContext();
        app1.setAttribute("xixihaha", token);
        response.setHeader("Authorization", token);
        SessionUtil.addUser(token, session);
        // 设置登陆有效时长 1小时
        session.setMaxInactiveInterval(60 * 60);
        return token;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    public UpdateDto register(User user) {
        // 校验（手机号/邮箱），密码是否是空数据，校验邮箱或者密码是否已存在
        checkUser(user);
        // 密码加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword().trim()));
        // 注册操作
        user = userRepository.saveAndFlush(user);
        Integer userid = user.getId();
        // 判断是否添加成功
        if (null == user || userid == null) {


            throw new ServiceException(CodeEnum.CODE_500.getCode(), CodeEnum.CODE_500.getMessage());
        }
        //添加默认角色
        userRoleRepository.saveAndFlush(new UserRole(userid, 1L));
        return new UpdateDto(user);
    }

    //手机注册
    public UpdateDto registerPhone(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword().trim()));
        this.userRepository.save(user);
        userRoleRepository.saveAndFlush(new UserRole(user.getId(), 1L));
        return new UpdateDto(user);
    }


    /**
     * 校验（手机号/邮箱），密码是否是空数据
     *
     * @param user
     * @return
     */
    private void checkUser(User user) {
        if (null == user || ((StringUtil.isEmpty(user.getPhone()) && StringUtil.isEmpty(user.getEmail())) && StringUtil.isEmpty(user.getPassword()))) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), CodeEnum.CODE_400.getMessage());
        } else if (StringUtil.isNotEmpty(user.getEmail())) {    // 校验邮箱是否已存在
            if (userRepository.findByUsername(user.getEmail()) != null) {
                throw new ServiceException(CodeEnum.CODE_400.getCode(), "邮箱已存在");
            }
        } else if (StringUtil.isNotEmpty(user.getPhone())) {    // 校验手机号是否已存在
            if (userRepository.findByUsername(user.getPhone()) != null) {
                throw new ServiceException(CodeEnum.CODE_400.getCode(), "手机号已存在");
            }
        }
    }


    /**
     * 不适用框架登陆
     *
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }


    //检查该手机号是否已注册过
    public Integer checkPhoneExist(String phone) {
        return this.userRepository.checkUserByPhone(phone);
    }

    //检查邮箱是否已注册过
    public Integer checkEmailExist(String email) {
        return this.userRepository.checkUserByEmail(email);
    }


    public UpdateDto registerEmail(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword().trim()));
        this.userRepository.save(user);
        userRoleRepository.saveAndFlush(new UserRole(user.getId(), 1L));
        return new UpdateDto(user);

    }


    public void updateUserMoney(Integer id, Integer money) {
        this.userRepository.updateUMoney(id, money);
    }


    public String findBalanceByUserId(Integer id) {
        Integer balance = this.userRepository.findBalanceByUserId(id);
        return "" + balance;
    }

    //根据用户输入的手机号查询帐户余额
    public String findSpecialBalanceByPhone(String number) {
        Integer SpecialBalance = this.userRepository.findSpecialBalanceByPhone(number);
        return "" + SpecialBalance;
    }

    //根据用户输入的邮箱查询帐户余额
    public String findSpecialBalanceByEmail(String number) {
        Integer SpecialBalance = this.userRepository.findSpecialBalanceByEmail(number);
        return "" + SpecialBalance;
    }

    /**
     * 后台登陆方法
     *
     * @param username
     * @param password
     * @return
     */
    public UpdateDto bLogin(String username, String password, HttpSession session) {
        // 获取用户信息
        User user = userRepository.findByPhoneB(username);
        // 判断用户、密码是否正确
        if (null == user || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), "用户名或密码输入错误，登录失败!");
        }
        // 判断用户是否被禁用
        if (user.getEnable()) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), "账户被禁用，登录失败，请联系管理员!");
        }
        // 设置用户登陆信息
        String token = setUserLoginInfoTwo(session, user);
        // 更新日志
        logService.insertLogB(user.getId(), user.getName() + "登陆系统", LogUtil.SUCCESS);
        return new UpdateDto(new HashMap<String, String>() {
            {
                this.put(TokenUtil.TOKEN, token);
            }
        });
    }

    /**
     * 设置后台用户登陆信息保存到session
     *
     * @param session
     * @param user
     * @return 返回token
     */
    private String setUserLoginInfoTwo(HttpSession session, User user) {
        // 获取用户权限相关信息，
        List<Role> roles = roleRepository.findByUserId(user.getId());
        List<Privileges> pris = null;
        if (roles.size() > 0) {
            // 根据用户的角色去获取权限
            StringBuffer sb = new StringBuffer();
            for (Role r : roles) {
                sb.append(r.getId()).append(',');
            }
            pris = privilegesRepository.findByROles(sb.substring(0, sb.length() - 1));
        }
        // 生成token,保存用户信息到session
        String token = TokenUtil.createToken(new UserVo(user.getId(), user.getName(), user.getEmail(), user.getPhone(), pris, roles));
        session.setAttribute(token, true);
        // 设置登陆有效时长 1小时
        session.setMaxInactiveInterval(60 * 60);
        SessionUtil.addUser(token, session);
        return token;
    }

    /**
     * 后台用户查询
     *
     * @param user
     * @param pageable
     * @return
     */
    public QueryDto find(User user, Pageable pageable) {
        return userDao.findJoinMoney(pageable);
    }

    /**
     * 后台用户删除
     *
     * @param id
     * @param token
     * @return
     */
    @Transactional
    public _ResultDto delete(int id, String token) {
        if (userRepository.deleteById(id) < 1) {
            throw new ServiceException(CodeEnum.CODE_500.getCode(), "删除失败");
        }
        UserVo user = TokenUtil.getUser(token);
        logService.insertLogB(user.getId(), user.getName() + "删除了用户id：" + id, LogUtil.SUCCESS);
        return new _ResultDto();
    }

    /**
     * 后台用户修改信息
     *
     * @param user
     * @param token
     * @return
     */
    public _ResultDto update(User user, String token) {
        User one = userRepository.getById(user.getId());
        if (one == null) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), "ID不存在或者已经被删除");
        }
        one.replace(user);
        userRepository.saveAndFlush(one);
        UserVo currUser = TokenUtil.getUser(token);
        logService.insertLogB(currUser.getId(), currUser.getName() + "修改了用户ID：" + user.getId() + "的数据", LogUtil.SUCCESS);
        return new _ResultDto();
    }


    //查询用户历史余额之总和
    public UpdateDto checkTotalMoney(Integer id) {

        Integer totalMoney = userRepository.checkTotalMoney(id);
        return new UpdateDto(totalMoney);


    }

}
