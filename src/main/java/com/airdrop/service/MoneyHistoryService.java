package com.airdrop.service;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.config.exception.ServiceException;
import com.airdrop.dto.QueryDto;
import com.airdrop.entity.MoneyHistory;
import com.airdrop.repository.MoneyHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("moneyHistoryService")
public class MoneyHistoryService {
    //实现存储用户帐户余额的功能
    @Autowired
    private MoneyHistoryRepository historyRepository;

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

}
