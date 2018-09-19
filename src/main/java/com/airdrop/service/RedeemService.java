package com.airdrop.service;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.config.exception.ServiceException;
import com.airdrop.dto.QueryDto;
import com.airdrop.dto.UpdateDto;
import com.airdrop.dto._ResultDto;
import com.airdrop.entity.Redeem;
import com.airdrop.repository.RedeemRepository;
import com.airdrop.util.LogUtil;
import com.airdrop.util.RedeemCodeUtils;
import com.airdrop.util.TokenUtil;
import com.airdrop.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description:兑换码业务层
 * @date 2018/9/18 19:05
 */
@Service("redeemService")
public class RedeemService {

    @Autowired
    private RedeemRepository redeemRepository;

    @Autowired
    private LogService logService;

    /**
     * 创建兑换码
     *
     * @param length
     * @param count
     * @return
     */
    public UpdateDto createRedeem(int length, int count, String airDrop, String token) {
        // 存储兑换码集合
        List<Redeem> redeems = new ArrayList<>();
        // 获取当前登陆用户
        UserVo user = TokenUtil.getUser(token);
        // 根据count循环生成兑换码
        for (int i = 0; i < count; i++) {
            // 生成兑换码，并存储到集合
            redeems.add(new Redeem(RedeemCodeUtils.createBigSmallLetterStrOrNumberRadom(length), user.getId(), airDrop));
        }
        // 保存到数据库
        redeems = redeemRepository.saveAll(redeems);
        // 添加日志
        logService.insertLog(user.getId(), user.getName() + "创建了" + count + "个验证码，长度为：" + length, LogUtil.SUCCESS);
        return new UpdateDto(redeems);
    }

    /**
     * 查询
     *
     * @param redeem
     * @param pageable
     * @return
     */
    public QueryDto find(Redeem redeem, Pageable pageable) {
        // 设置查询条件
        redeem.setDataStatus(0);
        // 调用查询方法
        Page<Redeem> pages = redeemRepository.findAll(Example.of(redeem), pageable);
        return new QueryDto(pages);
    }

    /**
     * 删除数据
     *
     * @param id
     * @param token
     * @return
     */
    @Transactional
    public _ResultDto delete(int id, String token) {
        // 校验id是否正确
        checkedId(id);
        if (redeemRepository.deleteById(id) == 0) {
            throw new ServiceException(CodeEnum.CODE_500.getCode(), "数据删除失败");
        }
        // 获取当前登陆用户
        UserVo user = TokenUtil.getUser(token);
        // 添加日志
        logService.insertLog(user.getId(), user.getName() + "删除了验证码ID：" + id, LogUtil.SUCCESS);
        return new _ResultDto();
    }

    /**
     * 修改空投值
     *
     * @param redeem
     * @param token
     * @return
     */
    @Transactional
    public _ResultDto update(Redeem redeem, String token) {
        // 校验id是否正确
        checkedId(redeem.getId());
        // 获取当前登陆用户
        UserVo user = TokenUtil.getUser(token);
        // 修改操作
        if (redeemRepository.updateAirDrop(redeem.getAirDrop(), redeem.getId()) == 0) {
            throw new ServiceException(CodeEnum.CODE_500.getCode(), "数据修改失败");
        }
        return new _ResultDto();
    }

    /**
     * 校验id是否正确
     *
     * @param id
     * @return true：存在
     */
    private void checkedId(int id) {
        if (redeemRepository.getOne(id) == null) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), "ID已被删除或不存在");
        }
    }

}
