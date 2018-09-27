package com.airdrop.service;

import com.airdrop.dto.UpdateDto;
import com.airdrop.repository.dao.AirDropDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 空投业务层
 * @date 2018/9/26 16:50
 */
@Service
public class AirDropService {

    @Autowired
    private AirDropDao airDropDao;

    /**
     * 查询空投信息
     *
     * @return
     */
    public UpdateDto findAirDrop() {
        return airDropDao.findAirDrop();
    }

}
