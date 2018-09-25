package com.airdrop.service;

import com.airdrop.dto.QueryDto;
import com.airdrop.entity.Log;
import com.airdrop.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 日志业务
 * @date 2018/8/16 14:43
 */
@Service("logService")
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public QueryDto find(Pageable page) {
        return new QueryDto(logRepository.findAll(page));
    }

    /**
     * 添加日志信息
     *
     * @param id      当前登陆用户id
     * @param content 日志内容
     * @param type    日志类型
     */
    @Async("myExecutor")
    @Transactional
    public void insertLog(int id, String content, int type) {
        logRepository.insert(content, type, id);
    }

    /**
     * 添加后台日志信息
     *
     * @param id      当前登陆用户id
     * @param content 日志内容
     * @param type    日志类型
     */
    @Async("myExecutor")
    @Transactional
    public void insertLogB(int id, String content, int type) {
        logRepository.insertB(content, type, id);
    }

}
