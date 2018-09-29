package com.airdrop.service;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.config.exception.ServiceException;
import com.airdrop.dto.QueryDto;
import com.airdrop.dto.UpdateDto;
import com.airdrop.entity.MoneyHistory;
import com.airdrop.entity.User;
import com.airdrop.repository.MoneyHistoryRepository;
import com.airdrop.repository.UserRepository;
import com.airdrop.repository.dao.UserDao;
import com.airdrop.util.TokenUtil;
import com.airdrop.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service("moneyHistoryService")
public class MoneyHistoryService {
    //实现存储用户帐户余额的功能
    @Autowired
    private MoneyHistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    //把用户通过大转盘得到的钱存到数据库中
    @Transactional
    public void insertMoney(MoneyHistory history) {
        historyRepository.saveAndFlush(history);

    }

    @Transactional
    public Integer findCountByUserId(Integer userId) {
        Integer count = historyRepository.findCountByUserId(userId);
        return count;
    }

    public List findHistoryCountByUserId(Integer userId) {
        List historyCount = historyRepository.findHistoryCountByUserId(userId);
        return historyCount;
    }

    /**
     * 查询余额信息
     *
     * @param userid
     * @param pageable
     * @return
     */
    public QueryDto find(Integer userid, Pageable pageable) {
        if (userid == null) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), "用户id不能为空");
        } else {
            return new QueryDto(historyRepository.findAll(Example.of(new MoneyHistory(userid)), pageable));
        }
    }

    /**
     * 查询当前用户余额历史
     */
    public QueryDto find(Pageable pageable, String token) {
        if (token == null) {
            throw new ServiceException(CodeEnum.CODE_401.getCode(), CodeEnum.CODE_401.getMessage());
        } else {
            UserVo user = TokenUtil.getUser(token);
            return new QueryDto(historyRepository.findAll(Example.of(new MoneyHistory(user.getId())), pageable));
        }
    }

    public UpdateDto getUse(String token){
        UserVo user = TokenUtil.getUser(token);
        return new UpdateDto(userRepository.getById(user.getId()).getMoney());

    }

}
